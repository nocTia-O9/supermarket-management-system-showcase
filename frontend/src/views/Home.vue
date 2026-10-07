
<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
        </el-breadcrumb><br />

        <div id="home_bg">
            <h1 style="color: white;font-size: 28px;text-align: center;width: 1024px;">欢迎访问超市管理系统</h1>
        </div>

    </div>
</template>
<script>

export default {
    data() {
        return {
        };
    },
    methods: {

    },
    mounted() {
    },
};
</script>
<style>
#home_bg {
    top: 0;
    left: 0;
    overflow-y: auto;
    height: 80vh;
    background-image: url("../assets/55.png");
    background-repeat: no-repeat;
  text-align: center;
}
</style>
