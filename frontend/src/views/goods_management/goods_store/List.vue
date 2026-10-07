<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>商品库存</el-breadcrumb-item> </el-breadcrumb
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
                <el-table-column width="200" prop="id" label="编号">
                </el-table-column>
                <el-table-column prop="coverUrl" label="封面">
                    <template v-slot="scope">
                        <img
                            height="60px"
                            :src="BaseApi + scope.row.coverUrl"
                        />
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="商品名"> </el-table-column>
                <el-table-column prop="inventory" label="基本库存量">
                </el-table-column>
                <el-table-column prop="shelves" label="货架基本数量">
                </el-table-column>
                <el-table-column label="操作" width="200" fixed="right">
                    <template v-slot="scope">
                        <el-button
                            type="success"
                            @click="editBtn(scope.row.id)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-edit"
                                style="font-size: 18px"
                            >
                            </i>
                            修改库存量</el-button
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
        <!--修改库存量-->
        <el-dialog
            title="修改商品库存量"
            :visible.sync="editVisible"
            width="60%"
        >
            <el-form
                :model="editForm"
                :rules="rules"
                ref="editForm"
                label-width="150px"
                class="demo-ruleForm"
            >
                <el-form-item style="width: 60%" label="商品名：">
                    <el-input
                        disabled
                        v-model="editForm.name"
                        placeholder="如：农夫山泉"
                    ></el-input>
                </el-form-item>

                <el-form-item
                    label="基本库存量："
                    style="width: 60%"
                    prop="inventory"
                >
                    <el-input
                        type="number"
                        @change="
                            () => {
                                if (editForm.inventory <= 0) {
                                    editForm.inventory = 1;
                                }
                            }
                        "
                        v-model="editForm.inventory"
                        placeholder="如：10"
                    ></el-input>
                </el-form-item>
                <el-form-item
                    label="货架基本数量："
                    style="width: 60%"
                    prop="shelves"
                >
                    <el-input
                        type="number"
                        @change="
                            () => {
                                if (editForm.shelves <= 0) {
                                    editForm.shelves = 1;
                                }
                            }
                        "
                        v-model="editForm.shelves"
                        placeholder="如：10"
                    ></el-input>
                </el-form-item>
                <el-form-item style="width: 60%">
                    <el-button
                        type="primary"
                        @click="submitEditForm('editForm')"
                        
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-yes"
                                style="font-size: 18px"
                            >
                            </i> 提交</el-button
                    >
                    <el-button @click="closeEdit('editForm')"
                            style="font-size: 18px"
                        >关闭</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    queryGoodsStoreById,
    queryPageByQo,
    updateInventory,
} from "@/api/goods_management/goods_store/goodsStoreApi";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            editVisible: false,
            /*表格*/
            tableData: [],
            editForm: {},
            searchForm: {
                total: 0,
                currentPage: 1,
                pageSize: 5,
                state: "0",
            },
            rules: {
                inventory: [
                    {
                        required: true,
                        message: "库存量不能为空",
                        trigger: "blur",
                    },
                ],
                shelves: [
                    {
                        required: true,
                        message: "货架数量不能为空",
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
        editBtn(id) {
            queryGoodsStoreById({ id: id }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.editForm = { ...res.data };
                    this.editVisible = true;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        submitEditForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    updateInventory(this.editForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("更新成功");
                            this.editForm = {};
                            this.editVisible = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        closeEdit(formName) {
            this.$refs[formName].resetFields();
            this.editForm = {};
            this.editVisible = false;
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
