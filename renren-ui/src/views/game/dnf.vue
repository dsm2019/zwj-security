<template>
  <div>
    <h1>DNF 角色列表</h1>
    <div style="margin-bottom: 10px;">
      <el-button type="primary" @click="openDialog('create')">新增</el-button>
    </div>
    <el-table :data="characterList" style="width: 100%" :default-sort="{ prop: 'id', order: 'ascending' }">
      <el-table-column prop="id" label="角色 ID" sortable></el-table-column>
      <el-table-column prop="name" label="角色名字" sortable></el-table-column>
      <el-table-column prop="profession" label="角色职业" sortable></el-table-column>
      <el-table-column prop="fame" label="角色名望" sortable></el-table-column>
      <el-table-column prop="simulatedDamage" label="模拟伤害" sortable></el-table-column>
      <el-table-column prop="level" label="角色等级" sortable></el-table-column>
      <el-table-column prop="attackSpeed" label="攻击速度" sortable></el-table-column>
      <el-table-column prop="movementSpeed" label="移动速度" sortable></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="openDialog('update', scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteCharacter(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogType === 'create' ? '新增角色' : '编辑角色'">
      <el-form :model="form" label-width="100px">
        <el-form-item label="角色名字">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="角色职业">
          <el-input v-model="form.profession"></el-input>
        </el-form-item>
        <el-form-item label="角色名望">
          <el-input-number v-model="form.fame"></el-input-number>
        </el-form-item>
        <el-form-item label="模拟伤害">
          <el-input-number v-model="form.simulatedDamage"></el-input-number>
        </el-form-item>
        <el-form-item label="角色等级">
          <el-input-number v-model="form.level"></el-input-number>
        </el-form-item>
        <el-form-item label="攻击速度">
          <el-input-number v-model="form.attackSpeed"></el-input-number>
        </el-form-item>
        <el-form-item label="移动速度">
          <el-input-number v-model="form.movementSpeed"></el-input-number>
        </el-form-item>
        <el-form-item label="sys_user ID">
          <el-input v-model="form.sysUserId"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import baseService from '@/service/baseService';

const characterList = ref([]);
const dialogVisible = ref(false);
const dialogType = ref('create');
const form = ref({
  id: null,
  name: '',
  profession: '',
  fame: 0,
  simulatedDamage: 0,
  level: 0,
  attackSpeed: 0,
  movementSpeed: 0,
  sysUserId: null // 新增 sys_user ID 字段
});

const fetchCharacterList = async () => {
  try {
    const res = await baseService.get('/dnf/character/list');
    characterList.value = res.data;
  } catch (error) {
    ElMessage.error('获取角色列表失败');
  }
};

const openDialog = (type: string, row?: any) => {
  dialogType.value = type;
  if (type === 'update' && row) {
    form.value = { ...row };
  } else {
    form.value = {
      id: null,
      name: '',
      profession: '',
      fame: 0,
      simulatedDamage: 0,
      level: 0,
      attackSpeed: 0,
      movementSpeed: 0
    };
  }
  dialogVisible.value = true;
};

const submitForm = async () => {
  try {
    if (dialogType.value === 'create') {
      await baseService.post('/dnf/character/save', form.value);
      ElMessage.success('新增角色成功');
    } else {
      await baseService.put('/dnf/character/update', form.value);
      ElMessage.success('编辑角色成功');
    }
    dialogVisible.value = false;
    fetchCharacterList();
  } catch (error) {
    ElMessage.error(dialogType.value === 'create' ? '新增角色失败' : '编辑角色失败');
  }
};

const deleteCharacter = async (id: number) => {
  try {
    await baseService.delete(`/dnf/character/delete`, { data: { ids: [id] } });
    ElMessage.success('删除角色成功');
    fetchCharacterList();
  } catch (error) {
    ElMessage.error('删除角色失败');
  }
};

onMounted(() => {
  fetchCharacterList();
});
</script>

<style scoped>
/* 可添加页面样式 */
</style>