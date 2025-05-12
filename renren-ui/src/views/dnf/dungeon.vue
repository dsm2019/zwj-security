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
<!--      <el-form-item>-->
<!--        <el-button v-if="state.hasPermission('sys:role:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>-->
<!--      </el-form-item>-->
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" show-overflow-tooltip row-key="id"  border @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle" style="width: 100%">
<!--      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>-->
      <el-table-column prop="name" label="名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="boss" label="boss" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="typeName" label="类型" header-align="center" align="center"></el-table-column>
      <el-table-column prop="createDate" label="创建时间" sortable="custom" header-align="center" align="center" width="180"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('dnf:character:save') && scope.row.parentId === 0 && scope.row.type === 3" type="primary" link @click="addHandle(scope.row)">新增</el-button>
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
import { reactive, ref, toRefs } from "vue";
import AddOrUpdate from "./dungeon-add-or-update.vue";

const view = reactive({
  getDataListURL: "/dnf/dungeon/page",
  deleteURL: "/dnf/dungeon",
  getDataListIsPage: true
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};

const addHandle = (row: any) => {
  addOrUpdateRef.value.init2(row);
};
</script>
