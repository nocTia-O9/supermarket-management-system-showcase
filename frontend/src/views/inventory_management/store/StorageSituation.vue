<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>库存管理 </el-breadcrumb-item>
            <el-breadcrumb-item>库存统计</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8">
                <el-input placeholder="仓库名称" v-model="searchForm.name" />
            </el-col>
            <el-col :span="16">
                <i class="iconfont icon-r-add" style="font-size: 28px">
                    <b>
                        总存储量：<span style="color: #fc4316"
                            >{{ totalStoreNum ? totalStoreNum : 0 }}件</span
                        >
                    </b>
                </i>
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
            </el-col>
        </el-row>
        <br />
        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column type="index" width="200" label="序号">
                </el-table-column>
                <el-table-column
                    prop="storeName"
                    :show-overflow-tooltip="true"
                    label="仓库名"
                >
                </el-table-column>
                <el-table-column
                    prop="residueNum"
                    :show-overflow-tooltip="true"
                    label="存货数量"
                >
                </el-table-column>
                <el-table-column label="操作">
                    <template v-slot="scope">
                        <el-button
                            type="primary"
                            style="font-size: 18px"
                            @click="detailStoreBtn(scope.row)"
                        >
                            <i
                                class="iconfont icon-r-find"
                                style="font-size: 18px"
                            ></i>
                            存储明细</el-button
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
        <el-dialog
            title="仓库存储明细"
            :visible.sync="detailStoreVisible"
            width="95%"
            height="100%"
        >
            <el-row>
                <el-col :span="8" style="text-align: right;">
                    <el-select
                        v-model="searchDetailForm.id"
                        placeholder="请选择商品"
                        filterable
                        @change="$forceUpdate()"
                        clearable
                    >
                        <el-option
                            v-for="item in options_storeGoods"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="8">
                    <el-button type="success" @click="submitDetailSearchForm" style="font-size: 18px;"
                        >
                        <i class="iconfont icon-r-find" style="font-size: 18px;"></i>
                         搜索</el-button
                    >
                    <el-button @click="closeDetailStoreVisible" style="font-size: 18px;"
                        >
                        <i class="iconfont icon-r-no" style="font-size: 18px;"></i>
                         关闭</el-button
                    >
                </el-col>
                <el-col :span="8">
                    
                <i class="iconfont icon-r-add" style="font-size: 28px">
                    <b>
                        总存储量：<span style="color: #fc4316"
                            >{{ totalStoreNum1 ? totalStoreNum1 : 0 }}件</span
                        >
                    </b>
                </i>
                </el-col>
            </el-row>
            <br>
            <div class="table" style="overflow: auto;height: 300px;">
                <el-table :data="tableData1" style="width: 100%" size="medium" >
                    <el-table-column type="index" width="200" label="序号">
                    </el-table-column>
                    <el-table-column
                        prop="goodsName"
                        :show-overflow-tooltip="true"
                        label="商品名"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="residueNum"
                        :show-overflow-tooltip="true"
                        label="商品数量"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="percentage"
                        :show-overflow-tooltip="true"
                        label="占总存储量"
                    >
                        <template v-slot="scope">
                            <el-progress
                                type="circle"
                                width="70"
                                :percentage="scope.row.percentage"
                                :color="customColorMethod"
                            ></el-progress>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="margin: 10px 0 15px 0">
                    <el-pagination
                        @size-change="handleSizeChange1"
                        @current-change="handleCurrentChange1"
                        :current-page.sync="searchDetailForm.currentPage"
                        :page-sizes="[5, 10, 20, 50]"
                        :page-size="searchDetailForm.pageSize"
                        layout="total,sizes, prev, pager, next,jumper"
                        :total="searchDetailForm.total"
                    >
                    </el-pagination>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    queryPageByQo,
    queryStoreGoodsByStoreId,
} from "@/api/inventory_management/storage_situation/storageSituationApi";

export default {
    data() {
        return {
            /*表格*/
            tableData: [],
            tableData1: [],
            totalStoreNum: "",
            totalStoreNum1: "",
            searchForm: {
                total: 0,
                currentPage: 1,
                pageSize: 5,
            },
            searchDetailForm: {
                total: 0,
                currentPage: 1,
                pageSize: 10,
            },
            options_storeGoods: [],
            detailStoreVisible: false,
        };
    },
    methods: {
        init() {
            queryPageByQo(this.searchForm).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.totalStoreNum = res.data.totalStoreNum;
                    this.tableData = res.data.page.records;
                    this.searchForm.total = res.data.page.total;
                    this.searchForm.pageSize = res.data.page.size;
                    this.searchForm.currentPage = res.data.page.current;
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
        init1() {
            queryStoreGoodsByStoreId(this.searchDetailForm).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_storeGoods = res.data.optionsStoreGoods;
                    this.totalStoreNum1 = res.data.totalStoreNum1;
                    this.tableData1 = res.data.vos.records;
                    this.searchDetailForm.total = res.data.vos.total;
                    this.searchDetailForm.pageSize = res.data.vos.size;
                    this.searchDetailForm.currentPage = res.data.vos.current;
                    this.detailStoreVisible = true;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        detailStoreBtn(row) {
            this.options_storeGoods = [];
            this.totalStoreNum1 = "";
            this.searchDetailForm.storeId = row.storeId;
            this.init1();
        },
        handleSizeChange1(val) {
            this.searchDetailForm.pageSize = val;
            this.init1();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange1(val) {
            this.searchDetailForm.currentPage = val;
            this.init1();
            console.log(`当前页: ${val}`);
        },
        submitDetailSearchForm() {
            this.init1();
        },
        customColorMethod(percentage) {
            if (percentage < 30) {
                return "#f56c6c";
            } else if (percentage < 70) {
                return "#e6a23c";
            } else {
                return "#67c23a";
            }
        },
        closeDetailStoreVisible() {
            this.searchDetailForm = {
                total: 0,
                currentPage: 1,
                pageSize: 10,
            };
            this.detailStoreVisible = false;
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
