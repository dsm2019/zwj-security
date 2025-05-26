<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
      <el-form-item  label="地下城" class="dungeon-list">
        <el-select v-model="dataForm.dungeonId" placeholder="地下城">
          <el-option v-for="dungeon in dungeonList" :key="dungeon.id" :label="dungeon.name" :value="dungeon.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item  label="角色" class="character-list">
        <el-select v-model="dataForm.characterId" placeholder="角色">
          <el-option v-for="character in characterList" :key="character.id" :label="character.name" :value="character.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="时间周期" prop="completionPeriod">
        <el-input v-model="dataForm.completionPeriod" placeholder="时间周期"></el-input>
      </el-form-item>
      <!-- 修改通关时间输入框布局 -->
      <el-form-item label="通关时间">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input v-model="dataForm.completionTimeMinutes" type="number" placeholder="分钟" @input="updateCompletionTime"></el-input>
          </el-col>
          <el-col :span="2" style="text-align: center; line-height: 40px;">分</el-col>
          <el-col :span="6">
            <el-input v-model="dataForm.completionTimeSeconds" type="number" placeholder="秒" @input="updateCompletionTime"></el-input>
          </el-col>
          <el-col :span="2" style="text-align: center; line-height: 40px;">秒</el-col>
          <el-col :span="6">
            <el-input v-model="dataForm.completionTimeMilliseconds" type="number" placeholder="毫秒" @input="updateCompletionTime" @blur="validateMilliseconds"></el-input>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { nextTick, reactive, ref } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";

const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();
const dungeonList = ref([]);
const characterList = ref([]);

const dataForm = reactive({
  id: "",
  dungeonId: "",
  characterId: "",
  completionPeriod: "",
  completionTime: "",
  completionTimeMinutes: 0, // 新增分钟字段
  completionTimeSeconds: 0,
  completionTimeMilliseconds: 0,
  creator: "",
  createDate: "",
  updater: "",
  updateDate: ""
});

const rules = ref({
  dungeonId: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  characterId: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  completionPeriod: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  // 移除原有的 completionTime 验证规则
});

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.completionTimeMinutes = 0; // 初始化分钟字段
  dataForm.completionTimeSeconds = 0;
  dataForm.completionTimeMilliseconds = 0;

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  nextTick(() => {
    Promise.all([getDungeonList(),getCharacterList()]).then(() => {
      if (id) {
        getInfo(id);
      }
    });
  });
};

// 获取地下城列表
const getDungeonList = () => {
  return baseService.get("/dnf/dungeon/list").then((res) => {
    dungeonList.value = res.data;
  });
};

// 获取角色列表
const getCharacterList = () => {
  return baseService.get("/dnf/character/list").then((res) => {
    characterList.value = res.data;
  });
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/dnf/completionRecord" + id).then((res) => {
    Object.assign(dataForm, res.data);
    // 将原有的毫秒值转换为分钟、秒和毫秒
    if (dataForm.completionTime) {
      let totalMilliseconds = Number(dataForm.completionTime);
      dataForm.completionTimeMinutes = Math.floor(totalMilliseconds / (1000 * 60));
      totalMilliseconds %= (1000 * 60);
      dataForm.completionTimeSeconds = Math.floor(totalMilliseconds / 1000);
      dataForm.completionTimeMilliseconds = totalMilliseconds % 1000;
    }
  });
};

// 验证毫秒输入
const validateMilliseconds = () => {
  let milliseconds = Number(dataForm.completionTimeMilliseconds);
  if (isNaN(milliseconds)) {
    dataForm.completionTimeMilliseconds = 0;
  } else if (milliseconds < 0) {
    dataForm.completionTimeMilliseconds = 0;
  } else if (milliseconds > 99) {
    dataForm.completionTimeMilliseconds = 99;
  }
  updateCompletionTime();
};

// 更新通关时间（毫秒）
const updateCompletionTime = () => {
  const minutes = Number(dataForm.completionTimeMinutes) || 0;
  const seconds = Number(dataForm.completionTimeSeconds) || 0;
  let milliseconds = Number(dataForm.completionTimeMilliseconds) || 0;
  // 确保毫秒值在 0 到 99 之间
  milliseconds = Math.min(Math.max(milliseconds, 0), 99);
  dataForm.completionTime = (minutes * 60 * 1000 + seconds * 1000 + milliseconds * 10).toString();
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/dnf/completionRecord", dataForm).then((res) => {
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
