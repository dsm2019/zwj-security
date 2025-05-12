<template>
  <div class="mod-dnf__role">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-input v-model="state.dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:role:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:role:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="refreshSort">刷新排序</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="sort" label="No." sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="name" label="名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="profession" label="职业" header-align="center" align="center"></el-table-column>
      <el-table-column prop="fame" label="名望" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="名望百分比" header-align="center" align="center">
        <template v-slot="scope">
          <span>{{ scope.row.famePercent || 0 }}%</span>
        </template>
      </el-table-column>
      <el-table-column prop="simulatedDamage" sortable="custom" label="模拟伤害" header-align="center" align="center"></el-table-column>
      <el-table-column label="伤害百分比" header-align="center" align="center">
        <template v-slot="scope">
          <span>{{ scope.row.damagePercent || 0 }}%</span>
        </template>
      </el-table-column>
      <el-table-column label="伤害除以名望" header-align="center" align="center">
        <template v-slot="scope">
          <span>{{ scope.row.damagePerFame || 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="level" label="等级" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="attackSpeed" label="攻击速度" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="movementSpeed" label="移动速度" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="userName" label="用户" sortable="custom" header-align="center" align="center"></el-table-column>
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
import { reactive, ref, toRefs, watch } from "vue";
import AddOrUpdate from "./character-add-or-update.vue";
import baseService from "@/service/baseService";
import { ElMessage } from 'element-plus'; // 引入 Element UI 的消息提示组件

const view = reactive({
  getDataListURL: "/dnf/character/page",
  getDataListIsPage: true,
  deleteURL: "/dnf/character",
  deleteIsBatch: true,
  dataForm: {
    name: ""
  }
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};

// 计算百分比和伤害除以名望的方法
const calculatePercentagesAndDamagePerFame = (list) => {
  if (!list || list.length === 0) return;

  // 获取第一行数据作为基准
  const firstRow = list[0];
  const baseFame = firstRow.fame || 0;
  const baseDamage = firstRow.simulatedDamage || 0;

  // 计算每一行的百分比和伤害除以名望（保留整数）
  list.forEach(row => {
    // 避免除以零
    row.famePercent = baseFame > 0?
      Math.round((row.fame / baseFame) * 100) : 0;

    row.damagePercent = baseDamage > 0?
      Math.round((row.simulatedDamage / baseDamage) * 100) : 0;

    row.damagePerFame = row.fame > 0?
      Math.round(row.simulatedDamage / row.fame) : 0; // 新增：使用Math.round取整
  });
};

// 新增：调用刷新排序接口的方法
const refreshSort = async () => {
  try {
    await baseService.post("/dnf/character/refreshSort", {});
    ElMessage.success('刷新排序成功'); // 显示成功提示
    state.getDataList(); // 重新加载数据
  } catch (error) {
    console.error('刷新排序失败:', error);
    ElMessage.error('刷新排序失败，请稍后重试'); // 显示失败提示
  }
};


// 监听数据列表变化，计算百分比和伤害除以名望
watch(() => state.dataList, (newList) => {
  calculatePercentagesAndDamagePerFame(newList);
}, { deep: true, immediate: true });
</script>
