<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
      <el-form-item prop="name" label="名称">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item prop="profession" label="职业">
        <el-input v-model="dataForm.profession" placeholder="职业"></el-input>
      </el-form-item>
      <el-form-item prop="fame" label="名望">
        <el-input v-model="dataForm.fame" placeholder="名望"></el-input>
      </el-form-item>
      <el-form-item prop="simulatedDamage" label="模拟伤害">
        <el-input v-model="dataForm.simulatedDamage" placeholder="模拟伤害"></el-input>
      </el-form-item>
      <el-form-item prop="level" label="等级">
        <el-input v-model="dataForm.level" placeholder="等级"></el-input>
      </el-form-item>
      <el-form-item prop="attackSpeed" label="攻击速度">
        <el-input v-model="dataForm.attackSpeed" placeholder="攻击速度"></el-input>
      </el-form-item>
      <el-form-item prop="movementSpeed" label="移动速度">
        <el-input v-model="dataForm.movementSpeed" placeholder="移动速度"></el-input>
      </el-form-item>
      <el-form-item prop="userId" label="用户" class="user-list">
        <el-select v-model="dataForm.userId" placeholder="用户">
          <el-option v-for="user in userList" :key="user.id" :label="user.username" :value="user.id"></el-option>
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
const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const userList = ref([]);
const dataFormRef = ref();

const dataForm = reactive({
  id: "",
  name: "",
  profession: "",
  fame: "",
  simulatedDamage: "",
  level: "",
  attackSpeed: "",
  movementSpeed: "",
  userId: "",
  menuIdList: [] as IObject[],
  deptIdList: [],
  remark: ""
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
    Promise.all([getUserList()]).then(() => {
      if (id) {
        getInfo(id);
      }
    });
  });
};

// 获取用户列表
const getUserList = () => {
  return baseService.get("/sys/user/page").then((res) => {
    userList.value = res.data.list;
  });
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get(`/dnf/character/${id}`).then((res) => {
    Object.assign(dataForm, res.data);

  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/dnf/character", dataForm).then((res) => {
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
  init
});
</script>
