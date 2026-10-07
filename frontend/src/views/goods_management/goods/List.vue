<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>商品信息</el-breadcrumb-item> </el-breadcrumb
        ><br />
        <el-row>
            <el-col :span="6" style="text-align: left; padding-right: 10px">
                <el-input placeholder="商品编号" v-model="searchForm.id" />
            </el-col>
            <el-col :span="6" style="text-align: left; padding-right: 10px">
                <el-input placeholder="商品名称" v-model="searchForm.name" />
            </el-col>
            <el-col :span="6" style="text-align: left; padding-right: 10px">
                <el-input
                    placeholder="商品售价"
                    v-model="searchForm.sellPrice"
                />
            </el-col>
            <el-col :span="6">
                <el-select
                    v-model="searchForm.categoryId"
                    placeholder="商品分类"
                    filterable
                    @change="$forceUpdate()"
                    clearable
                >
                    <el-option
                        v-for="item in options_category"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    >
                    </el-option>
                </el-select>
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="6" style="text-align: left; padding-right: 10px">
                <el-select
                    v-model="searchForm.state"
                    placeholder="请选择状态"
                    clearable
                >
                    <el-option
                        v-for="item in options_state"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="18">
                <span>时间：</span>
                <el-date-picker
                    v-model="searchForm.operateStartTime"
                    type="date"
                    placeholder="起始时间"
                >
                </el-date-picker>
                -
                <el-date-picker
                    v-model="searchForm.operateEndTime"
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
                    创建商品
                </el-button>
            </el-col>
        </el-row>
        <br />
        <div class="table">
            <el-table :data="tableData" style="width: 100%" size="medium">
                <el-table-column type="index" label="序号"> </el-table-column>
                <el-table-column prop="coverUrl" label="封面">
                    <template v-slot="scope">
                        <img
                            height="60px"
                            :src="BaseApi + scope.row.coverUrl"
                        />
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="编号"> </el-table-column>
                <el-table-column prop="name" label="名称"> </el-table-column>
                <el-table-column prop="sellPrice" label="售价">
                </el-table-column>
                <el-table-column prop="purchashPrice" label="成本价">
                </el-table-column>
                <el-table-column prop="residueNum" label="在售数量">
                </el-table-column>
                <el-table-column prop="categoryName" label="类型">
                </el-table-column>
                <el-table-column prop="state" label="状态">
                    <template v-slot="scope">
                        <el-tag
                            size="mini"
                            type="success"
                            v-if="scope.row.state == '0'"
                            >上架</el-tag
                        >
                        <el-tag size="mini" type="danger" v-else>下架</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="residueStoreNum" label="库存量">
                    <template v-slot="scope">
                        {{
                            scope.row.residueStoreNum
                                ? scope.row.residueStoreNum
                                : 0
                        }}
                    </template>
                </el-table-column>
                <el-table-column prop="updateby" label="操作者">
                </el-table-column>
                <el-table-column
                    prop="updateTime"
                    :show-overflow-tooltip="true"
                    label="操作时间"
                >
                </el-table-column>
                <el-table-column
                    prop="info"
                    :show-overflow-tooltip="true"
                    label="备注"
                >
                </el-table-column>
                <el-table-column width="360" fixed="right" label="操作">
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
                            修改</el-button
                        >
                        <el-button
                            type="danger"
                            @click="returnBtn(scope.row.id)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-left"
                                style="font-size: 18px"
                            >
                            </i>
                            退还</el-button
                        >
                        <el-button
                            v-if="scope.row.state == '0'"
                            type="warning"
                            @click="
                                upOrdown(
                                    scope.row.id,
                                    scope.row.state,
                                    scope.row.residueNum
                                )
                            "
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-bottom"
                                style="font-size: 18px"
                            >
                            </i>
                            下架
                        </el-button>
                        <el-button
                            v-if="scope.row.state == '1'"
                            type="warning"
                            @click="upOrdown(scope.row.id, scope.row.state)"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-top"
                                style="font-size: 18px"
                            >
                            </i>
                            上架
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
        <!--创建商品表单弹出-->
        <el-dialog
            title="创建商品"
            :visible.sync="newVisable"
            @close="dialog_imgClose"
            width="50%"
        >
            <el-form
                :model="newForm"
                :rules="rules"
                ref="newForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="商品图：">
                    <!--                    action="http://localhost:9291/goods_management/goods/uploadImg"-->
                    <el-upload
                        class="avatar-uploader"
                        action="http://localhost:9291/goods_management/goods/uploadImg"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                    >
                        <img
                            v-if="imageUrl"
                            :src="BaseApi + imageUrl"
                            class="avatar"
                        />
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="商品名：" prop="name">
                    <el-input
                        v-model="newForm.name"
                        placeholder="如：农夫山泉"
                    ></el-input>
                </el-form-item>
                <el-form-item label="成本价：" prop="purchashPrice">
                    <el-input
                        v-model="newForm.purchashPrice"
                        placeholder="如：9.9"
                    ></el-input>
                </el-form-item>
                <el-form-item label="销售价格：">
                    <el-input
                        v-model="newForm.sellPrice"
                        placeholder="如：9.9"
                    ></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input
                        type="textarea"
                        placeholder="如：农夫山泉是矿泉水"
                        v-model="newForm.info"
                    ></el-input>
                </el-form-item>
                <el-form-item label="分类：">
                    <el-select
                        v-model="newForm.categoryId"
                        placeholder="请选择分类"
                        filterable
                        @change="$forceUpdate()"
                        clearable
                    >
                        <el-option
                            v-for="item in options_category"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitNewForm('newForm')"
                        >提交</el-button
                    >
                    <el-button @click="saveCancel('newForm')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--修改表单-->
        <el-dialog
            title="修改商品信息"
            :visible.sync="dialogVisible"
            @close="dialog_imgClose"
            width="50%"
        >
            <el-form
                :model="editForm"
                :rules="rules"
                ref="editForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <!--                action="http://localhost:9291/goods_management/goods/uploadImg"-->
                <el-upload
                    class="avatar-uploader"
                    action="http://localhost:9291/goods_management/goods/uploadImg"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                >
                    <img
                        v-if="imageUrl"
                        :src="BaseApi + imageUrl"
                        class="avatar"
                    />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <el-form-item label="商品名：" prop="name">
                    <el-input
                        v-model="editForm.name"
                        placeholder="如：农夫山泉"
                    ></el-input>
                </el-form-item>
                <el-form-item label="成本价：" prop="purchashPrice">
                    <el-input
                        v-model="editForm.purchashPrice"
                        placeholder="如：9.9"
                    ></el-input>
                </el-form-item>
                <el-form-item label="销售价格：">
                    <el-input
                        v-model="editForm.sellPrice"
                        placeholder="如：9.9"
                    ></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input
                        type="textarea"
                        placeholder="如：农夫山泉是矿泉水"
                        v-model="editForm.info"
                    ></el-input>
                </el-form-item>
                <el-form-item style="width: 40%" label="分类：">
                    <el-select
                        v-model="editForm.categoryId"
                        placeholder="请选择分类"
                        filterable
                        @change="$forceUpdate()"
                        clearable
                    >
                        <el-option
                            v-for="item in options_category"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        @click="submitEditForm('editForm')"
                        >提交</el-button
                    >
                    <el-button @click="resetEditForm('editForm')"
                        >关闭</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--退还表单-->
        <el-dialog
            title="退还商品入库信息"
            :visible.sync="returnVisible"
            width="60%"
        >
            <el-form
                :model="returnForm"
                :rules="rules"
                ref="returnForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="商品名：">
                            <el-input
                                readonly
                                v-model="returnForm.goodsName"
                                placeholder="如：农夫山泉"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="仓库：" prop="storeId">
                            <el-select
                                v-model="returnForm.storeId"
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
                        <el-form-item label="数量：" prop="goodsNum">
                            <el-input
                                type="number"
                                @change="
                                    () => {
                                        if (
                                            this.goodsNum_max <
                                            this.returnForm.goodsNum
                                        ) {
                                            this.returnForm.goodsNum =
                                                this.goodsNum_max;
                                        }
                                        if (this.returnForm.goodsNum < 1) {
                                            this.returnForm.goodsNum = 1;
                                        }
                                    }
                                "
                                min="1"
                                :max="goodsNum_max"
                                v-model="returnForm.goodsNum"
                                placeholder="如：1"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类型：" prop="state">
                            <el-select
                                v-model="returnForm.state"
                                @change="$forceUpdate"
                                placeholder="请选择类型"
                                clearable
                            >
                                <el-option label="过期" value="1"></el-option>
                                <el-option label="入库" value="0"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="描述：">
                            <el-input
                                type="textarea"
                                placeholder="如：该商品正执行下架操作"
                                v-model="returnForm.info"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="日期：" prop="createTime">
                            <el-date-picker
                                size="mini"
                                value-format="yyyy-MM-dd"
                                v-model="returnForm.createTime"
                                type="date"
                                placeholder="退还日期"
                            >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item style="width: 80%">
                    <el-button
                        type="primary"
                        @click="submitReturnForm('returnForm')"
                            style="font-size: 18px"
                        >
                            <i
                                class="iconfont icon-r-yes"
                                style="font-size: 18px"
                            >
                            </i> 确定</el-button
                    >
                    <el-button @click="resetReturnForm('returnForm')"
                        
                            style="font-size: 18px"
                        >关闭</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    queryPageByQo,
    save,
    update,
    upOrdown,
    queryGoodsById,
    selected_storeAll,
    returnGoods,
} from "@/api/goods_management/goods/goodsApi";
import { getNormalCategoryAll } from "@/api/goods_management/goods_category/goodsCategoryApi";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            /*搜索*/
            options_category: [],
            options_state: [
                { label: "上架", value: "0" },
                { label: "下架", value: "1" },
            ],
            goodsNum_max: "",
            options_store: [],
            newVisable: false,
            dialogVisible: false,
            returnVisible: false,
            returnForm: {
                goodsId: "",
                goodsNum: "",
                goodsName: "",
                createTime: "",
                info: "",
                storeId: "",
            },
            newForm: {},
            editForm: {
                id: "",
                name: "",
                info: "",
                categoryId: "",
                sellPrice: "",
                purchashPrice: "",
                state: "",
                coverUrl: "",
            },
            imageUrl: "",
            /*表格*/
            tableData: [],
            searchForm: {
                total: 0,
                currentPage: 1,
                pageSize: 5,
                state: "0",
            },
            rules: {
                name: [
                    {
                        required: true,
                        message: "商品名不能为空",
                        trigger: "blur",
                    },
                ],
                purchashPrice: [
                    {
                        required: true,
                        message: "成本价不能为空",
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
                createTime: [
                    {
                        required: true,
                        message: "退还日期不能为空",
                        trigger: "blur",
                    },
                ],
                state: [
                    {
                        required: true,
                        message: "退还类型不能为空",
                        trigger: "blur",
                    },
                ],
                goodsNum: [
                    {
                        required: true,
                        message: "退还数量不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        normalCategoryAll() {
            getNormalCategoryAll().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_category = res.data;
                }
            });
        },
        normalStoreAll() {
            selected_storeAll().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_store = res.data;
                }
            });
        },
        dialog_imgClose() {
            this.imageUrl = "";
        },
        init() {
            if (
                this.searchForm.operateStartTime >
                this.searchForm.operateEndTime
            ) {
                popup("结束时间不能小于开始时间", "warning");
                return;
            }
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
        /*创建新的商品*/
        saveCancel(formName) {
            this.newForm = {};
            this.$refs[formName].resetFields();
            this.newVisable = false;
        },
        /*上传头像*/
        handleAvatarSuccess(res) {
            this.imageUrl = res.url;
        },
        beforeAvatarUpload(file) {
            const isJPG =
                file.type === "image/jpeg" || file.type === "image/png";
            const isLt2M = file.size / 1024 / 1024 < 3;
            if (!isJPG) {
                this.$message.error("上传头像图片只能是 JPG或PNG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传头像图片大小不能超过 3MB!");
            }
            return isJPG && isLt2M;
        },
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.newForm.coverUrl = this.imageUrl;
                    save(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.newForm = {};
                            this.imageUrl = "";
                            this.newVisable = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        /*上/下架业务*/
        upOrdown(gid, state, residueNum) {
            if (state == "0") {
                if (residueNum) {
                    popup("请先将该商品从货架上退还给仓库", "warning");
                    return;
                }
            }
            this.$confirm(
                "确定要将该商品" + (state == "0" ? "下架" : "上架") + "？",
                "警示",
                {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }
            )
                .then(() => {
                    upOrdown({ gid: gid, state: state }).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            if (state == "0") {
                                popup("下架成功");
                            } else {
                                popup("上架成功");
                            }
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
        resetReturnForm(formName) {
            this.$refs[formName].resetFields();
            this.returnVisible = false;
            this.returnForm = {};
        },
        /*修改*/
        submitEditForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.editForm.coverUrl = this.imageUrl;
                    update(this.editForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("操作成功");
                            this.editForm = {};
                            this.imageUrl = "";
                            this.dialogVisible = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        resetEditForm(formName) {
            this.$refs[formName].resetFields();
            this.editForm = {};
            this.dialogVisible = false;
        },
        editBtn(id) {
            queryGoodsById({ id: id }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.editForm = {
                        id: res.data.id,
                        name: res.data.name,
                        info: res.data.info,
                        categoryId: res.data.categoryId,
                        sellPrice: res.data.sellPrice,
                        purchashPrice: res.data.purchashPrice,
                        state: res.data.state,
                        coverUrl: res.data.coverUrl,
                    };

                    this.imageUrl = this.editForm.coverUrl
                        ? this.editForm.coverUrl
                        : "";
                    this.dialogVisible = true;
                } else {
                    popup(res.msg, "warning");
                }
            });
        },
        returnBtn(id) {
            this.returnForm = {
                goodsId: "",
                goodsNum: "",
                goodsName: "",
                createTime: "",
                info: "",
                storeId: "",
            };
            queryGoodsById({ id: id }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.returnForm.goodsId = res.data.id;
                    this.returnForm.goodsName = res.data.name;
                    this.returnForm.goodsNum = res.data.residueNum;
                    if (this.returnForm.goodsNum == 0) {
                        popup("货架上没有商品", "warning");
                        return;
                    }
                    this.goodsNum_max = res.data.residueNum;
                    this.returnVisible = true;
                } else {
                    popup(res.msg, "warning");
                }
            });
        },
        submitReturnForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    returnGoods(this.returnForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("退还成功");
                            this.returnVisible = false;
                            this.init();
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
    },
    mounted() {
        this.init();
        this.normalCategoryAll();
        this.normalStoreAll();
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
