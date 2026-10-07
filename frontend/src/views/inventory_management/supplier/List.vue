<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>库存管理 </el-breadcrumb-item>
            <el-breadcrumb-item>供货商信息</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input placeholder="供货商名称" v-model="searchForm.name" />
            </el-col>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input placeholder="地址" v-model="searchForm.address" />
            </el-col>
            <el-col :span="8">
                <el-input placeholder="描述" v-model="searchForm.info" />
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="24" style="text-align: left">
                <el-button
                    type="primary"
                    @click="subSearchForm"
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
                    创建供货商
                </el-button>
            </el-col>
        </el-row>
        <br />

        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column type="index" width="200" label="序号">
                </el-table-column>
                <el-table-column
                    prop="name"
                    :show-overflow-tooltip="true"
                    label="名称"
                >
                </el-table-column>
                <el-table-column
                    prop="address"
                    :show-overflow-tooltip="true"
                    label="地址"
                >
                </el-table-column>
                <el-table-column
                    prop="tel"
                    :show-overflow-tooltip="true"
                    label="联系电话"
                >
                </el-table-column>
                <el-table-column
                    prop="info"
                    :show-overflow-tooltip="true"
                    label="备注"
                >
                </el-table-column>
                <el-table-column width="240" fixed="right" label="操作">
                    <template v-slot="scope">
                        <el-button
                            type="warning"
                            @click="editBtn(scope.row.cn)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-edit"
                                style="font-size: 18px"
                            ></i>
                            修改</el-button
                        >
                        <el-button
                            type="danger"
                            @click="delBtn(scope.row.cn)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-delete"
                                style="font-size: 18px"
                            ></i>
                            删除</el-button
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
        <!--创建弹出框-->
        <el-dialog
            title="创建供货商信息"
            :visible.sync="newVisable"
            width="50%"
        >
            <el-form
                :model="newForm"
                :rules="rules"
                ref="newForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="名称：" prop="name">
                    <el-input
                        v-model="newForm.name"
                        placeholder="供应商名称"
                    ></el-input>
                </el-form-item>
                <el-form-item label="地址：" prop="address">
                    <el-input
                        type="text"
                        v-model="newForm.address"
                        placeholder="供应商地址"
                    ></el-input>
                </el-form-item>
                <el-form-item label="电话：" prop="tel">
                    <el-input
                        type="text"
                        v-model="newForm.tel"
                        placeholder="联系电话"
                    ></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input
                        type="textarea"
                        v-model="newForm.info"
                        placeholder="供应商的简单描述"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitNewForm('newForm')"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-yes"
                            style="font-size: 18px"
                        ></i>
                        提交</el-button
                    >
                    <el-button
                        @click="
                            () => {
                                this.$refs['newForm'].resetFields();
                                newForm = {};
                                newVisable = false;
                            }
                        "
                        style="font-size: 18px"
                        >关闭</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--修改弹出框-->
        <el-dialog
            title="修改供货商信息"
            :visible.sync="editVisable"
            width="50%"
        >
            <el-form
                :model="editForm"
                :rules="rules"
                ref="editForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="名称：" prop="name">
                    <el-input
                        v-model="editForm.name"
                        placeholder="供应商名称"
                    ></el-input>
                </el-form-item>
                <el-form-item label="地址：" prop="address">
                    <el-input
                        type="text"
                        v-model="editForm.address"
                        placeholder="供应商地址"
                    ></el-input>
                </el-form-item>
                <el-form-item label="电话：" prop="tel">
                    <el-input
                        type="text"
                        v-model="editForm.tel"
                        placeholder="联系电话"
                    ></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input
                        type="textarea"
                        v-model="editForm.info"
                        placeholder="供应商的简单描述"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitEditForm('editForm')"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-yes"
                            style="font-size: 18px"
                        ></i>
                        提交</el-button
                    >
                    <el-button
                        @click="
                            () => {
                                this.$refs['editForm'].resetFields();
                                editForm = {};
                                editVisable = false;
                            }
                        "
                        style="font-size: 18px"
                        >关闭</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    delSupplier,
    queryByCn,
    queryPageSupplier,
    saveSupplier,
    updateSupplier,
} from "@/api/inventory_management/supplier/supplierApi";

export default {
    data() {
        return {
            tableData: [],
            editVisable: false,
            editForm: {},
            searchForm: {
                pageSize: 5,
            },
            newVisable: false,
            newForm: {},
            rules: {
                name: [
                    {
                        required: true,
                        message: "供货商名称不能为空",
                        trigger: "blur",
                    },
                ],
                address: [
                    {
                        required: true,
                        message: "供货商地址不能为空",
                        trigger: "blur",
                    },
                ],
                tel: [
                    {
                        required: true,
                        message: "供货商联系电话不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        subSearchForm() {
            this.init();
        },
        init() {
            queryPageSupplier(this.searchForm).then((res) => {
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
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    saveSupplier(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("添加成功");
                            this.newVisable = false;
                            this.newForm = {};
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        delBtn(cn) {
            this.$confirm("确定要删除这条记录" + "？", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    delSupplier({ cn: cn }).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("删除成功");
                            this.init();
                        } else {
                            popup(res.msg, "warning");
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
        editBtn(cn) {
            queryByCn({ cn: cn }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.editForm = { ...res.data };
                    this.editVisable = true;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        submitEditForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    updateSupplier(this.editForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("修改成功");
                            this.editVisable = false;
                            this.editForm = {};
                            this.init();
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
.table {
    height: 320px;
}

.pageUtils {
    position: absolute;
    width: 84%;
    margin: 2px auto;
    height: 43px;
}

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
