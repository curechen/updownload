<template>
  <div class="login-box">
    <h1>LOGIN</h1>
    <form>
      <div class="inputBox">
        <!-- required必须在提交之前写入东西 -->
        <input type="text" required class="phone" v-model="username" />
        <label>Username</label>
      </div>
      <div class="inputBox">
        <input type="password" required class="password" v-model="password" />
        <label>Password</label>
      </div>
      <input type="button" value="Login" class="login" @click="loginClick" />
      <input
        type="button"
        value="Register"
        class="login"
        @click="registerClick"
      />
    </form>
  </div>
</template>

<script>
import { accountLogin } from 'network/login'

export default {
  name: 'Login',
  data() {
    return {
      username: 'czy',
      password: 123,
      
    }
  },
  methods: {
    loginClick() {
      accountLogin(this.username, this.password).then((res) => {
        console.log(res.code)
        if (res.code === 20000) {
          // this.$store.commit('setUserId', res.account.id)
          // window.sessionStorage.setItem('userId', res.account.id)
          this.$router.push('/file/' + res.data.token)
        }
      })
    },
    registerClick() {
      this.$router.push('/register')
    }
  },
  mounted () {
    setTimeout(() => {
      console.log(this.username)
    }, 10)
  }
}
</script>

<style scoped>
/* 动画并不会改变原有属性，在动画结束后，属性还是会根据元素里的值而规定 */
@keyframes mymove {
  from {
    transform: translate(-50%, -5%);
    opacity: 0;
  }
  to {
    opacity: 0.9;
    /* top: 18%; */
  }
}

.login-box {
  position: absolute;
  /* 使登录框居中 */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* 透明度 */
  opacity: 0.9;
  background-color: #00000080;
  width: 330px;
  height: 180px;
  padding: 40px;
  /* box-sizing: border-box; */
  /* 水平 垂直 模糊程度 颜色 */
  box-shadow: 10px 10px 25px;
  /* 圆角 */
  border-radius: 15px;
  animation: mymove 1s;
  z-index: 11;
}

/* .login-box-show{
    transform: translate(-50%, -50%);
    opacity: 1;
} */
.login-box h1 {
  margin-top: -10px;
  margin-bottom: 15px;
  color: white;
  text-align: center;
}

/* .inputBox不能设置为absolute，会使它也脱离正常的文档流，两个并列的inputBox会重合 */
.login-box .inputBox {
  position: relative;
  padding: 12px 0;
}

.login-box .inputBox input {
  width: 100%;
  height: 25px;
  border: 0px;
  border-bottom: 1px solid;

  font-size: 16px;
  color: white;
  /* 鼠标点击时外面出现的线 */
  outline: none;
  background: transparent;
}

/* label和input都是内联元素 */
.login-box .inputBox label {
  /* 会找设置了定位的最近父元素 */
  /* 设置为absolute会脱离正常的文档流 */
  position: absolute;
  top: 0;
  left: 0;
  padding: 14px 0;
  font-size: 16px;
  color: white;
  /* 鼠标点击事件设置为无，否则鼠标在label标签上点击无法输入文字 */
  pointer-events: none;
  transition: 0.5s;
}
/* valid合法的意思 */
.login-box .inputBox input:focus ~ label,
.login-box .inputBox input:valid ~ label {
  top: -20px;
}

/* input是内联元素及行内元素，若想行内居中得设置为块元素 */
.login-box .login {
  /* background-image: linear-gradient(120deg, #f6d365 0%, #fda085 100%); */
  background-image: linear-gradient(120deg, #fdfbfb 0%, #ebedee 100%);
  opacity: 0.8;
  font-size: 16px;
  color: black;
  border: 0px;
  outline: none;
  /* padding: 5px 25px; */
  width: 75px;
  height: 30px;
  cursor: pointer;
  border-radius: 10px;
  margin: 20px 45px 0;
  display: inline-block;
}
</style>
