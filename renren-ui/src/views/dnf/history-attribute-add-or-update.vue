<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false"
             :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()"
             label-width="120px">
      <el-form-item prop="character" label="角色" class="character-list">
        <el-select v-model="dataForm.characterId" placeholder="请选择角色">
          <el-option v-for="character in characterList" :key="character.id" :label="character.name"
                     :value="character.id"></el-option>
        </el-select>
      </el-form-item>

      <!--      <el-form-item prop="characterId" label="角色" class="character-list">-->
      <!--        <el-select-->
      <!--          v-model="dataForm.characterId"-->
      <!--          placeholder="请选择角色"-->
      <!--          filterable-->
      <!--          remote-->
      <!--          :remote-method="filterCharacters"-->
      <!--          :loading="searchLoading"-->
      <!--        >-->
      <!--          <el-option-->
      <!--            v-for="character in filteredCharacterList"-->
      <!--            :key="character.id"-->
      <!--            :label="character.name"-->
      <!--            :value="character.id"-->
      <!--          ></el-option>-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item prop="name" label="名称">-->
      <!--        <el-input v-model="dataForm.characterName" placeholder="名称"></el-input>-->
      <!--      </el-form-item>-->
      <el-form-item prop="fame" label="名望">
        <el-input v-model="dataForm.fame" placeholder="名望"></el-input>
      </el-form-item>
      <el-form-item prop="simulatedDamage" label="模拟伤害">
        <el-input v-model="dataForm.simulatedDamage" placeholder="模拟伤害"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import {nextTick, reactive, ref} from "vue";
import baseService from "@/service/baseService";
import {IObject} from "@/types/interface";
import {ElMessage} from "element-plus";

const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const characterList = ref([]);
const dataFormRef = ref();

const dataForm = reactive({
  id: "",
  characterId: "",
  fame: "",
  simulatedDamage: ""
});

const rules = ref({
  name: [{required: true, message: "必填项不能为空", trigger: "blur"}]
});

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  nextTick(() => {
    Promise.all([getCharacterList()]).then(() => {
      if (id) {
        getInfo(id);
      }
    });
  });
};

// 获取dnf角色列表
const getCharacterList = () => {
  return baseService.get("/dnf/character/list").then((res) => {
    characterList.value = res.data;
  });
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get(`/dnf/history-attribute/${id}`).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/dnf/history-attribute", dataForm).then((res) => {
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
