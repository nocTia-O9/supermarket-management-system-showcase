<template>
    <div id="employee_index">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>会员管理</el-breadcrumb-item>
            <el-breadcrumb-item>会员信息</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input placeholder="姓名" v-model="searchForm.name" />
            </el-col>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input placeholder="手机号码" v-model="searchForm.phone" />
            </el-col>
            <el-col :span="8">
                <el-select
                    v-model="searchForm.state"
                    @change="$forceUpdate()"
                    placeholder="状态"
                    clearable
                >
                    <el-option label="正常" value="0"></el-option>
                    <el-option label="删除" value="1"></el-option>
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
                    录入会员
                </el-button>
            </el-col>
        </el-row>
        <br />
        <!--表格数据-->
        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column prop="phone" label="手机号"> </el-table-column>
                <el-table-column prop="name" label="姓名"> </el-table-column>
                <el-table-column
                    prop="email"
                    :show-overflow-tooltip="true"
                    label="邮箱"
                >
                </el-table-column>
                <el-table-column prop="integral" label="积分">
                </el-table-column>
                <el-table-column prop="state" label="状态">
                    <template v-slot="scope">
                        <el-tag v-if="scope.row.state == '0'" type="success"
                            >正常</el-tag
                        >
                        <el-tag v-else type="danger">删除</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="info"
                    :show-overflow-tooltip="true"
                    label="备注"
                >
                </el-table-column>
                <el-table-column label="操作" width="420" fixed="right">
                    <template v-slot="scope">
                        <el-button
                            v-if="scope.row.state == '0'"
                            type="warning"
                            @click="exchangeProductsBtn(scope.row.id)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-refresh"
                                style="font-size: 18px"
                            ></i>
                            积分兑换
                        </el-button>
                        <el-button
                            type="success"
                            @click="editBtn(scope.row.id)"
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
                            @click="delBtn(scope.row.id)"
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

        <!--录入-->
        <el-dialog
            title="录入会员"
            :visible.sync="newVisable"
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
                <el-form-item label="号码：" prop="phone">
                    <el-input
                        max="11"
                        placeholder="请输入手机号"
                        v-model="newForm.phone"
                    ></el-input>
                </el-form-item>

                <el-form-item label="姓名：" prop="name">
                    <el-input
                        v-model="newForm.name"
                        placeholder="请输入姓名"
                    ></el-input>
                </el-form-item>
                <el-form-item label="邮箱：">
                    <el-input
                        v-model="newForm.email"
                        placeholder="如：demo@example.com"
                    ></el-input>
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input
                        type="textarea"
                        v-model="newForm.info"
                        placeholder="如：新会员"
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
                        @click="saveCancel('newForm')"
                        style="font-size: 18px"
                    >
                        取消</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--修改-->
        <el-dialog
            title="修改会员"
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
                <el-form-item label="号码：" prop="phone">
                    <el-input
                        max="11"
                        placeholder="请输入手机号"
                        v-model="editForm.phone"
                    ></el-input>
                </el-form-item>
                <el-form-item label="姓名：" prop="name">
                    <el-input
                        v-model="editForm.name"
                        placeholder="请输入姓名"
                    ></el-input>
                </el-form-item>
                <el-form-item label="邮箱：">
                    <el-input
                        v-model="editForm.email"
                        placeholder="如：demo@example.com"
                    ></el-input>
                </el-form-item>
                <el-form-item label="积分：">
                    <el-input
                        v-model="editForm.integral"
                        placeholder="如：10"
                    ></el-input>
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input
                        type="textarea"
                        v-model="editForm.info"
                        placeholder="如：新会员"
                    ></el-input>
                </el-form-item>
                <el-form-item label="状态：" prop="state">
                    <el-select
                        v-model="editForm.state"
                        placeholder="请选择状态"
                        filterable
                        @change="$forceUpdate()"
                        clearable
                    >
                        <el-option label="正常" value="0"></el-option>
                        <el-option label="删除" value="1"></el-option>
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
                        type="info"
                        @click="resetEditForm('editForm')"
                        style="font-size: 18px"
                    >
                        关闭</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--积分商品-->
        <el-dialog
            title="兑换积分商品"
            :visible.sync="pointProductsVisable"
            label-width="200"
            width="40%"
        >
            <el-form
                :model="pointProductsForm"
                :rules="rules"
                ref="pointProductsForm"
                class="demo-ruleForm"
            >
                <el-form-item v-if="pointProductsForm.goodsId">
                    <img
                        width="100px"
                        :src="BaseApi + pointProductsForm.coverUrl"
                        alt="商品图片"
                    />
                </el-form-item>
                <el-form-item label="积分商品：" prop="goodsId">
                    <el-select
                        @change="
                            queryPointProductByGoodsId(
                                pointProductsForm.goodsId
                            )
                        "
                        v-model="pointProductsForm.goodsId"
                        placeholder="请选择积分商品"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="item in options_pointProducts"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item
                    v-if="pointProductsForm.goodsId"
                    label="所需积分："
                >
                    <el-input
                        disabled
                        type="number"
                        v-model="pointProductsForm.integral"
                        placeholder="所需积分"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button
                        v-if="pointProductsForm.goodsId"
                        type="warning"
                        @click="submitPointProductsForm('pointProductsForm')"
                        style="font-size: 18px"
                    >
                        <i
                            class="iconfont icon-r-yes"
                            style="font-size: 18px"
                        ></i>
                        兑换
                    </el-button>
                    <el-button
                        type="info"
                        @click="closePointProductsForm"
                        style="font-size: 18px"
                    >
                        关闭</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    delMember,
    queryPageByQo,
    save,
    update,
    queryMemberById,
} from "@/api/member_management/member/memberApi";
import {
    queryPointProductByGoodsId,
    queryPointProductBymemberId,
    saveExchangePointProductRecords,
} from "@/api/sale_management/exchange_point_products/exchangePointProductsApi";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            newVisable: false,
            editVisable: false,
            searchForm: {
                pageSize: 5,
            },
            tableData: [],
            newForm: {},
            editForm: {},
            rules: {
                name: [
                    {
                        required: true,
                        message: "名称不能为空",
                        trigger: "blur",
                    },
                ],
                phone: [
                    {
                        required: true,
                        message: "手机号不能为空",
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
                goodsId: [
                    {
                        required: true,
                        message: "商品不能为空",
                        trigger: "blur",
                    },
                ],
            },
            options_pointProducts: [],
            pointProductsForm: {
                goodsId: "",
                coverUrl: "",
                integral: "",
            },
            pointProductsVisable: false,
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
        saveCancel(formName) {
            this.newForm = {};
            this.$refs[formName].resetFields();
            this.newVisable = false;
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
        editBtn(id) {
            queryMemberById({ id: id }).then((res) => {
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
                    update(this.editForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.editForm = {};
                            this.editVisable = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        resetEditForm(formName) {
            this.editForm = {};
            this.$refs[formName].resetFields();
            this.editVisable = false;
        },
        delBtn(id) {
            this.$confirm("确定删除该会员？", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    delMember({ id: id }).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.searchForm.state = "0";
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
        /*兑换积分商品*/
        exchangeProductsBtn(id) {
            (this.pointProductsForm = {
                memberId: this.pointProductsForm.memberId,
                goodsId: "",
                coverUrl: "",
                integral: "",
            }),
                queryPointProductBymemberId({ memberId: id }).then((res) => {
                    res = res.data;
                    if (res.code == 200) {
                        this.options_pointProducts = res.data;
                        if (!this.options_pointProducts.length) {
                            popup("该会员积分数无法兑换任何商品", "warning");
                            return;
                        }
                        this.pointProductsForm.memberId = id;
                        this.pointProductsVisable = true;
                    } else {
                        popup(res.msg, "error");
                    }
                });
        },
        submitPointProductsForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    saveExchangePointProductRecords(
                        this.pointProductsForm
                    ).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("兑换成功");
                            this.pointProductsForm = {};
                            this.pointProductsVisable = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        closePointProductsForm() {
            this.options_pointProducts = [];
            this.pointProductsForm = {};
            this.pointProductsVisable = false;
        },
        queryPointProductByGoodsId(goodsId) {
            queryPointProductByGoodsId({ goodsId: goodsId }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    if (!res.data) {
                        this.pointProductsForm = {
                            memberId: this.pointProductsForm.memberId,
                            goodsId: "",
                            coverUrl: "",
                            integral: "",
                        };
                        return;
                    }
                    this.pointProductsForm.integral = res.data.integral;
                    this.pointProductsForm.coverUrl = res.data.coverUrl;
                } else {
                    popup(res.msg, "error");
                    this.pointProductsForm.goodsId = "";
                }
            });
        },
    },
    mounted() {
        /*初始化表格数据*/
        this.searchForm.state = "0";
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
