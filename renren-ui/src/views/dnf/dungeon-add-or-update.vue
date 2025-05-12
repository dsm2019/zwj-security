<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
      <el-form-item prop="name" label="名称">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item prop="boss" label="boss">
        <el-input v-model="dataForm.boss" placeholder="boss"></el-input>
      </el-form-item>
      <el-form-item  prop="type" label="类型" class="type-list">
        <el-select v-model="dataForm.type" :disabled="dataForm.parentId !== 0" placeholder="类型">
          <el-option v-for="type in typeList" :key="type.id" :label="type.name" :value="type.id"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { nextTick, reactive, ref } from "vue";
import baseService from "@/service/baseService";
import { IObject } from "@/types/interface";
import { ElMessage } from "element-plus";
import { getIconList } from "@/utils/utils";
const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const typeList = ref([]);
const dataFormRef = ref();

const dataForm = reactive({
  id: "",
  parentId: 0,
  name: "",
  boss: "",
  type: "",
});

const rules = ref({
  name: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  nextTick(() => {
    Promise.all([getTypeList()]).then(() => {
      if (id) {
        getInfo(id);
      }
    });
  });
};

const init2 = (row: IObject) => {
  visible.value = true;

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  dataForm.id = "";
  dataForm.parentId = row.id;


  nextTick(() => {
    Promise.all([getTypeList()]).then(() => {

    });
  });

  dataForm.type = row.type;
};

// 获取类型列表
const getTypeList = () => {
  return baseService.get("/dnf/dungeon/typeList").then((res) => {
    typeList.value = res.data;
  });
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get(`/dnf/dungeon/${id}`).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/dnf/dungeon", dataForm).then((res) => {
      ElMessage.success({
        message: "成功",
        duration: 500,
        onClose: () => {
          visible.value = false;
          emit("refreshDataList");
        }
      });
    });
  });
};

defineExpose({
  init,
  init2
});
</script>
