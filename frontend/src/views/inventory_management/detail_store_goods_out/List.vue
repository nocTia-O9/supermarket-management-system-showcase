<template>
    <div id="detail_store_goods_out">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>库存管理 </el-breadcrumb-item>
            <el-breadcrumb-item>出库明细</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="4" style="padding-right: 10px;">
                <el-input
                    placeholder="出库编号"
                    v-model="searchForm.cn"
                />
            </el-col>
            <el-col :span="4">
                <el-input
                    placeholder="商品名称"
                    v-model="searchForm.goodsName"
                />
            </el-col>
            <el-col :span="16">
                
                <span>出库时间：</span>
                <el-date-picker
                    value-format="yyyy-MM-dd"
                    v-model="searchForm.startCreateTime"
                    type="date"
                    placeholder="起始时间"
                >
                </el-date-picker>
                -
                <el-date-picker
                    v-model="searchForm.endCreateTime"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="结束时间"
                >
                </el-date-picker>
            </el-col>
        </el-row>
        <br>
        <el-row>
            <el-col :span="4" style="padding-right: 10px;">
                <el-select
                    v-model="searchForm.state"
                    @change="$forceUpdate()"
                    placeholder="类型"
                    clearable
                >
                    <el-option label="出库" value="0"></el-option>
                    <el-option label="过期" value="1"></el-option>
                    <el-option label="下架" value="2"></el-option>
                </el-select>
            </el-col>
            <el-col :span="4">
                <el-select
                    v-model="searchForm.state1"
                    @change="$forceUpdate()"
                    placeholder="请选择状态"
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
                    type="warning"
                    @click="goodsOutBtn"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-left" style="font-size: 18px"> </i>
                     商品出库
                </el-button>
            </el-col>
        </el-row>
        <br />
        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column prop="cn" width="200" label="编号">
                </el-table-column>
                <el-table-column
                    prop="goodsName"
                    :show-overflow-tooltip="true"
                    label="商品名"
                >
                </el-table-column>
                <el-table-column
                    prop="storeName"
                    :show-overflow-tooltip="true"
                    label="仓库"
                >
                </el-table-column>
                <el-table-column prop="goodsNum" label="商品数量">
                </el-table-column>
                <el-table-column prop="state" label="类型">
                    <template v-slot="scope">
                        <el-tag
                            type="danger"
                            v-if="scope.row.state == '2'"
                            >下架</el-tag
                        >
                        <el-tag
                            type="danger"
                            v-else-if="scope.row.state == '1'"
                            >过期</el-tag
                        >
                        <el-tag type="warning" v-else
                            >出库</el-tag
                        >
                    </template>
                </el-table-column>
                <el-table-column prop="state1" label="状态">
                    <template v-slot="scope">
                        <el-tag
                            type="success"
                            v-if="scope.row.state1 == '0'"
                            >正常</el-tag
                        >
                        <el-tag type="danger" v-else>删除</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="createby" label="出库者">
                </el-table-column>
                <el-table-column
                    prop="createTime"
                    :show-overflow-tooltip="true"
                    label="出库时间"
                >
                </el-table-column>
                <el-table-column
                    prop="info"
                    :show-overflow-tooltip="true"
                    label="备注"
                >
                </el-table-column>
                <el-table-column width="140" label="操作" fixed="right">
                    <template v-slot="scope">
                        <el-button
                            type="danger"
                            @click="del(scope.row.cn)"
                            style="font-size: 18px"
                            >
                            <i class="iconfont icon-r-delete" style="font-size: 18px;"></i>
                             删除
                        </el-button>
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
        <!--商品出库-->
        <el-dialog title="商品出库" :visible.sync="goodsOutVisable" width="50%">
            <el-form
                v-if="selectGoodsVisable"
                :model="selectGoods"
                :rules="rules"
                ref="selectGoods"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="商品：" prop="goodsId">
                    <el-select
                        @change="changeGoods"
                        v-model="selectGoods.goodsId"
                        placeholder="请选择商品"
                        filterable
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
                <el-form-item label="仓库：" prop="storeId">
                    <el-select
                        v-model="selectGoods.storeId"
                        @change="changeStore"
                        placeholder="请选择仓库"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="item in options_store"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="类型：" prop="state">
                    <el-select
                        v-model="selectGoods.state"
                        @change="$forceUpdate()"
                        filterable
                        placeholder="请选择类型"
                        clearable
                    >
                        <el-option label="出库" value="0"></el-option>
                        <el-option label="过期" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="success"
                        @click="selectedGoods('selectGoods')"
                        style="font-size: 18px;"
                        >
                        <i class="iconfont icon-r-yes" style="font-size: 18px;"></i>
                         确定
                        </el-button
                    >
                    <el-button
                        type="warning"
                        @click="closeSelectedGoods('selectGoods')"
                        style="font-size: 18px;"
                        >
                         关闭</el-button
                    >
                </el-form-item>
            </el-form>
            <el-form
                v-if="newVisable"
                :model="newForm"
                :rules="rules"
                ref="newForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="商品名：">
                            <el-input
                                readonly
                                v-model="newForm.goodsName"
                                placeholder="如：农夫山泉"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item
                            label="类型："
                            prop="state"
                        >
                            <el-select
                                disabled
                                v-model="newForm.state"
                                filterable
                                @change="$forceUpdate()"
                                placeholder="请选择类型"
                                clearable
                            >
                                <el-option label="出库" value="0"></el-option>
                                <el-option label="过期" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item
                            label="商品数量："
                            prop="goodsNum"
                        >
                            <el-input
                                type="number"
                                @change="
                                    () => {
                                        if (
                                            this.goodsNum_max <
                                            this.newForm.goodsNum
                                        ) {
                                            this.newForm.goodsNum =
                                                this.goodsNum_max;
                                        }
                                        if (this.newForm.goodsNum < 1) {
                                            this.newForm.goodsNum = 1;
                                        }
                                    }
                                "
                                min="1"
                                :max="goodsNum_max"
                                v-model="newForm.goodsNum"
                                placeholder="如：1"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item
                            label="仓库："
                            prop="storeId"
                        >
                            <el-select
                                disabled
                                v-model="newForm.storeId"
                                @change="changeStore"
                                placeholder="请选择仓库"
                                filterable
                                clearable
                            >
                                <el-option
                                    v-for="item in options_store"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id"
                                >
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item
                            label="出库日期："
                            prop="createTime"
                        >
                            <el-date-picker
                                size="mini"
                                style="width: 140px"
                                value-format="yyyy-MM-dd"
                                v-model="newForm.createTime"
                                type="date"
                                placeholder="出库日期"
                            >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="备注：">
                            <el-input
                                type="textarea"
                                v-model="newForm.info"
                                placeholder="如：某商品因什么原因出库"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button type="warning" @click="submitNewForm('newForm')" style="font-size: 18px;"
                        >
                        <i class="iconfont icon-r-left" style="font-size: 18px;"></i>
                         出库</el-button
                    >
                    <el-button @click="saveCancel('newForm')" style="font-size: 18px;">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import {
    changeOutGoods,
    changeOutStore,
    delIn,
    delOut,
    initOptions,
    initOutOptions,
    queryOutGoods,
    queryPageByQoOut,
    saveIn,
    saveOut,
} from "@/api/inventory_management/detail_store_goods/detail_store_goodsApi";
import { popup } from "@/assets/js/common";
import {
    queryGoodsById,
    selected_goodsAll,
} from "@/api/goods_management/goods/goodsApi";
import { storeList } from "@/api/inventory_management/store/storeApi";

