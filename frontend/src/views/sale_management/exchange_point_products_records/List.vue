<template>
    <div id="sale_records">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item
                >首页</el-breadcrumb-item
            >
            <el-breadcrumb-item
                >销售管理
            </el-breadcrumb-item>
            <el-breadcrumb-item
                >积分兑换记录</el-breadcrumb-item
            > </el-breadcrumb
        ><br />
        <el-row>
            <el-col :span="7">
                <el-input
                    placeholder="兑换订单编号"
                    v-model="searchForm.cn"
                />
            </el-col>
            <el-col :span="7">
                <el-select
                    v-model="searchForm.memberId"
                    placeholder="请选择会员账号"
                    filterable
                    @change="$forceUpdate()"
                    clearable
                >
                    <el-option
                        v-for="item in options_memberPhone"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    >
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="10">
                <span>兑换时间：</span>
                <el-date-picker
                    style="width: 140px"
                    v-model="searchForm.startTime"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="起始时间"
                >
                </el-date-picker>
                -
                <el-date-picker
                    v-model="searchForm.endTime"
                    style="width: 140px"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="结束时间"
                >
                </el-date-picker>
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="24" style="text-align: left">
                <el-button
                    type="success"
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
                <el-table-column prop="cn" width="200" label="编号">
                </el-table-column>
                <el-table-column prop="goodsCoverUrl" label="商品封面">
                    <template v-slot="scope">
                        <img
                            :src="BaseApi + scope.row.goodsCoverUrl"
                            alt="兑换商品图片"
                            height="50px"
                        />
                    </template>
                </el-table-column>
                <el-table-column prop="goodsName" label="商品">
                </el-table-column>
                <el-table-column prop="integral" label="积分">
                </el-table-column>
                <el-table-column
                    width="200"
                    prop="memberPhone"
                    label="会员账号"
                >
                </el-table-column>
                <el-table-column prop="updateTime" width="200" label="兑换时间">
                </el-table-column>
                <el-table-column prop="updateby" label="操作者">
                </el-table-column>
                <el-table-column width="140" fixed="right" label="操作">
                    <template v-slot="scope">
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
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    delExchangePointProducts,
    queryOptionsMemberPhone,
    queryPageByQoExchangePointProducts,
} from "@/api/sale_management/exchange_point_products/exchangePointProductsApi";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            searchForm: {
                pageSize: 5,
            },
            tableData: [],
            options_memberPhone: [],
        };
    },
    methods: {
        queryOptionsMemberPhone() {
            queryOptionsMemberPhone().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_memberPhone = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        init() {
            if (this.searchForm.startTime > this.searchForm.endTime) {
                popup("结束时间不能小于开始时间", "warning");
                return;
            }
            queryPageByQoExchangePointProducts(this.searchForm).then((res) => {
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
                    delExchangePointProducts({ cn: cn }).then((res) => {
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
        /*分页*/
        handleSizeChange(val) {
            this.searchForm.pageSize = val;
            this.init();
        },
        handleCurrentChange(val) {
            this.searchForm.currentPage = val;
            this.init();
        },
        submitSearchForm() {
            this.init();
        },
    },
    mounted() {
        this.init();
        this.queryOptionsMemberPhone();
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
