<template>
    <div id="role_list">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>系统管理</el-breadcrumb-item>
            <el-breadcrumb-item>角色管理</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input
                    placeholder="角色名称"
                    v-model="roleSearchForm.name"
                />
            </el-col>
            <el-col :span="8">
                <el-select
                    @change="$forceUpdate"
                    v-model="roleSearchForm.state"
                    placeholder="状态"
                    clearable
                >
                    <el-option label="正常" value="0"></el-option>
                    <el-option label="停用" value="-1"></el-option>
                </el-select>
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="24" style="text-align: left">
                <el-button
                    type="primary"
                    @click="subSearchForm('ruleForm')"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-find" style="font-size: 18px">
                    </i>
                    搜索</el-button
                >
                <el-button
                    type="success"
                    @click="newRoleVisable = true"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-add" style="font-size: 18px"> </i>
                    创建角色
                </el-button>
            </el-col>
        </el-row>
        <br />
        <el-table :data="tableData" style="width: 100%">
            <el-table-column type="index" label="序号" width="180">
            </el-table-column>
            <el-table-column prop="name" label="名称" width="180">
            </el-table-column>
            <el-table-column prop="info" label="描述"> </el-table-column>
            <el-table-column prop="state" label="状态">
                <template v-slot="scope">
                    <el-tag v-if="scope.row.state == '0'" type="success"
                        >正常</el-tag
                    >
                    <el-tag v-else type="danger">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="360" fixed="right">
                <template v-slot="scope">
                    <el-button
                        type="success"
                        @click="editRole(scope.row)"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-edit"
                            style="font-size: 18px"
                        ></i>
                        修改</el-button
                    >
                    <el-button
                        v-if="scope.row.state == '0'"
                        type="danger"
                        @click="forbidden(scope.row.id)"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-no"
                            style="font-size: 18px"
                        ></i>
                        停用</el-button
                    >
                    <el-button
                        type="warning"
                        @click="checkPermissons(scope.row.id)"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-setting"
                            style="font-size: 18px"
                        ></i>
                        授权</el-button
                    >
                </template>
            </el-table-column>
        </el-table>
        <!--修改描述/状态弹出框-->
        <el-dialog
            title="角色信息修改"
            :visible.sync="dialogVisible"
            width="50%"
        >
            <el-form
                v-model="editRoleForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="名称：">
                    <el-input disabled v-model="editRoleForm.name"></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input
                        type="text"
                        v-model="editRoleForm.info"
                    ></el-input>
                </el-form-item>
                <el-form-item label="状态：">
                    <el-select
                        v-model="editRoleForm.state"
                        clearable
                        @change="$forceUpdate()"
                        placeholder="请选择状态"
                    >
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitEditForm"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-yes"
                            style="font-size: 18px"
                        ></i>
                        提交</el-button
                    >
                    <el-button @click="resetEditForm" style="font-size: 18px">
                        <i
                            class="iconfont icon-r-refresh"
                            style="font-size: 18px"
                        ></i>
                        重置</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--创建角色弹出框-->
        <el-dialog title="创建角色" :visible.sync="newRoleVisable" width="50%">
            <el-form
                :model="newRoleForm"
                :rules="rules"
                ref="newRoleForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="名称：" prop="name">
                    <el-input v-model="newRoleForm.name"></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input type="text" v-model="newRoleForm.info"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitNewForm('newRoleForm')"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-add"
                            style="font-size: 18px"
                        ></i>
                        创建角色</el-button
                    >
                    <el-button @click="resetNewForm" style="font-size: 18px">
                        <i
                            class="iconfont icon-r-refresh"
                            style="font-size: 18px"
                        ></i
                        >重置</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--职能分配框-->
        <el-dialog
            title="权限分配"
            :visible.sync="distributionFulVisable"
            width="50%"
        >
            <el-form
                :model="distributionFulForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item>
                    <el-input
                        placeholder="输入关键字进行过滤"
                        v-model="filterText"
                    >
                    </el-input>
                    <el-tree
                        :data="fuloptions"
                        class="filter-tree"
                        show-checkbox
                        node-key="value"
                        :default-checked-keys="default_checked_mid"
                        default-expand-all
                        :filter-node-method="filterNode"
                        ref="tree"
                        :props="props"
                    ></el-tree>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitDistributionFulForm"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-add"
                            style="font-size: 18px"
                        ></i>
                        提交</el-button
                    >
                    <el-button
                        @click="celDistributionFul"
                        style="font-size: 18px"
                        >取消</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import {
    forbiddenRole,
    roleList,
    edit_role,
    save_role,
    checkPermissons,
    saveRolePermissons,
} from "@/api/system/role/roleApi";
import { ajaxPost, popup } from "@/assets/js/common";

