<template>
  <div class="mod-demo__dnf-completion-record">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-button v-if="state.hasPermission('dnf:character:save')" type="primary" @click="addOrUpdateHandle()">新增 </el-button>
      </el-form-item>
      <!--      <el-form-item>-->
      <!--        <el-button v-if="state.hasPermission('dnf:character:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>-->
      <!--      </el-form-item>-->
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <!--      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>-->
      <el-table-column prop="id" label="ID" header-align="center" align="center"></el-table-column>
      <el-table-column prop="dungeonId" label="地下城ID" header-align="center" align="center"></el-table-column>
      <el-table-column prop="characterId" label="角色ID" header-align="center" align="center"></el-table-column>
      <el-table-column prop="completionPeriod" label="ID" header-align="center" align="center"></el-table-column>
      <el-table-column prop="completionTime" label="完成的时间(毫秒)" header-align="center" align="center"></el-table-column>
      <el-table-column prop="creator" label="创建者" header-align="center" align="center"></el-table-column>
      <el-table-column prop="createDate" label="创建时间" header-align="center" align="center"></el-table-column>
      <!--              <el-table-column prop="updater" label="更新者" header-align="center" align="center"></el-table-column>-->
      <!--              <el-table-column prop="updateDate" label="更新时间" header-align="center" align="center"></el-table-column>-->
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('dnf:character:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改 </el-button>
          <el-button v-if="state.hasPermission('dnf:character:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"></el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <completion-record-add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList">确定 </completion-record-add-or-update>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs } from "vue";
import CompletionRecordAddOrUpdate from "./completion-record-add-or-update.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/dnf/completionRecord/page",
  getDataListIsPage: true,
  exportURL: "/dnf/completionRecord/export",
  deleteURL: "/dnf/completionRecord"
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
