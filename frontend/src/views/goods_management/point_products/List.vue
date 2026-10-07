<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>积分商品</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input placeholder="商品名称" v-model="searchForm.name" />
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="24" style="text-align: left; padding-right: 10px">
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
                    @click="newBtn"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-add" style="font-size: 18px"> </i>
                    创建积分商品</el-button
                >
            </el-col>
        </el-row>
        <br />

        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column type="index" width="200" label="序号">
                </el-table-column>
                <el-table-column
                    prop="coverUrl"
                    :show-overflow-tooltip="true"
                    label="封面"
                >
                    <template v-slot="scope">
                        <img
                            height="60px"
                            :src="BaseApi + scope.row.coverUrl"
                        />
                    </template>
                </el-table-column>
                <el-table-column
                    prop="goodsName"
                    :show-overflow-tooltip="true"
                    label="商品名"
                >
                </el-table-column>
                <el-table-column
                    prop="integral"
                    :show-overflow-tooltip="true"
                    label="积分"
                >
                </el-table-column>
                <el-table-column
                    prop="updateby"
                    :show-overflow-tooltip="true"
                    label="操作者"
                >
                </el-table-column>
                <el-table-column width="240" fixed="right" label="操作">
                    <template v-slot="scope">
                        <el-button
                            type="success"
                            @click="editBtn(scope.row.goodsId)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-edit"
                                style="font-size: 18px"
                            >
                            </i
                            >修改</el-button
                        >
                        <el-button
                            type="danger"
                            @click="delBtn(scope.row.goodsId)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-delete"
                                style="font-size: 18px"
                            >
                            </i
                            >删除</el-button
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
        <!--创建-->
        <el-dialog title="创建积分商品" :visible.sync="newVisable" width="50%">
            <el-form
                :model="newForm"
                :rules="rules"
                ref="newForm"
                class="demo-ruleForm"
            >
                <el-form-item
                    v-if="selectGoodsVisiable"
                    label="商品"
                    prop="goodsId"
                >
                    <el-select
                        v-model="newForm.goodsId"
                        placeholder="请选择商品"
                        filterable
                        @change="$forceUpdate()"
                        clearable
                    >
                        <el-option
                            v-for="item in options_goods"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-else label="商品" prop="goodsId">
                    <el-select
                        disabled
                        v-model="newForm.goodsId"
                        placeholder="请选择商品"
                        filterable
                        @change="$forceUpdate()"
                        clearable
                    >
                        <el-option
                            v-for="item in options_goods"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-if="!selectGoodsVisiable" label="图片：">
                    <img
                        style="width: 100px"
                        :src="BaseApi + newForm.coverUrl"
                        alt="商品图片"
                    />
                </el-form-item>
                <el-form-item
                    v-if="!selectGoodsVisiable"
                    label="积分："
                    prop="integral"
                >
                    <el-input
                        @change="
                            () => {
                                if (this.newForm.integral < 0) {
                                    this.newForm.integral = 0;
                                }
                            }
                        "
                        type="number"
                        v-model="newForm.integral"
                        placeholder="如：3000"
                    />
                </el-form-item>
                <el-form-item v-if="selectGoodsVisiable">
                    <el-button
                        type="primary"
                        @click="confirmGoods('newForm')"
                        style="font-size: 18px"
                    >
                        <i class="iconfont icon-r-yes" style="font-size: 18px">
                        </i>
                        确定</el-button
                    >
                    <el-button
                        @click="closeGoods('newForm')"
                        style="font-size: 18px"
                        >关闭</el-button
                    >
                </el-form-item>
                <el-form-item v-else>
                    <el-button
                        type="primary"
                        @click="submitNewForm('newForm')"
                        style="font-size: 18px"
                    >
                        <i class="iconfont icon-r-yes" style="font-size: 18px">
                        </i>
                        确定</el-button
                    >
                    <el-button
                        @click="cancel('newForm')"
                        style="font-size: 18px"
                        >取消</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--修改-->
        <el-dialog title="修改积分商品" :visible.sync="editVisable" width="50%">
            <el-form
                :model="editForm"
                :rules="rules"
                ref="editForm"
                class="demo-ruleForm"
            >
                <el-form-item label="商品" prop="goodsId">
                    <el-select
                        disabled
                        v-model="editForm.goodsId"
                        placeholder="请选择商品"
                        filterable
                        @change="$forceUpdate()"
                        clearable
                    >
                        <el-option
                            v-for="item in options_goods1"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="图片：">
                    <img
                        style="width: 100px"
                        :src="BaseApi + editForm.coverUrl"
                        alt="商品图片"
                    />
                </el-form-item>
                <el-form-item label="积分：" prop="integral">
                    <el-input
                        @change="
                            () => {
                                if (this.editForm.integral < 0) {
                                    this.editForm.integral = 0;
                                }
                            }
                        "
                        type="number"
                        v-model="editForm.integral"
                        placeholder="如：3000"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitEditForm('editForm')"
                        style="font-size: 18px"
                    >
                        <i class="iconfont icon-r-yes" style="font-size: 18px">
                        </i>
                        确定</el-button
                    >
                    <el-button
                        @click="cancelEdit('editForm')"
                        style="font-size: 18px"
                        >取消</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    del,
    queryOptionGoods,
    queryPageByQo,
    queryPointGoodsById,
    savePointGoods,
    updatePointGoods,
} from "@/api/goods_management/point_products/pointProductApi";
import {
    queryGoodsById,
    selected_goodsAll,
} from "@/api/goods_management/goods/goodsApi";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            tableData: [],
            searchForm: {
                pageSize: 5,
            },
            selectGoodsVisiable: true,
            newVisable: false,
            editVisable: false,
            newForm: {
                goodsId: "",
                goodsName: "",
                integral: "",
                coverUrl: "",
            },
            editForm: {
                goodsId: "",
                goodsName: "",
                integral: "",
                coverUrl: "",
            },
            options_goods: [],
            options_goods1: [],
            rules: {
                goodsId: [
                    {
                        required: true,
                        message: "商品编号不能为空",
                        trigger: "blur",
                    },
                ],
                integral: [
                    {
                        required: true,
                        message: "积分不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        queryOptionGoods() {
            queryOptionGoods().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_goods = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        newBtn() {
            if (this.options_goods.length == 0) {
                popup("没有可以添加的积分商品", "warning");
                return;
            }
            this.newVisable = true;
            this.selectGoodsVisable = true;
        },
        closeGoods(formName) {
            this.$refs[formName].resetFields();
            this.newVisable = false;
        },
        confirmGoods(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    queryGoodsById({ id: this.newForm.goodsId }).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            this.newForm.coverUrl = res.data.coverUrl;
                            this.newForm.goodsName = res.data.name;
                            this.selectGoodsVisiable = false;
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    savePointGoods(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("创建成功");
                            this.newVisable = false;
                            this.selectGoodsVisiable = true;
                            this.newForm = {};
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        submitEditForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    updatePointGoods(this.editForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("创建成功");
                            this.editVisable = false;

                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        cancel(formName) {
            this.$refs[formName].resetFields();
            this.selectGoodsVisiable = true;
        },
        cancelEdit(formName) {
            this.$refs[formName].resetFields();
            this.editVisable = false;
            this.editForm = {};
        },
        subSearchForm() {
            this.init();
        },
        init() {
            (this.editForm = {
                goodsId: "",
                goodsName: "",
                integral: "",
                coverUrl: "",
            }),
                this.queryOptionGoods();
            queryPageByQo(this.searchForm).then((res) => {
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

        delBtn(cn) {
            this.$confirm("确定要删除这条记录" + "？", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    del({ id: cn }).then((res) => {
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
            queryPointGoodsById({ goodsId: cn }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.editVisable = true;
                    this.editForm.goodsId = res.data.goodsId;
                    this.editForm.goodsName = res.data.goodsName;
                    this.editForm.integral = res.data.integral;
                    this.editForm.coverUrl = res.data.coverUrl;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
    },
    mounted() {
        this.init();
        selected_goodsAll().then((res) => {
            res = res.data;
            if (res.code == 200) {
                this.options_goods1 = res.data;
            } else {
                popup(res.msg, "error");
            }
        });
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
