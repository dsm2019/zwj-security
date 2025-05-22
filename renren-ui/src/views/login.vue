<template>
  <div class="rr-login">
    <!-- 宇宙飞船元素 -->
    <div class="spaceship spaceship1"></div>
    <div class="spaceship spaceship2"></div>
    <div class="spaceship spaceship3"></div>
    <div class="rr-login-wrap">
      <div class="rr-login-top hidden-sm-and-down">
        <p class="rr-login-top-title">带你飞系统</p>
      </div>

      <div class="rr-login-bottom">
        <div class="rr-login-bottom-main">
          <h4 class="rr-login-bottom-main-title">登录</h4>
          <el-form ref="formRef" label-width="80px" :status-icon="true" :model="login" :rules="rules" @keyup.enter="onLogin">
            <el-form-item label-width="0" prop="username">
              <el-input 
                v-model="login.username" 
                placeholder="用户名" 
                prefix-icon="user" 
                autocomplete="off"
                @focus="handleInputFocus('username')"
                @click="handleInputClick('username')"
              ></el-input>
            </el-form-item>
            <el-form-item label-width="0" prop="password">
              <el-input 
                placeholder="密码" 
                v-model="login.password" 
                prefix-icon="lock" 
                autocomplete="off" 
                show-password
                @focus="handleInputFocus('password')"
                @click="handleInputClick('password')"
              ></el-input>
            </el-form-item>
            <el-form-item label-width="0" prop="captcha">
              <el-space class="rr-login-bottom-main-code">
                <el-input 
                  v-model="login.captcha" 
                  placeholder="验证码" 
                  prefix-icon="first-aid-kit"
                  @focus="handleInputFocus('captcha')"
                  @click="handleInputClick('captcha')"
                ></el-input>
                <img style="vertical-align: middle; height: 40px; cursor: pointer" :src="state.captchaUrl" @click="onRefreshCode" alt="" />
              </el-space>
            </el-form-item>
            <el-form-item label-width="0">
              <el-button type="primary" size="small" :disabled="state.loading" @click="onLogin" class="rr-login-right-main-btn"> 登录 </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <div class="login-footer">
      <p><a href="https://www.renren.io/enterprise" target="_blank">企业版</a> | <a href="https://www.renren.io/cloud" target="_blank">微服务版</a></p>
      <p><a href="https://www.renren.io/" target="_blank">人人开源</a>{{ state.year }} © renren.io</p>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { CacheToken } from "@/constants/cacheKey";
import baseService from "@/service/baseService";
import { setCache } from "@/utils/cache";
import { ElMessage } from "element-plus";
import { getUuid } from "@/utils/utils";
import app from "@/constants/app";
import SvgIcon from "@/components/base/svg-icon/index";
import { useAppStore } from "@/store";
import { useRouter } from "vue-router";

const store = useAppStore();
const router = useRouter();

const state = reactive({
  captchaUrl: "",
  loading: false,
  year: new Date().getFullYear()
});

const login = reactive({ username: "", password: "", captcha: "", uuid: "" });

onMounted(() => {
  //清理数据
  store.logout();
  getCaptchaUrl();
});
const formRef = ref();

const rules = ref({
  username: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  password: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  captcha: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});

const getCaptchaUrl = () => {
  login.uuid = getUuid();
  state.captchaUrl = `${app.api}/captcha?uuid=${login.uuid}`;
};

const onRefreshCode = () => {
  getCaptchaUrl();
};

const onLogin = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      state.loading = true;
      baseService
        .post("/login", login)
        .then((res) => {
          state.loading = false;
          if (res.code === 0) {
            setCache(CacheToken, res.data, true);
            ElMessage.success("登录成功");

            localStorage.setItem("token", res.data.token);
            // localStorage.setItem(USER_KEY, JSON.stringify(data.user));
            //
            // 触发事件通知其他tab页
            // localStorage.setItem(EVENT_KEY, Date.now().toString())

            router.push("/");
          } else {
            ElMessage.error(res.msg);
          }
        })
        .catch(() => {
          state.loading = false;
          onRefreshCode();
        });
    }
  });
};

// 添加调试方法
const handleInputFocus = (field: string) => {
  console.log(`输入框 ${field} 获得焦点`);
};

const handleInputClick = (field: string) => {
  console.log(`点击了输入框 ${field}`);
};
</script>

<style lang="less" scoped>
@import url("@/assets/theme/base.less");

.rr-login {
  width: 100vw;
  height: 100vh;
  background: #000; /* 星空背景通常为黑色 */
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

/* 宇宙飞船通用样式 */
.spaceship {
  position: absolute;
  width: 50px; /* 飞船大小 */
  height: 50px;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50"><path d="M25 0 L50 50 H0 Z" fill="%23fff"/></svg>'); /* 简单的三角形飞船 */
  background-size: contain;
  background-repeat: no-repeat;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}

/* 第一架飞船动画 */
.spaceship1 {
  top: 10%;
  left: -50px;
  animation-name: fly1;
  animation-duration: 15s;
}

@keyframes fly1 {
  from {
    transform: translateX(-50px);
  }
  to {
    transform: translateX(calc(100vw + 50px));
  }
}

/* 第二架飞船动画 */
.spaceship2 {
  top: 50%;
  left: -50px;
  animation-name: fly2;
  animation-duration: 20s;
  animation-delay: 5s; /* 延迟 5 秒出发 */
}

@keyframes fly2 {
  from {
    transform: translateX(-50px) rotate(10deg);
  }
  to {
    transform: translateX(calc(100vw + 50px)) rotate(10deg);
  }
}

/* 第三架飞船动画 */
.spaceship3 {
  top: 90%;
  left: -50px;
  animation-name: fly3;
  animation-duration: 25s;
  animation-delay: 10s; /* 延迟 10 秒出发 */
}

@keyframes fly3 {
  from {
    transform: translateX(-50px) rotate(-10deg);
  }
  to {
    transform: translateX(calc(100vw + 50px)) rotate(-10deg);
  }
}

.rr-login-wrap {
  width: 380px;
  background: #fff;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.rr-login-top-title {
  color: #333;
  text-align: center;
  font-size: 28px;
  margin-bottom: 30px;
}

.rr-login-bottom-main-title {
  color: #333;
  text-align: center;
  font-size: 24px;
  margin-bottom: 30px;
}

.rr-login-bottom-main .el-form-item {
  margin-bottom: 25px;
}

.rr-login-bottom-main .el-input__inner {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  color: #606266;
  height: 40px;
  padding: 0 15px;
  transition: border-color 0.3s ease;
}

.rr-login-bottom-main .el-input__inner:focus {
  border-color: #409EFF;
  outline: none;
}

.rr-login-bottom-main .el-input__prefix {
  color: #909399;
}

.rr-login-bottom-main-code {
  display: flex;
  align-items: center;
  gap: 15px;
}

.rr-login-bottom-main-code img {
  height: 40px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.rr-login-right-main-btn {
  width: 100%;
  height: 40px;
  background: #409EFF;
  border: none;
  border-radius: 4px;
  color: #fff;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.rr-login-right-main-btn:hover {
  background: #66b1ff;
}

.login-footer {
  position: absolute;
  bottom: 20px;
  color: #909399;
  text-align: center;
}

.login-footer a {
  color: #909399;
  text-decoration: none;
  transition: color 0.3s ease;
}

.login-footer a:hover {
  color: #409EFF;
}
</style>