export default {
    data() {
        return {
            goodsOutVisable: false,
            selectGoodsVisable: true,
            newVisable: false,
            searchForm: {
                pageSize: 5
            },
            tableData: [],
            options_store: [],
            goodsNum_max: "",
            newForm: {
                goodsId: "",
                goodsNum: "",
                goodsName: "",
                info: "",
                createTime: "",
                storeId: "",
                state: "",
            },
            rules: {
                goodsId: [
                    {
                        required: true,
                        message: "商品编号不能为空",
                        trigger: "blur",
                    },
                ],
                storeId: [
                    {
                        required: true,
                        message: "仓库编号不能为空",
                        trigger: "blur",
                    },
                ],
                state: [
                    {
                        required: true,
                        message: "类型不能为空",
                        trigger: "blur",
                    },
                ],
                goodsNum: [
                    {
                        required: true,
                        message: "出库数量不能为空",
                        trigger: "blur",
                    },
                ],
                createTime: [
                    {
                        required: true,
                        message: "出库日期不能为空",
                        trigger: "blur",
                    },
                ],
            },
            options_goods: [],
            selectGoods: {},
        };
    },
    methods: {
        changeGoods() {
            this.$forceUpdate();
            changeOutGoods({ gid: this.selectGoods.goodsId }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_store = res.data;
                }
            });
        },
        changeStore() {
            this.$forceUpdate();
            changeOutStore({ storeId: this.selectGoods.storeId }).then(
                (res) => {
                    res = res.data;
                    if (res.code == 200) {
                        this.options_goods = res.data;
                    }
                }
            );
        },
        initOptions() {
            initOutOptions().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_goods = res.data.goods;
                    this.options_store = res.data.stores;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        init() {
            if (
                this.searchForm.startCreateTime > this.searchForm.endCreateTime
            ) {
                popup("结束时间不能小于开始时间", "warning");
                return;
            }
            queryPageByQoOut(this.searchForm).then((res) => {
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
        del(cn) {
            this.$confirm("确定删除这条记录？", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    delOut({ cn: cn }).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.searchForm.state1 = "0";
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
        goodsOutBtn() {
            this.selectGoods.state = "0";
            this.goodsOutVisable = true;
            this.selectGoodsVisable = true;
            this.newVisable = false;
            this.newForm = {
                goodsId: "",
                goodsNum: "",
                goodsName: "",
                info: "",
                createTime: "",
                storeId: "",
                state: "",
            };
        },
        selectedGoods(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    queryOutGoods(this.selectGoods).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            this.selectGoodsVisable = false;
                            this.newVisable = true;
                            this.newForm.goodsNum = res.data.goodsNum;
                            this.goodsNum_max = res.data.goodsNum;
                            this.newForm.goodsName = res.data.goodsName;
                            this.newForm.state = this.selectGoods.state;
                            this.newForm.storeId = this.selectGoods.storeId;
                            this.newForm.goodsId = this.selectGoods.goodsId;
                            this.selectGoods = {};
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        closeSelectedGoods(formName) {
            this.$refs[formName].resetFields();
            this.selectGoods = {};
            this.goodsOutVisable = false;
        },
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    saveOut(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("出库成功");
                            this.searchForm.state1 = "0";
                            this.goodsOutVisable = false;
                            this.newVisable = false;
                            this.selectGoodsVisable = true;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        saveCancel(formName) {
            this.$refs[formName].resetFields();
            this.newVisable = false;
            this.selectGoodsVisable = true;
        },
    },
    mounted() {
        this.searchForm.state1 = "0";
        this.initOptions();
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
