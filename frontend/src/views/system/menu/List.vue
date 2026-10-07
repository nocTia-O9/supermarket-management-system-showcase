<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>系统管理</el-breadcrumb-item>
            <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
             </el-breadcrumb
        ><br />
        <el-row>
            <el-col :span="8" style="text-align: left; padding-right: 10px">
                <el-input placeholder="菜单名称" v-model="searchForm.name" />
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
                    type="info"
                    @click="resetSearchForm"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-refresh" style="font-size: 18px">
                    </i>
                    重置
                </el-button>
            </el-col>
        </el-row>
        <br />
        <div class="table">
            <el-table
                :data="tableData"
                style="width: 100%"
                size="medium"
                row-key="id"
                border
                :tree-props="{ children: 'children' }"
            >
                <el-table-column prop="label" label="菜单名"> </el-table-column>
                <el-table-column
                width="150"
                    prop="flag"
                    :show-overflow-tooltip="true"
                    label="标识符"
                >
                    <template v-slot="scope">
                        <el-tag type="info">{{
                            scope.row.flag ? scope.row.flag : "暂定"
                        }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="类型">
                    <template v-slot="scope">
                        <el-tag  v-if="scope.row.type == 0"
                            >目录</el-tag
                        >
                        <el-tag
                            
                            v-else-if="scope.row.type == 1"
                            type="success"
                            >菜单</el-tag
                        >
                        <el-tag  v-else type="warning"
                            >按钮</el-tag
                        >
                    </template>
                </el-table-column>
                <el-table-column prop="icon" label="图标">
                    <template v-slot="scope">
                        <i :class="scope.row.icon"></i>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="info"
                    :show-overflow-tooltip="true"
                    label="描述"
                >
                    <template v-slot="scope">
                        <el-tag type="info">{{
                            scope.row.info ? scope.row.info : "无"
                        }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="component"
                    :show-overflow-tooltip="true"
                    label="组件路径"
                >
                    <template v-slot="scope">
                        <el-tag type="info">{{
                            scope.row.component ? scope.row.component : "暂定"
                        }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="state" label="状态">
                    <template v-slot="scope">
                        <el-tag
                            
                            type="success"
                            v-if="scope.row.state == 0"
                            >正常</el-tag
                        >
                        <el-tag  type="danger" v-else>禁用</el-tag>
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
import { queryPageByQo } from "@/api/system/menu/menuApi";
import { popup } from "@/assets/js/common";

export default {
    data() {
        return {
            /*搜索*/
            /*表格*/
            tableData: [],
            searchForm: {
                total: 0,
                currentPage: 1,
                pageSize: 5,
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
        resetSearchForm() {
            this.searchForm.name = "";
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
