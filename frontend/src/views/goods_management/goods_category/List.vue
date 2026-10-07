z<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>分类管理</el-breadcrumb-item> </el-breadcrumb
        ><br />
        <el-row>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input placeholder="分类名称" v-model="searchForm.name" />
            </el-col>
            <el-col :span="8">
                <el-select
                    v-model="searchForm.state"
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
                    创建分类
                </el-button>
            </el-col>
        </el-row>
        <br />

        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column type="index" label-width="100px" label="序号">
                </el-table-column>
                <el-table-column prop="name" label-width="100px" label="名称">
                </el-table-column>
                <el-table-column
                    prop="info"
                    label-width="100px"
                    :show-overflow-tooltip="true"
                    label="描述"
                >
                </el-table-column>
                <el-table-column prop="state" label-width="100px" label="状态">
                    <template v-slot="scope">
                        <el-tag type="success" v-if="scope.row.state == '0'"
                            >正常</el-tag
                        >
                        <el-tag type="danger" v-else>停用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="240" fixed="right">
                    <template v-slot="scope">
                        <el-button
                            type="success"
                            @click="editbtn(scope.row)"
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
                            @click="forbidden(scope.row.id)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-no"
                                style="font-size: 18px"
                            ></i>
                            停用</el-button
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
        <el-dialog title="创建商品分类" :visible.sync="newVisable" width="50%">
            <el-form
                :model="newForm"
                :rules="rules"
                ref="newForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="名称：" prop="name">
                    <el-input v-model="newForm.name"></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input type="textarea" v-model="newForm.info"></el-input>
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
                    <el-button @click="saveCancel" style="font-size: 18px"
                        >取消</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--修改描述/状态弹出框-->
        <el-dialog
            title="分类信息修改"
            :visible.sync="dialogVisible"
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
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input
                        type="textarea"
                        v-model="editForm.info"
                    ></el-input>
                </el-form-item>
                <el-form-item label="状态：" prop="state">
                    <el-select
                        v-model="editForm.state"
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
                        @click="resetEditForm('editForm')"
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
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    deactivate,
    queryPageByQo,
    save,
    update,
} from "@/api/goods_management/goods_category/goodsCategoryApi";

export default {
    data() {
        return {
            /*搜索*/
            newVisable: false,
            dialogVisible: false,
            options: [
                { label: "正常", value: "0" },
                { label: "停用", value: "-1" },
            ],
            /*表格*/
            tableData: [],
            searchForm: {
                total: 0,
                currentPage: 1,
                pageSize: 5,
            },
            editForm: {},
            newForm: {},
            rules: {
                name: [
                    {
                        required: true,
                        message: "名称不能为空",
                        trigger: "blur",
                    },
                ],
                state: [
                    {
                        required: true,
                        message: "状态不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        init() {
            queryPageByQo(this.searchForm).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    console.log(res.data);
                    this.tableData = res.data.records;
                    this.searchForm.total = res.data.total;
                    this.searchForm.pageSize = res.data.size;
                    this.searchForm.currentPage = res.data.current;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        submitSearchForm() {
            this.init();
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
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    save(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.newForm = {};
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
            this.$refs["newForm"].resetFields();
        },
        /*修改信息业务*/
        editbtn(row) {
            this.editForm = { ...row };
            this.dialogVisible = true;
        },
        submitEditForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    update(this.editForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.editForm = {};
                            this.dialogVisible = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        resetEditForm(formName) {
            this.$refs[formName].resetFields();
        },
        /*停用业务*/
        forbidden(sid) {
            deactivate(sid).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    popup("操作成功");
                    this.searchForm.currentPage = 1;
                } else {
                    popup(res.msg, "error");
                }
                this.init();
            });
        },
    },
    mounted() {
        this.init();
    },
};
</script>
<style>
.searchForm {
    height: 134px;
}

.table {
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
</style>
