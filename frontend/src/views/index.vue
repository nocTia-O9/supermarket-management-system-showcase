<template>
    <el-container>
        <el-header style="height: 80px">
            <el-row>
                <el-col :span="12" style="margin-top: 20px">
                    <i
                        style="color: white; font-size: 32px"
                        class="iconfont icon-r-building"
                    >
                        <b style="font-size: 26px"> 超市管理系统</b></i
                    >
                </el-col>
                <el-col
                    :span="12"
                    style="text-align: right; margin-top: 15px; cursor: pointer"
                >
                    <el-dropdown>
                        <el-avatar
                            :size="50"
                            shape="square"
                            :src="BaseApi + circleUrl"
                        ></el-avatar>
                        <b
                            style="
                                font-size: 24px;
                                color: white;
                                margin-top: -10px;
                            "
                        >
                            {{isAdmin ? "管理员 " : "用户 "}}
                            {{ loginName }}</b
                        >

                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="informationBtn">个人资料完善</el-dropdown-item>
                            <el-dropdown-item @click.native="empExit">退出</el-dropdown-item>
                            <el-dropdown-item @click.native="logoutVisable = true">注销</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-col>
            </el-row>
        </el-header>
        <!--注销账户-->
        <el-dialog title="注销账户" :visible.sync="logoutVisable" width="70%">
            <el-form
                :model="logoutform"
                :rules="rules"
                ref="logoutform"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="内容" prop="content">
                    <el-input
                        v-model="logoutform.content"
                        placeholder="请填写“本人确定注销”"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="logoutSubmit('logoutform')"
                        >确 定</el-button
                    >
                    <el-button @click="logoutCel('logoutform')"
                        >取 消</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-container>
            <el-aside width="200px" style="overflow-y: hidden;min-height: 900px;">
                <el-menu
                    background-color="#6495ED"
                    text-color="white"
                    :router="true"
                    :unique-opened="true"
                    active-text-color="#FFDEAD"
                >
                    <el-submenu
                        v-for="item in menu_catalogs"
                        :key="item.id"
                        :index="item.id + ''"
                    >
                        <template slot="title">
                            <i
                                :class="item.icon"
                                style="font-size: 26px; color: white"
                            >
                                <b style="font-size: 18px"> {{ item.label }}</b>
                            </i>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item
                                v-for="c in item.children"
                                :key="c.id"
                                :index="c.purl"
                            >
                                <i :class="c.icon" style="font-size: 24px"> </i>
                                <b style="font-size: 16px"> {{ c.label }}</b>
                            </el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-main>
                <router-view />
            </el-main>
        </el-container>
    </el-container>
</template>
<script>
import { clearCookie, loginEmp } from "@/assets/js/auth";
import { ajaxGet, ajaxPost, popup } from "@/assets/js/common";
import Cookies from "js-cookie";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            logoutVisable: false,
            loginName: "",
            isAdmin: "",
            logoutform: {},
            circleUrl: loginEmp().headImg,
            menu_catalogs: [],
            rules: {
                content: [
                    {
                        required: true,
                        message: "内容不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        init() {
            this.isAdmin = loginEmp().isAdmin;
            this.loginName = loginEmp().nickName;
            ajaxGet("/empMenu", {}).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.menu_catalogs = res.data;
                }
            });
        },
        /*个人资料*/
        informationBtn() {
            this.$router.push("/personal/information");
            popup("请完善个人的资料");
        },
        /*账户退出*/
        empExit() {
            this.$confirm("确定要退出系统?", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    ajaxGet("/exit", this.pwdForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("成功退出系统...");
                            clearCookie("employee");
                            clearCookie("token");
                            this.$router.push("/");
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消操作",
                    });
                });
        },
        /*注销账户*/
        logoutCel(formName) {
            this.$refs[formName].resetFields();
            this.logoutform = {};
            this.logoutVisable = false;
        },
        logoutSubmit(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    ajaxPost("/logout", {
                        content: this.logoutform.content,
                    }).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("注销成功");
                            clearCookie("employee");
                            clearCookie("token");
                            this.logoutVisable = false;
                            this.$router.push("/");
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
    },
    mounted() {
        this.init();
    },
};
</script>
<style>
.avatar {
    display: inline-block;
    width: 200px;
    float: right;
}

.personalFul {
    background-color: #ffffff;
    position: absolute;
    z-index: 999999;
    top: 58px;
    right: 20px;
    width: 151px;
}

/* .personalFul ul > li {
    margin: 1px auto;
    padding: 0px;
    height: 20px;
    color: #ffffff;
    background-color: #6495ED;
    list-style-type: none;
} */

.personalFul ul > li:hover {
    background-color: #fc4316;
}

.el-menu-item i {
    color: white;
}
.el-submenu__title i {
    color: white;
}
</style>
