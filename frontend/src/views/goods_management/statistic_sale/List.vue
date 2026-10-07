<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理 </el-breadcrumb-item>
            <el-breadcrumb-item>销量统计</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8">
                <el-input v-model="searchForm.name" placeholder="商品名称" />
            </el-col>
            <el-col :span="16">
                <i class="iconfont icon-r-add" style="font-size: 28px">
                    <b>
                        总销量：<span style="color: #fc4316"
                            >{{
                                totalSalesVolume ? totalSalesVolume : 0
                            }}件</span
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
                    prop="coverUrl"
                    :show-overflow-tooltip="true"
                    label="封面"
                >
                    <template v-slot="scope">
                        <img width="60px" :src="BaseApi + scope.row.coverUrl" />
                    </template>
                </el-table-column>
                <el-table-column
                    prop="goodsName"
                    :show-overflow-tooltip="true"
                    label="商品名"
                >
                </el-table-column>
                <el-table-column
                    prop="salesVolume"
                    :show-overflow-tooltip="true"
                    label="销量"
                >
                </el-table-column>
                <el-table-column
                    prop="percentage"
                    :show-overflow-tooltip="true"
                    label="销售量占比"
                >
                    <template v-slot="scope">
                        <el-progress
                            width="50"
                            type="dashboard"
                            :percentage="scope.row.percentage"
                            :color="customColorMethod"
                        ></el-progress>
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
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import { queryPageByQo } from "@/api/goods_management/statistic/statisticSaleApi";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            tableData: [],
            totalSalesVolume: "", //所有商品总销量
            searchForm: {
                pageSize: 5,
            },
            options_goods: [],
        };
    },
    methods: {
        /*分页*/
        init() {
            /*查询商品信息*/
            queryPageByQo(this.searchForm).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.totalSalesVolume = res.data.total;
                    this.tableData = res.data.vos.records;
                    this.searchForm.total = res.data.vos.total;
                    this.searchForm.pageSize = res.data.vos.size;
                    this.searchForm.currentPage = res.data.vos.current;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
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
        customColorMethod(percentage) {
            if (percentage < 30) {
                return "#f56c6c";
            } else if (percentage < 70) {
                return "#e6a23c";
            } else {
                return "#67c23a";
            }
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
