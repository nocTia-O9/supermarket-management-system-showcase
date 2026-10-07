<template>
    <div id="information">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>个人中心 </el-breadcrumb-item>
            <el-breadcrumb-item>个人资料</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <i class="iconfont icon-r-paper" style="font-size: 32px">
            <b style="font-size: 28px">完善个人资料</b></i
        >
        <br />
        <br />
        <el-form
            :model="informationForm"
            :rules="rules"
            ref="informationForm"
            label-width="100px"
            class="demo-ruleForm"
        >
            <el-row>
                <el-col :span="12">
                    <el-form-item style="width: 40%">
                        <el-upload
                            class="avatar-uploader"
                            action="http://localhost:9291/personnel_management/employee/uploadImg"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                        >
                            <img
                                v-if="imageUrl"
                                :src="BaseApi + imageUrl"
                                class="avatar"
                            />
                            <i
                                v-else
                                class="el-icon-plus avatar-uploader-icon"
                            ></i>
                        </el-upload>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item
                        style="width: 40%"
                        label="电话号码："
                        prop="username"
                    >
                        <el-input
                            max="11"
                            v-model="informationForm.username"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item
                        style="width: 40%"
                        label="姓名："
                        prop="nickName"
                    >
                        <el-input
                            v-model="informationForm.nickName"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item
                        style="width: 40%"
                        label="身份证号："
                        prop="idCard"
                    >
                        <el-input
                            v-model="informationForm.idCard"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item style="width: 40%" label="年龄：">
                        <el-input
                            type="number"
                            min="18"
                            max="70"
                            v-model="informationForm.age"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item
                        style="width: 40%"
                        label="所属部门："
                    >
                        <el-select
                            disabled
                            style="width: 200px"
                            v-model="informationForm.deptId"
                            placeholder="请选择部门"
                            filterable
                            @change="$forceUpdate()"
                            clearable
                        >
                            <el-option
                                v-for="item in options"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id"
                            >
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item style="width: 40%" label="住址：">
                        <el-input
                            type="text"
                            v-model="informationForm.address"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item style="width: 40%" label="备注：">
                        <el-input
                            type="textarea"
                            cols="50"
                            rows="3"
                            v-model="informationForm.info"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item style="width: 40%" label="性别：">
                        <el-select
                            style="width: 200px"
                            v-model="informationForm.sex"
                            @change="$forceUpdate()"
                            placeholder="请选择性别"
                            clearable
                        >
                            <el-option label="女" value="0"></el-option>
                            <el-option label="男" value="1"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item>
                <el-button
                    type="primary"
                    @click="submitInformationForm('informationForm')"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-yes" style="font-size: 18px"></i>
                    提交</el-button
                >
                <el-button
                    @click="resetForm('informationForm')"
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
</template>
<script>
import { listByQo } from "@/api/personnel_management/department/deptApi";
import { popup } from "@/assets/js/common";
import { queryInformation } from "@/api/personal/personApi";
import { update } from "@/api/personnel_management/employee/empApi";
import { loginEmp } from "@/assets/js/auth";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            loginEid: loginEmp().id,
            informationForm: {},
            imageUrl: "",
            options: [],
            rules: {
                username: [
                    {
                        required: true,
                        message: "手机号不能为空",
                        trigger: "blur",
                    },
                ],
                nickName: [
                    {
                        required: true,
                        message: "姓名不能为空",
                        trigger: "blur",
                    },
                ],
                idCard: [
                    {
                        required: true,
                        message: "身份证号不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        deptAll() {
            listByQo({}).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        init() {
            queryInformation().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.informationForm = { ...res.data };
                    this.imageUrl = this.informationForm.headImg;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        submitInformationForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    update(this.informationForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("更新成功");
                        } else {
                            popup(res.msg, "error");
                        }
                        this.init();
                    });
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
            this.init();
        },
        /*上传头像*/
        handleAvatarSuccess(res) {
            this.informationForm.headImg = res.url;
            this.imageUrl = res.url;
        },
        beforeAvatarUpload(file) {
            const isJPG =
                file.type === "image/jpeg" || file.type === "image/png";
            const isLt2M = file.size / 1024 / 1024 < 3;
            if (!isJPG) {
                this.$message.error("上传头像图片只能是 JPG或PNG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传头像图片大小不能超过 3MB!");
            }
            return isJPG && isLt2M;
        },
    },
    mounted() {
        this.imageUrl = "";
        this.deptAll();
        this.init();
    },
};
</script>
