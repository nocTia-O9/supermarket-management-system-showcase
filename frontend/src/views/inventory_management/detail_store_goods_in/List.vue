<template>
    <div id="detail_store_goods_in">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>库存管理 </el-breadcrumb-item>
            <el-breadcrumb-item>入库明细</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="3" style="text-align: left;padding-right: 10px;">
                <el-input placeholder="入库编号" v-model="searchForm.cn"/>
            </el-col>
            <el-col :span="3">
                <el-input placeholder="商品名称" v-model="searchForm.goodsName"/>
            </el-col>
            <el-col :span="16">
                <span>入库时间：</span>
                <el-date-picker
                        value-format="yyyy-MM-dd"
                        v-model="searchForm.startCreateTime"
                        type="date"
                        placeholder="起始时间">
                </el-date-picker>
                -
                <el-date-picker
                        v-model="searchForm.endCreateTime"
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="结束时间">
                </el-date-picker>
            </el-col>
            <el-col :span="2" >
                <el-select
                           v-model="searchForm.state1"
                           @change="$forceUpdate()"
                           placeholder="请选择状态" clearable>
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
                    @click="goodsInBtn"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-right" style="font-size: 18px"> </i>
                    商品入库
                </el-button>
            </el-col>
        </el-row>
        <br />
        <div class="table">
            <el-table
                    :data="tableData"
                    style="width: 100%;"
                    
                    size="medium">
                <el-table-column
                        prop="cn"
                        width="200"
                        label="编号">
                </el-table-column>
                <el-table-column
                        prop="goodsName"
                        :show-overflow-tooltip="true"
                        label="商品名">
                </el-table-column>
                <el-table-column
                        prop="goodsPrice"
                        label="单价">
                </el-table-column>
                <el-table-column
                        prop="goodsNum"
                        label="入库数量">
                </el-table-column>

                <el-table-column
                        prop="expiryTime"
                        :show-overflow-tooltip="true"
                        label="过期日期">
                </el-table-column>
                <el-table-column
                        prop="expiryTime"
                        :show-overflow-tooltip="true"
                        label="是否过期">
                    <template v-slot="scope">
                        <el-tag type="danger" v-if="new Date()>Date.parse(scope.row.expiryTime)">过期</el-tag>
                        <el-tag type="success" v-else>未过期</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        :show-overflow-tooltip="true"
                        label="入库时间">
                </el-table-column>
                <el-table-column
                        prop="storeName"
                        :show-overflow-tooltip="true"
                        label="存储仓库">
                </el-table-column>
                <el-table-column
                        prop="supplierName"
                        :show-overflow-tooltip="true"
                        label="供货商">
                </el-table-column>
                <el-table-column
                        prop="createby"
                        :show-overflow-tooltip="true"
                        label="录入者">
                </el-table-column>
                <el-table-column
                        prop="state"
                        label="类型">
                    <template v-slot="scope">
                        <el-tag type="success" v-if="scope.row.state=='0'">入库</el-tag>
                        <el-tag type="danger" v-if="scope.row.state=='1'">过期</el-tag>
                        <el-tag type="danger" v-if="scope.row.state=='2'">下架</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="state1"
                        label="状态">
                    <template v-slot="scope">
                        <el-tag type="success" v-if="scope.row.state1=='0'">正常</el-tag>
                        <el-tag type="danger" v-else-if="scope.row.state1=='2'">未处理</el-tag>
                        <el-tag type="danger" v-else>删除</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="info"
                        :show-overflow-tooltip="true"
                        label="备注">
                </el-table-column>
                <el-table-column
                        width="140"
                        fixed="right"
                        label="操作">
                    <template v-slot="scope">
                        <el-button type="danger" v-if="scope.row.state1=='0'"
                                   @click="del(scope.row.cn)" style="font-size: 18px;">
                                   <i class="iconfont icon-r-delete" style="font-size: 18px;"></i>
                                   删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="margin: 10px 0 15px 0;">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="searchForm.currentPage"
                        :page-sizes="[5, 10, 20, 50]"
                        :page-size="searchForm.pageSize"
                        layout="total,sizes, prev, pager, next,jumper"
                        :total="searchForm.total">
                </el-pagination>
            </div>
        </div>
        <!--商品入库表单-->
        <el-dialog
                title="商品入库"
                :visible.sync="goodsInVisable"
                width="50%">
            <el-form v-if="selectGoodsVisable" :model="selectGoods" :rules="rules" ref="selectGoods" label-width="100px"
                     class="demo-ruleForm">
                <el-form-item label="商品：" prop="goodsId">
                    <el-select v-model="selectGoods.goodsId"
                               placeholder="请选择商品"
                               filterable
                               @change="$forceUpdate()"
                               clearable>
                        <el-option
                                v-for="item in options_goods"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="仓库：" prop="storeId">
                    <el-select v-model="selectGoods.storeId"
                               placeholder="请选择存储仓库"
                               filterable
                               @change="$forceUpdate()"
                               clearable>
                        <el-option
                                v-for="item in options_store"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="selectedGoods('selectGoods')" style="font-size: 18px;">
                        <i class="iconfont icon-r-yes" style="font-size: 18px;"></i>
                        确定</el-button>
                    <el-button @click="closeSelectedGoods('selectGoods')" style="font-size: 18px;">
                
                         关闭</el-button>
                </el-form-item>
            </el-form>
            <el-form v-if="newVisable" :model="newForm" :rules="rules" ref="newForm" label-width="100px"
                     class="demo-ruleForm">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="商品名称：">
                            <el-input disabled v-model="newForm.goodsName" placeholder="如：农夫山泉"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="商品数量：" prop="goodsNum">
                            <el-input v-model="newForm.goodsNum" placeholder="如：1"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="供货商：" prop="supplierId">
                            <el-select v-model="newForm.supplierId"
                                       placeholder="请选择供货商"
                                       filterable
                                       @change="$forceUpdate()"
                                       clearable>
                                <el-option
                                        v-for="item in options_suppliers"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="商品价格：" prop="goodsPrice">
                            <el-input v-model="newForm.goodsPrice" placeholder="如：1.0"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="生产日期：" prop="birthTime">
                            <el-date-picker
                                    size="mini"
                                    style="width: 140px"
                                    value-format="yyyy-MM-dd"
                                    v-model="newForm.birthTime"
                                    type="date"
                                    placeholder="生产日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="过期日期：" prop="expiryTime">
                            <el-date-picker
                                    size="mini"
                                    style="width: 140px"
                                    value-format="yyyy-MM-dd"
                                    v-model="newForm.expiryTime"
                                    type="date"
                                    placeholder="过期日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="备注：">
                            <el-input type="textarea" v-model="newForm.info" placeholder="如：农夫山泉入库"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>
                <el-form-item>
                    <el-button type="warning" @click="submitNewForm('newForm')" style="font-size: 18px;">
                        <i class="iconfont icon-r-right" style="font-size: 18px;"></i>
                         入库</el-button>
                    <el-button @click="saveCancel('newForm')" style="font-size: 18px;">
                         取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import {
    queryGoodsById,
    selected_goodsAll,
} from "@/api/goods_management/goods/goodsApi";
import { popup } from "@/assets/js/common";
import {
    delIn,
    queryOptionsSuppliers,
    queryPageByQoIn,
    saveIn,
} from "@/api/inventory_management/detail_store_goods/detail_store_goodsApi";
import { storeList } from "@/api/inventory_management/store/storeApi";

