
<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>个人中心 </el-breadcrumb-item>
            <el-breadcrumb-item>密码修改</el-breadcrumb-item> </el-breadcrumb
        ><br />
        <div id="edit_pwd">
            <el-form
                :model="pwdForm"
                :rules="rules"
                ref="pwdForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <i class="iconfont icon-r-lock" style="font-size: 32px">
                    <b style="font-size: 28px">密码修改</b></i
                >
                <br />
                <br />
                <el-form-item label="账号">
                    <el-input disabled v-model="pwdForm.username"></el-input>
                </el-form-item>
                <el-form-item label="旧密码" prop="oldPwd">
                    <el-input
                        type="password"
                        v-model="pwdForm.oldPwd"
                    ></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPwd">
                    <el-input
                        type="password"
                        v-model="pwdForm.newPwd"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitForm('pwdForm')"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-yes"
                            style="font-size: 18px"
                        ></i>
                        确定</el-button
                    >
                    <el-button
                        @click="resetForm('pwdForm')"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-refresh"
                            style="font-size: 18px"
                        ></i>
                        重置</el-button
                    >
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import { loginEmp, removeToken } from "@/assets/js/auth";
import Cookies from "js-cookie";
import { ajaxPost, popup } from "@/assets/js/common";

export default {
    data() {
        return {
            pwdForm: {},
            rules: {
                newPwd: [
                    {
                        required: true,
                        message: "新密码不能为空",
                        trigger: "blur",
                    },
                    {
                        min: 6,
                        max: 8,
                        message: "新密码长度为6-8位",
                        trigger: "blur",
                    },
                ],
                oldPwd: [
                    {
                        required: true,
                        message: "旧密码不能为空",
                        trigger: "blur",
                    },
                    {
                        min: 6,
                        max: 8,
                        message: "旧密码长度为6-8位",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        init() {
            this.pwdForm.username = loginEmp().username;
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$confirm("确定修改密码?", "警示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    })
                        .then(() => {
                            ajaxPost("/personal/edit_pwd", this.pwdForm).then(
                                (res) => {
                                    res = res.data;
                                    if (res.code == 200) {
                                        popup("密码修改成功,请重新登录...");
                                        this.pwdForm = {
                                            username: this.pwdForm.username,
                                        };
                                        Cookies.remove("employee");
                                        removeToken();
                                        this.$router.push("/");
                                    } else {
                                        popup(res.msg, "error");
                                    }
                                }
                            );
                        })
                        .catch(() => {
                            this.$message({
                                type: "info",
                                message: "已取消操作",
                            });
                        });
                }
            });
        },
    },
    created() {
        this.init();
    },
};
</script>
<style>
#edit_pwd {
    width: 50%;
    margin: 50px auto;
    background-color: #ffffff;
    padding: 20px;
}
</style>