export default {
    data() {
        return {
            tableData: [],
            editRoleForm: {},
            editRoleRow: {},
            dialogVisible: false,
            newRoleVisable: false,
            distributionFulVisable: false,
            newRoleForm: {},
            roleSearchForm: {},
            distributionFulForm: {},
            options: [
                { label: "正常", value: "0" },
                { label: "停用", value: "-1" },
            ],
            /*职能分配*/
            props: { multiple: true },
            fuloptions: [],
            default_checked_mid: [],
            filterText: "",
            rules: {
                name: [
                    {
                        required: true,
                        message: "名称不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        },
    },
    methods: {
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        init() {
            roleList(this.roleSearchForm).then((res) => {
                res = res.data;
                if ((res.code = 200)) {
                    this.tableData = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        forbidden(id) {
            forbiddenRole(id).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.roleSearchForm.state = "0";
                    this.init();
                    popup("操作成功");
                } else {
                    popup(res.msg, "error");
                }
            });
        },

        /*弹出修改表单*/
        editRole(row) {
            this.editRoleForm = { ...row };
            this.editRoleRow = { ...row };
            this.dialogVisible = true;
        },
        /*条件搜索*/
        subSearchForm() {
            this.init();
        },
        /*对接后端修改角色接口*/
        submitEditForm() {
            if (
                this.editRoleForm.info == this.editRoleRow.info &&
                this.editRoleForm.state == this.editRoleRow.state
            ) {
                popup("您没有更改内容", "warning");
                return;
            }
            edit_role(this.editRoleForm).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    popup("操作成功");
                    this.editRoleForm = {};
                    this.editRoleRow = {};
                    this.dialogVisible = false;
                    this.roleSearchForm.state = "0";
                    this.init();
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        /*修改角色重置按钮*/
        resetEditForm() {
            this.editRoleForm = { ...this.editRoleRow };
        },
        /*创建新角色*/
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log(this.newRoleForm);
                    save_role(this.newRoleForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.newRoleForm = {};
                            this.newRoleVisable = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        resetNewForm() {
            this.newRoleForm = {};
        },
        /*职能分配*/
        /*授权按钮*/
        checkPermissons(rid) {
            this.distributionFulForm.rid = rid;
            this.default_checked_mid = [];
            checkPermissons(rid).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.fuloptions = res.data.menus;
                    this.default_checked_mid = res.data.menuIds.sort(
                        (x, y) => x - y
                    );
                    console.log(typeof this.default_checked_mid);
                    console.log(this.default_checked_mid);
                } else {
                    popup(res.msg, "error");
                }
            });

            this.distributionFulVisable = true;
        },
        submitDistributionFulForm() {
            var keys = this.$refs.tree.getCheckedKeys();
            var v = JSON.stringify(keys).replace("[", "").replace("]", "");
            this.distributionFulForm.menuIds = v;
            console.log(this.distributionFulForm.menuIds);
            saveRolePermissons(this.distributionFulForm).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    popup("操作成功");
                    this.distributionFulVisable = false;
                    this.distributionFulForm = {};
                    this.default_checked_mid = [];
                    this.fuloptions = [];
                } else {
                    popup(res.msg, "error");
                }
            });
            this.distributionFulVisable = false;
        },
        celDistributionFul() {
            this.default_checked_mid = [];
            this.fuloptions = [];
            this.distributionFulForm = {};
            this.distributionFulVisable = false;
        },
    },
    created() {
        this.init();
    },
};
</script>
<style>
.search {
    display: inline-block;
    margin: 0 auto;
    height: 51px;
    line-height: 51px;
}

.search .column {
    display: inline-block;
    margin: 0 auto;
    margin-right: 2px;
    line-height: 48px;
    height: 51px;
}
</style>
