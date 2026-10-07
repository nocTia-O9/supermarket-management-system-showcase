<template>
    <div id="sale_records">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item
                >首页</el-breadcrumb-item
            >
            <el-breadcrumb-item
                >销售管理</el-breadcrumb-item
            >
            <el-breadcrumb-item>销售记录</el-breadcrumb-item>
        </el-breadcrumb>
        <br />
        <el-row>
            <el-col :span="4">
                <el-input
                    style="height: 21px; width: 130px"
                    placeholder="销售编号"
                    v-model="searchForm.cn"
                />
            </el-col>
            <el-col :span="10">
                <span>销售时间：</span>
                <el-date-picker
                    style="width: 140px"
                    v-model="searchForm.startSellTime"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="起始时间"
                >
                </el-date-picker>
                -
                <el-date-picker
                    v-model="searchForm.endSellTime"
                    style="width: 140px"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="结束时间"
                >
                </el-date-picker>
            </el-col>
            <el-col :span="4">
                <el-select
                    style="width: 120px"
                    v-model="searchForm.type"
                    @change="$forceUpdate()"
                    placeholder="顾客类型"
                    clearable
                >
                    <el-option label="非会员顾客" value="0"></el-option>
                    <el-option label="会员" value="1"></el-option>
                </el-select>
            </el-col>
            <el-col :span="4">
                <el-select
                    style="width: 120px"
                    v-model="searchForm.sellway"
                    @change="$forceUpdate()"
                    placeholder="支付方式"
                    clearable
                >
                    <el-option label="支付宝" value="0"></el-option>
                    <el-option label="微信" value="1"></el-option>
                    <el-option label="现金" value="2"></el-option>
                    <el-option label="银行卡" value="3"></el-option>
                </el-select>
            </el-col>
        </el-row>
        <br>
        <el-row>
            <el-col :span="24" style="text-align: left;">
                
                <el-button type="success" @click="submitSearchForm" style="font-size: 18px;"
                    >
                    <i class="iconfont icon-r-find" style="font-size: 18px;"> </i>
                    搜索</el-button
                >
            </el-col>
        </el-row>
        <br />
        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column prop="cn" width="200" label="编号">
                </el-table-column>
                <el-table-column prop="sellTotal" label="销售数量">
                </el-table-column>
                <el-table-column prop="sellTotalmoney" label="销售总额">
                    <template v-slot="scope">
                        {{ parseFloat(scope.row.sellTotalmoney).toFixed(2) }}
                    </template>
                </el-table-column>
                <el-table-column
                    prop="sellTime"
                    :show-overflow-tooltip="true"
                    label="销售时间"
                >
                </el-table-column>
                <el-table-column prop="type" label="顾客类型" width="120">
                    <template v-slot="scope">
                        <el-tag
                            effect="dark"
                            type="warning"
                            style="font-size: 14px;"
                            v-if="scope.row.type == '1'"
                            >
                            <i class="iconfont icon-r-mark1"></i>
                            会员</el-tag
                        >
                        <el-tag effect="dark" type="info" v-else
                            >非会员</el-tag
                        >
                    </template>
                </el-table-column>
                <el-table-column prop="sellby" label="销售员">
                </el-table-column>
                <el-table-column prop="sellway" label="支付方式">
                    <template v-slot="scope">
                        <el-tag
                            effect="dark"
                            type="primary"
                            v-if="scope.row.sellway == '0'"
                            >支付宝</el-tag
                        >
                        <el-tag
                            effect="dark"
                            type="success"
                            v-else-if="scope.row.sellway == '1'"
                            >微信</el-tag
                        >
                        <el-tag
                            effect="dark"
                            type="warning"
                            v-else-if="scope.row.sellway == '3'"
                            >银行卡</el-tag
                        >
                        <el-tag effect="dark" type="danger" v-else>现金</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="info"
                    :show-overflow-tooltip="true"
                    label="备注"
                >
                </el-table-column>
                <el-table-column width="240" label="操作" fixed="right">
                    <template v-slot="scope">
                        <el-button
                            type="success"
                            style="font-size: 18px;"
                            @click="detailBtn(scope.row)"
                            >
                            <i class="iconfont icon-r-find" style="font-size: 18px;"> </i>
                            明细
                        </el-button>
                        <el-button
                            type="danger"
                            @click="del(scope.row.cn)"
                            style="font-size: 18px;"
                            >
                            <i class="iconfont icon-r-delete" style="font-size: 18px;"> </i>
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
                    :total="searchForm.total"
                >
                </el-pagination>
            </div>
        </div>
        <el-dialog
            title="销售订单明细"
            :visible.sync="detailSaleRecordsVisable"
            width="80%"
        >
            <el-form
                :model="saleRecordForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-row>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="销售编号：">
                            <el-input
                            disabled
                                readonly
                                v-model="saleRecordForm.cn"
                                placeholder="如：销售编号"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item
                            style="width: 80%"
                            label="支付方式："
                            prop="sellway"
                        >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="saleRecordForm.sellway"
                                label="0"
                                >支付宝</el-radio
                            >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="saleRecordForm.sellway"
                                label="1"
                                >微信</el-radio
                            >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="saleRecordForm.sellway"
                                label="2"
                                >现金</el-radio
                            >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="saleRecordForm.sellway"
                                label="3"
                                >银行卡</el-radio
                            >
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="顾客类型：">
                            <el-radio
                                disabled
                                type="radio"
                                v-model="saleRecordForm.type"
                                label="1"
                                >会员</el-radio
                            >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="saleRecordForm.type"
                                label="0"
                                >非会员</el-radio
                            >
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" v-if="saleRecordForm.type == '1'">
                        <el-form-item
                            style="width: 60%"
                            label="会员账号："
                            prop="memberPhone"
                        >
                            <el-input
                                readonly
                                v-model="saleRecordForm.memberPhone"
                                placeholder="如：电话号码"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <hr>
                <el-row>
                    <el-col :span="24">
                        <el-table
                            :data="saleRecordForm.detailSaleRecords"
                            style="width: 100%"
                            size="medium"
                        >
                            <el-table-column prop="goodsId" label="商品编号">
                            </el-table-column>
                            <el-table-column prop="goodsName" label="商品名">
                            </el-table-column>
                            <el-table-column prop="goodsNum" label="数量">
                                <template v-slot="scope">
                                    <el-button type="info" disabled>-</el-button>
                                    <el-input
                                        disabled
                                        style="width: 80px"
                                        type="number"
                                        min="1"
                                        v-model="scope.row.goodsNum"
                                    ></el-input>
                                    <el-button type="info" disabled>+</el-button>
                                </template>
                            </el-table-column>
                            <el-table-column prop="goodsPrice" label="商品单价（元）">
                            </el-table-column>
                        </el-table>
                    </el-col>
                </el-row>
                <hr>
                <el-row>
                    <el-col :span="24">
                        <el-form-item style="width: 100%" label="备注：">
                            <el-input
                                disabled
                                readonly
                                type="textarea"
                                v-model="saleRecordForm.info"
                                placeholder="如：订单1"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div v-if="saleRecordForm.detailSaleRecords.length > 0">
                            订单号：{{ saleRecordForm.cn }}&nbsp;&nbsp;
                            商品总数：{{
                                saleRecordForm.sellTotal
                                    ? saleRecordForm.sellTotal
                                    : 0
                            }}&nbsp;&nbsp; 消费：{{
                                saleRecordForm.sellTotalmoney
                                    ? parseFloat(
                                          saleRecordForm.sellTotalmoney
                                      ).toFixed(2)
                                    : 0
                            }}元&nbsp;&nbsp;
                            <span v-if="saleRecordForm.sellway"
                                >支付方式：{{
                                    saleRecordForm.sellway == "0"
                                        ? "支付宝"
                                        : saleRecordForm.sellway == "1"
                                        ? "微信"
                                        : saleRecordForm.sellway == "2"
                                        ? "现金"
                                        : "银行卡"
                                }}&nbsp;&nbsp;</span
                            ><br>
                            <span v-if="saleRecordForm.type == '1'"
                                >会员享受9折&nbsp;&nbsp;</span
                            >
                            <span v-if="saleRecordForm.sellTime"
                                >消费时间：{{
                                    saleRecordForm.sellTime
                                }}&nbsp;&nbsp; 操作者编号：{{
                                    saleRecordForm.eid
                                }}</span
                            >
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item>
                            <el-button
                                type="info"
                                @click="detailSaleRecordsVisable = false"
                                >关闭</el-button
                            >
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    delSaleRecords,
    queryPageByQoSaleRecords,
} from "@/api/sale_management/saleRecordsApi";

export default {
    data() {
        return {
            detailSaleRecordsVisable: false,
            searchForm: {
                pageSize: 5
            },
            tableData: [],
            saleRecordForm: {
                cn: "",
                sellway: "",
                info: "",
                sellTotal: "",
                sellTotalmoney: "",
                type: "0",
                detailSaleRecords: [],
            },
        };
    },
    methods: {
        init() {
            if (this.searchForm.startSellTime > this.searchForm.endSellTime) {
                popup("结束时间不能小于开始时间", "warning");
                return;
            }
            queryPageByQoSaleRecords(this.searchForm).then((res) => {
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
                    delSaleRecords({ cn: cn }).then((res) => {
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
        detailBtn(row) {
            this.saleRecordForm = {
                cn: "",
                sellway: "",
                info: "",
                sellTotal: "",
                sellTotalmoney: "",
                type: "0",
                detailSaleRecords: [],
            };
            this.saleRecordForm = { ...row };
            this.detailSaleRecordsVisable = true;
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
</style>
