<template>
    <div id="employee_index">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>人事管理 </el-breadcrumb-item>
            <el-breadcrumb-item>员工管理</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8">
                <el-input
                    style="width: 95%"
                    placeholder="用户名"
                    v-model="searchForm.username"
                />
            </el-col>
            <el-col :span="8">
                <el-input
                    style="width: 95%"
                    placeholder="姓名"
                    v-model="searchForm.nickName"
                />
            </el-col>
            <el-col :span="8">
                <el-input
                    style="width: 95%"
                    type="number"
                    :min="0"
                    :max="100"
                    placeholder="年龄"
                    v-model="searchForm.age"
                />
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="12">
                <el-input
                    style="width: 97%"
                    placeholder="住址"
                    v-model="searchForm.address"
                />
            </el-col>
            <el-col :span="6">
                <el-select
                    v-model="searchForm.sex"
                    @change="$forceUpdate()"
                    placeholder="性别"
                    clearable
                >
                    <el-option label="女" value="0"></el-option>
                    <el-option label="男" value="1"></el-option>
                </el-select>
            </el-col>
            <el-col :span="6">
                <el-select
                    v-model="searchForm.deptId"
                    placeholder="部门"
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
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="24" style="text-align: left">
                <el-button
                    type="primary"
                    @click="submitSearchForm"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-find" style="font-size: 18px">
                    </i>
                    搜索</el-button
                >
                <el-button
                    type="success"
                    @click="newVisable = true"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-add" style="font-size: 18px"> </i>
                    新建员工
                </el-button>
            </el-col>
        </el-row>
        <br />
        <!--表格数据-->
        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column prop="username" width="130" label="用户名">
                </el-table-column>
                <el-table-column width="100" prop="nickName" label="姓名">
                </el-table-column>
                <el-table-column width="50" prop="sex" label="性别">
                    <template v-slot="scope">
                        {{ scope.row.sex == "1" ? "男" : "女" }}
                    </template>
                </el-table-column>
                <el-table-column width="50" prop="age" label="年龄">
                </el-table-column>
                <el-table-column
                    width="130"
                    :show-overflow-tooltip="true"
                    prop="address"
                    label="住址"
                >
                </el-table-column>
                <el-table-column
                    width="130"
                    :show-overflow-tooltip="true"
                    prop="deptName"
                    label="所属部门"
                >
                </el-table-column>
                <el-table-column
                    width="120"
                    :show-overflow-tooltip="true"
                    prop="createTime"
                    label="入职时间"
                >
                </el-table-column>
                <el-table-column
                    width="120"
                    :show-overflow-tooltip="true"
                    prop="leaveTime"
                    label="离职时间"
                >
                </el-table-column>
                <el-table-column width="120" prop="state" label="状态">
                    <template v-slot="scope">
                        <el-tag v-if="scope.row.state == '0'" type="success"
                            >在职</el-tag
                        >
                        <el-tag v-else type="danger">离职</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="570" fixed="right">
                    <template v-slot="scope">
                        <el-button
                            style="font-size: 16px"
                            type="primary"
                            @click="detailBtn(scope.row)"
                        >
                            <i
                                style="font-size: 16px"
                                class="iconfont icon-r-find"
                            >
                            </i>
                            详情
                        </el-button>
                        <el-button
                            style="font-size: 16px"
                            type="success"
                            @click="editBtn(scope.row.id)"
                        >
                            <i
                                style="font-size: 16px"
                                class="iconfont icon-r-edit"
                            >
                            </i>
                            修改</el-button
                        >
                        <el-button
                            style="font-size: 16px"
                            type="danger"
                            @click="delBtn(scope.row)"
                        >
                            <i
                                style="font-size: 16px"
                                class="iconfont icon-r-delete"
                            >
                            </i>
                            删除</el-button
                        >
                        <el-button
                            style="font-size: 16px"
                            type="warning"
                            @click="empRoleBtn(scope.row.id)"
                        >
                            <i
                                style="font-size: 16px"
                                class="iconfont icon-r-user1"
                            >
                            </i>
                            职务</el-button
                        >

                        <el-button
                            style="font-size: 16px"
                            type="warning"
                            @click="resetPwd(scope.row)"
                        >
                            <i
                                style="font-size: 16px"
                                class="iconfont icon-r-lock"
                            >
                            </i>
                            重置密码</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
            <div style="margin: 10px 0 15px 0">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.sync="searchForm.currentPage"
                    :page-sizes="[5, 10, 20, 50]"
                    :page-size="searchForm.pageSize"
                    layout="total,sizes, prev, pager, next,jumper"
                    :total="searchForm.total"
                >
                </el-pagination>
            </div>
        </div>
        <!--用户详情-->
        <el-dialog :visible.sync="detailVisable" width="50%">
            <el-descriptions :model="detailForm" column="2" title="员工信息">
                <el-descriptions-item label="用户名">{{
                    detailForm.username
                }}</el-descriptions-item>
                <el-descriptions-item label="性别">{{
                    detailForm.sex == "1" ? "男" : "女"
                }}</el-descriptions-item>
                <el-descriptions-item label="年龄">{{
                    detailForm.age
                }}</el-descriptions-item>
                <el-descriptions-item label="住址">{{
                    detailForm.address
                }}</el-descriptions-item>
                <el-descriptions-item label="昵称">{{
                    detailForm.nickName
                }}</el-descriptions-item>
                <el-descriptions-item label="头像"
                    ><img :src="BaseApi + detailForm.headImg" width="30px"
                /></el-descriptions-item>
                <el-descriptions-item label="状态">
                    <el-tag v-if="detailForm.state == '0'" type="success"
                        >在职</el-tag
                    >
                    <el-tag v-else type="danger">离职</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="系统管理员">
                    <el-tag v-if="detailForm.isAdmin == true" type="success"
                        >是</el-tag
                    >
                    <el-tag v-else type="danger">否</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="描述"> </el-descriptions-item>
                <el-descriptions-item label="录入者">{{
                    detailForm.createby
                }}</el-descriptions-item>
                <el-descriptions-item label="所属部门">
                    <el-tag v-if="detailForm.deptName" type="warning">{{
                        detailForm.deptName
                    }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="身份证号">{{
                    detailForm.idCard
                }}</el-descriptions-item>
                <el-descriptions-item label="入职时间">{{
                    detailForm.createTime
                }}</el-descriptions-item>
                <el-descriptions-item
                    v-if="detailForm.state != '0'"
                    label="离职时间"
                    >{{ detailForm.leaveTime }}
                </el-descriptions-item>
                <el-descriptions-item label="角色">
                    <div style="flex-wrap: wrap;display: flex;">

                    <el-tag
                        size="mini"
                        type="success"
                        :key="item.id"
                        v-for="item in detailForm.roleNames"
                        >{{ item }}</el-tag
                    >
                    </div>
                </el-descriptions-item>
            </el-descriptions>
            <el-button type="info" @click="detail_down">关闭</el-button>
        </el-dialog>
        <!--录入-->
        <el-dialog
            title="录入用户"
            :visible.sync="newVisable"
            size="mini"
            @close="dialog_imgClose"
            label-width="200"
            width="50%"
        >
            <el-form
                :model="newForm"
                :rules="rules"
                ref="newForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="头像：">
                            <el-upload
                                class="avatar-uploader"
                                :action="
                                    BaseApi +
                                    '/personnel_management/employee/uploadImg'
                                "
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
                        <el-form-item label="电话号码：" prop="username">
                            <el-input
                                max="11"
                                v-model="newForm.username"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="姓名：" prop="nickName">
                            <el-input v-model="newForm.nickName"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="身份证号：" prop="idCard">
                            <el-input v-model="newForm.idCard"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="年龄：">
                            <el-input
                                type="number"
                                min="18"
                                max="70"
                                v-model="newForm.age"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="所属部门：">
                            <el-select
                                style="width: 200px"
                                v-model="newForm.deptId"
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
                        <el-form-item label="住址：">
                            <el-input
                                type="text"
                                v-model="newForm.address"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="员工备注：">
                            <textarea
                                cols="20"
                                rows="3"
                                v-model="newForm.info"
                            ></textarea>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="性别：">
                            <el-select
                                style="width: 200px"
                                v-model="newForm.sex"
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
                    <el-button type="primary" @click="submitNewForm('newForm')"
                        >提交</el-button
                    >
                    <el-button @click="saveCancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--修改-->
        <el-dialog
            title="修改员工"
            @close="dialog_imgClose"
            :visible.sync="editVisable"
            size="mini"
            label-width="200"
            width="50%"
        >
            <el-form
                :model="editForm"
                :rules="rules"
                ref="editForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="头像：">
                            <el-upload
                                class="avatar-uploader"
                                :action="
                                    BaseApi +
                                    '/personnel_management/employee/uploadImg'
                                "
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
                        <el-form-item label="电话号码：" prop="username">
                            <el-input
                                max="11"
                                v-model="editForm.username"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="姓名：" prop="nickName">
                            <el-input v-model="editForm.nickName"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="身份证号：" prop="idCard">
                            <el-input v-model="editForm.idCard"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="年龄：">
                            <el-input
                                type="number"
                                min="18"
                                max="70"
                                v-model="editForm.age"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="所属部门：">
                            <el-select
                                style="width: 200px"
                                v-model="editForm.deptId"
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
                        <el-form-item label="住址：">
                            <el-input
                                type="text"
                                v-model="editForm.address"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="员工备注：">
                            <textarea
                                cols="20"
                                rows="3"
                                v-model="editForm.info"
                            ></textarea>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="性别：">
                            <el-select
                                style="width: 200px"
                                v-model="editForm.sex"
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
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="状态：">
                            <el-select
                                style="width: 200px"
                                v-model="editForm.state"
                                placeholder="请选择状态"
                                clearable
                            >
                                <el-option label="在职" value="0"></el-option>
                                <el-option label="离职" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-form-item>
                    <el-button
                        type="primary"
                        style="font-size: 18px;"
                        @click="submitEditForm('editForm')"
                        ><i class="iconfont icon-r-yes" style="font-size: 18px;"> </i> 提交</el-button
                    >
                    <el-button @click="editCancel" style="font-size: 18px;">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--角色列表-->
        <el-dialog title="分配职务" :visible.sync="empRoleVisable">
            <div>
                <label>已有职务：</label>
                <el-select
                    disabled
                    size="small"
                    v-model="hasRoleIds"
                    :multiple="true"
                    filterable
                    placeholder="没有职务"
                >
                    <el-option
                        v-for="item in allRoles"
                        :key="item.id"
                        :label="item.label"
                        :value="item.id"
                    >
                    </el-option>
                </el-select>
            </div>
            <div>
                <label>更改职务：</label>
                <el-select
                    size="small"
                    name="roleIds"
                    v-model="empRoleForm.empRoleIds"
                    :multiple="true"
                    @change="$forceUpdate()"
                    filterable
                    placeholder="请选择职务"
                >
                    <el-option
                        v-for="item in allRoles"
                        :key="item.id"
                        :label="item.label"
                        :value="item.id"
                    >
                    </el-option>
                </el-select>
            </div>
            <br>
            <el-button type="primary" @click="submitRoleBtn" style="font-size: 18px;">
                <i class="iconfont icon-r-yes" style="font-size: 18px;"> </i> 提交</el-button>
            <el-button @click="roleBtnCancel" style="font-size: 18px;">取消</el-button>
        </el-dialog>
        <!--重置密码-->
        <el-dialog
            title="重置密码"
            v-model="resetPwdForm"
            :visible.sync="resetPwdVisable"
        >
            <el-form>
                <el-form-item label="密码：">
                    <el-input
                        style="width: 75%; margin: 0 auto; padding: 0"
                        type="password"
                        v-model="resetPwdForm.code"
                        placeholder="请输入密码"
                    ></el-input>
                </el-form-item>
                <el-button type="primary" @click="submitResetPwd" style="font-size: 18px;"
                    >
                    <i class="iconfont icon-r-yes" style="font-size: 18px;"> </i>
                     确定</el-button
                >
                <el-button @click="resetPwdVisable = false" style="font-size: 18px;">取消</el-button>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { listByQo } from "@/api/personnel_management/department/deptApi";
import { popup } from "@/assets/js/common";
import {
    deactivate,
    detail,
    editBtnClick,
    pageByQo,
    resetPwd,
    save,
    update,
} from "@/api/personnel_management/employee/empApi";
import {
    getAllRole,
    queryRoleIdsByEid,
    saveRoleEmp,
} from "@/api/system/role/roleApi";
import { clearCookie, loginEmp } from "@/assets/js/auth";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            detailVisable: false,
            newVisable: false,
            editVisable: false,
            empRoleForm: {},
            hasRoleIds: [],
            allRoles: [],
            detailForm: {},
            searchForm: {},
            editForm: {},
            options: [],
            tableData: [],
            resetPwdVisable: false,
            newForm: {},
            empRoleVisable: false,
            checked_keys: [],
            roles: [],
            resetPwdForm: {},
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
            /*上传头像*/
            imageUrl: "",
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
        getRoleAll() {
            getAllRole().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.allRoles = res.data;
                } else {
                    popup(res.msg, "warning");
                }
            });
        },
        init() {
            pageByQo(this.searchForm).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.tableData = res.data.records;
                    this.searchForm.total = res.data.total;
                    this.searchForm.pageSize = res.data.size;
                    this.searchForm.currentPage = res.data.current;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        /*分页*/
        handleSizeChange(val) {
            this.searchForm.pageSize = val;
            this.init();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.searchForm.currentPage = val;
            this.init();
            console.log(`当前页: ${val}`);
        },
        submitSearchForm() {
            this.init();
        },
        detailBtn(row) {
            detail(row.id).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.detailForm = res.data;
                    this.detailForm.deptName = row.deptName;
                    this.detailVisable = true;
                } else {
                    popup(res.msg, "error");
                }
            });
            console.log(this.detailForm);
        },
        detail_down() {
            this.detailForm = {};
            this.detailVisable = false;
        },
        /*上传头像*/
        handleAvatarSuccess(res) {
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
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.newForm.headImg = this.imageUrl;
                    save(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.newForm = {};
                            this.imageUrl = "";
                            this.newVisable = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        saveCancel() {
            this.newVisable = false;
            this.imageUrl = "";
            this.$refs["newForm"].resetFields();
        },
        /*修改员工信息*/
        editCancel() {
            this.editVisable = false;
            this.imageUrl = "";
            this.$refs["editForm"].resetFields();
            this.editForm = {};
        },
        editBtn(id) {
            editBtnClick(id).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.editForm = { ...res.data };
                    console.log(this.editForm);
                    this.imageUrl = this.editForm.headImg;
                    this.editVisable = true;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        submitEditForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.editForm.headImg = this.imageUrl;
                    update(this.editForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.editForm = {};
                            this.imageUrl = "";
                            this.editVisable = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        /*离职*/
        delBtn(row) {
            this.$confirm("确定要将该员工办理离职?", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    if (row.state == "1") {
                        popup("已是离职状态", "warning");
                        return;
                    }
                    deactivate(row.id).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.init();
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
        dialog_imgClose() {
            this.imageUrl = "";
        },
        /*分配角色按钮*/
        empRoleBtn(id) {
            this.empRoleForm.eid = id;
            /*根据员工编号查询角色编号集合*/
            queryRoleIdsByEid(id).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.empRoleForm.empRoleIds = res.data;
                    this.hasRoleIds = res.data;
                    this.empRoleVisable = true;
                } else {
                    popup(res.msg, "warning");
                }
            });
            this.empRoleVisable = true;
        },
        submitRoleBtn() {
            if (
                JSON.stringify(this.hasRoleIds) ===
                JSON.stringify(this.empRoleForm.empRoleIds)
            ) {
                popup("你未做改动...", "warning");
            } else {
                this.$confirm("确定修改员工的职务?", "警示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        this.empRoleForm.empRoleIds =
                            this.empRoleForm.empRoleIds.toString();
                        saveRoleEmp(this.empRoleForm).then((res) => {
                            res = res.data;
                            if (res.code == 200) {
                                popup("操作成功");
                                this.hasRoleIds = [];
                                this.empRoleForm = {};
                                this.empRoleVisable = false;
                            } else {
                                this.empRoleForm.empRoleIds = this.hasRoleIds;
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
            }
        },
        roleBtnCancel() {
            this.empRoleVisable = false;
            this.hasRoleIds = [];
            this.empRoleIds = [];
        },
        /*重置密码按钮*/
        resetPwd(row) {
            this.resetPwdForm = {};
            this.resetPwdForm.eid = row.id;
            this.resetPwdVisable = true;
        },
        submitResetPwd() {
            this.$confirm("确定要重置该账户的密码?", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    resetPwd(this.resetPwdForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            this.resetPwdVisable = false;
                            var emp = loginEmp();
                            if (emp.id == this.resetPwdForm.eid) {
                                clearCookie("employee");
                                clearCookie("token");
                                this.$router.push("/");
                            }
                            this.resetPwdForm = {};
                            popup("密码重置成功");
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
    },
    mounted() {
        /*初始化所有部门信息*/
        this.deptAll();
        this.getRoleAll();
        /*初始化表格数据*/
        this.init();
    },
};
</script>
<style>
.searchForm {
    height: 134px;
}

.table {
    margin: 0 auto;
    height: 320px;
}

.pageUtils {
    position: absolute;
    width: 84%;
    margin: 2px auto;
    height: 43px;
}

.searchForm {
    display: inline-block;
    margin: 0 auto;
    height: 51px;
    line-height: 51px;
}

.searchForm .column {
    display: inline-block;
    margin: 0 auto;
    margin-right: 2px;
    line-height: 48px;
    height: 51px;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