export default {
    data() {
        return {
            newVisable: false,
            goodsInVisable: false,
            selectGoodsVisable: true,
            options_suppliers: [],
            searchForm: {
                pageSize: 5
            },
            tableData: [],
            newForm: {
                cn: "",
                goodsId: "",
                goodsNum: "",
                goodsName: "",
                goodsPrice: "",
                supplierId: "",
                info: "",
                expiryTime: "",
                birthTime: "",
                storeId: "",
            },
            options_goods: [],
            options_store: [],
            rules: {
                supplierId: [
                    {
                        required: true,
                        message: "供应商不能为空",
                        trigger: "blur",
                    },
                ],
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
                goodsNum: [
                    {
                        required: true,
                        message: "商品数量不能为空",
                        trigger: "blur",
                    },
                ],
                goodsPrice: [
                    {
                        required: true,
                        message: "商品价格不能为空",
                        trigger: "blur",
                    },
                ],
                expiryTime: [
                    {
                        required: true,
                        message: "过期时间不能为空",
                        trigger: "blur",
                    },
                ],
                birthTime: [
                    {
                        required: true,
                        message: "生产时间不能为空",
                        trigger: "blur",
                    },
                ],
            },
            selectGoods: {},
        };
    },
    methods: {
        init() {
            this.newVisable = false;
            this.selectGoodsVisable = true;
            if (
                this.searchForm.startCreateTime > this.searchForm.endCreateTime
            ) {
                popup("结束时间不能小于开始时间", "warning");
                return;
            }
            queryPageByQoIn(this.searchForm).then((res) => {
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
        closeSelectedGoods(formName) {
            this.$refs[formName].resetFields();
            this.goodsInVisable = false;
            this.newVisable = false;
            this.selectGoodsVisable = true;
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
        goodsInBtn() {
            this.selectGoods = {};
            this.goodsInVisable = true;
            this.newForm = {
                cn: "",
                goodsId: "",
                goodsNum: "",
                goodsName: "",
                goodsPrice: "",
                supplierId: "",
                info: "",
                expiryTime: "",
                birthTime: "",
                storeId: "",
            };
        },
        detailStoreGoodsIn_goodsAll() {
            selected_goodsAll().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_goods = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        detailStoreGoodsIn_suppliers() {
            queryOptionsSuppliers().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_suppliers = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        detailStoreGoodsIn_storeAll() {
            storeList({ state: "0" }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    for (var item of res.data) {
                        this.options_store.push({
                            id: item.id,
                            name: item.name,
                        });
                    }
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        selectedGoods(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    queryGoodsById({ id: this.selectGoods.goodsId }).then(
                        (res) => {
                            res = res.data;
                            if (res.code == 200) {
                                this.selectGoodsVisable = false;
                                this.newVisable = true;
                                this.newForm.storeId = this.selectGoods.storeId;
                                this.selectGoods = {
                                    storeId: "",
                                    goodsId: "",
                                };
                                this.newForm.goodsId = res.data.id;
                                this.newForm.goodsName = res.data.name;
                                this.newForm.goodsPrice =
                                    res.data.purchashPrice;
                            } else {
                                popup(res.msg, "error");
                            }
                        }
                    );
                }
            });
        },
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.newForm.birthTime >= this.newForm.expiryTime) {
                        popup("生成日期不能大于或等于过期日期", "warning");
                        this.newForm.birthTime = "";
                        this.newForm.expiryTime = "";
                        return;
                    }
                    saveIn(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("入库成功");
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
        del(cn) {
            this.$confirm("确定删除这条记录？", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    delIn({ cn: cn }).then((res) => {
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
    },
    mounted() {
        this.searchForm.state1 = "0";
        this.detailStoreGoodsIn_goodsAll();
        this.detailStoreGoodsIn_storeAll();
        this.detailStoreGoodsIn_suppliers();
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
