<template>
  <div class="mod-dnf__history-attribute">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-input v-model="state.dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="state.dataForm.characterId" placeholder="请选择角色" @change="state.getDataList()">
          <el-option v-for="character in characterList" :key="character.id" :label="character.name"
                     :value="character.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="state.dataForm.selectDate"
          type="date"
          placeholder="选择记录日期"
          :disabled-date="state.disabledDate"
          @change="state.getDataList()"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:role:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:role:delete')" type="primary" @click="state.refreshRankingHandle()">刷新排名</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle" style="width: 100%">
<!--      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>-->
      <el-table-column prop="characterName" label="dnf角色" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="recordDate" label="时间" sortable="custom" header-align="center" align="center" width="180"></el-table-column>
      <el-table-column prop="fame" label="名望" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="fameRanking" label="名望No." sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="simulatedDamage" sortable="custom" label="模拟伤害" header-align="center" align="center"></el-table-column>
      <el-table-column prop="simulatedDamageRanking" sortable="custom" label="模拟伤害No." header-align="center" align="center"></el-table-column>
      <el-table-column prop="createDate" label="创建时间" sortable="custom" header-align="center" align="center" width="180"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('dnf:character:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('dnf:character:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"> </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs, onMounted } from "vue";
import AddOrUpdate from "./history-attribute-add-or-update.vue";
import baseService from "@/service/baseService";

const formatDate = (date: Date | null): string | null => {
  if (!date) return null;
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const availableDates = ref<string[]>([]);
const characterList = ref<number[]>([]);

const view = reactive({
  getDataListURL: "/dnf/history-attribute/page",
  getDataListIsPage: true,
  deleteURL: "/dnf/history-attribute",
  deleteIsBatch: true,
  refreshRankingURL: "/dnf/history-attribute/refreshRanking",
  recordDateUrl: "/dnf/history-attribute/recordDates",
  getCharacterListUrl: "/dnf/character/list",
  dataForm: {
    name: "",
    characterId: null,
    selectDate: null,
    get recordDate() {
      return formatDate(this.selectDate);
    }
  },
  disabledDate(date: Date) {
    const formatted = formatDate(date);
    return !formatted || !availableDates.value.includes(formatted);
  }
});

const state = reactive({
  ...useView(view),
  ...toRefs(view),
  // 新增刷新排名方法
  refreshRankingHandle() {
    baseService.post(state.refreshRankingURL)
     .then(() => {
        this.getDataList();
      })
     .catch((error) => {
        console.error('刷新排名失败', error);
      });
  }
});

// 获取dnf角色列表
// const getCharacterList = () => {
//   return baseService.get(state.getCharacterListUrl).then((res) => {
//     characterList.value = res.data;
//   });
// };

// 组件挂载时获取可用日期
onMounted(async () => {
  try {
    const res = await baseService.get(state.recordDateUrl);
    if (res.code === 0) {
      availableDates.value = res.data; // 假设返回的数据在 res.data 中
    }
  } catch (error) {
    console.error('获取可用日期失败', error);
  }

  try {
    const res = await baseService.get(state.getCharacterListUrl);
    if (res.code === 0) {
      characterList.value = res.data; // 假设返回的数据在 res.data 中
    }
  } catch (error) {
    console.error('获取可用日期失败', error);
  }
});

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
