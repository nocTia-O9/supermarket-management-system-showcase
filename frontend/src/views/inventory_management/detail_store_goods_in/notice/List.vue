<template>
    <div>
        <div class="searchForm">
            <div class="column">
                <span>商品名：</span>
                <el-input style="height: 21px;width: 300px" placeholder="请输入商品名" v-model="searchForm.name"/>
            </div>
            <div class="column">
                <el-button type="success" @click="submitSearchForm">搜索</el-button>
            </div>
        </div>
        <div class="table">
            <el-table
                    :data="tableData"
                    style="width: 100%;"
                    
                    size="medium">
                <el-table-column
                        width="200"
                        type="index"
                        label="序号">
                </el-table-column>
                <el-table-column
                        prop="coverUrl"
                        label="封面">
                    <template v-slot="scope">
                        <img height="60px" :src="scope.row.coverUrl">
                    </template>
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="商品名">
                </el-table-column>
                <el-table-column
                        prop="purchashPrice"
                        label="进货单价">
                </el-table-column>
                <el-table-column
                        prop="goodsNum"
                        label="入库数量">
                </el-table-column>
                <el-table-column>
                    <template v-slot="scope">
                        <el-button type="success"
                                   @click="goodsInBtn(scope.row)" plain>入库
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
                <el-form-item style="width:40%" label="商品：" prop="goodsId">
                    <el-select disabled v-model="selectGoods.goodsId"
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
                <el-form-item style="width:40%" label="仓库：" prop="storeId">
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
                <el-form-item style="width:40%">
                    <el-button type="success" @click="selectedGoods('selectGoods')" plain>确定</el-button>
                    <el-button type="warning" @click="closeSelectedGoods('selectGoods')" plain>关闭</el-button>
                </el-form-item>
            </el-form>
            <el-form size="mini" v-if="newVisable" :model="newForm" :rules="rules" ref="newForm" label-width="100px"
                     class="demo-ruleForm">
                <el-row>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="商品名：">
                            <el-input readonly v-model="newForm.goodsName" placeholder="如：农夫山泉"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="商品数量：" prop="goodsNum">
                            <el-input v-model="newForm.goodsNum" :placeholder="'如：'+newGoodsNum"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="供货商：" prop="supplierId">
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
                        <el-form-item style="width: 60%" label="商品价格：" prop="goodsPrice">
                            <el-input v-model="newForm.goodsPrice" placeholder="如：1.0"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="生产日期：" prop="birthTime">
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
                        <el-form-item style="width: 60%" label="过期日期：" prop="expiryTime">
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
                        <el-form-item style="width: 82%" label="备注：">
                            <el-input type="textarea" v-model="newForm.info" placeholder="如：农夫山泉入库"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>
                <el-form-item>
                    <el-button type="primary" @click="submitNewForm('newForm')">入库</el-button>
                    <el-button @click="saveCancel('newForm')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>
<script>
    import {popup} from "@/assets/js/common";
    import {
        queryGoodsStoreById,
        queryPageByQo,
        updateInventory
    } from "@/api/goods_management/goods_store/goodsStoreApi";
    import {queryPageNoticeIn} from "@/api/inventory_management/notice/noticeApi";
    import {queryGoodsById, selected_goodsAll} from "@/api/goods_management/goods/goodsApi";
    import {queryOptionsSuppliers, saveIn} from "@/api/inventory_management/detail_store_goods/detail_store_goodsApi";
    import {storeList} from "@/api/inventory_management/store/storeApi";

    export default {
        data() {
            return {
                newVisable: false,
                goodsInVisable: false,
                selectGoodsVisable: true,
                newGoodsNum:'',
                newForm: {
                    cn: '',
                    goodsId: '',
                    goodsNum: '',
                    goodsName: '',
                    goodsPrice: '',
                    info: '',
                    expiryTime: '',
                    birthTime: '',
                    storeId: ''
                },
                /*表格*/
                tableData: [],
                searchForm: {
                    total: 0,
                    currentPage: 1,
                    pageSize: 10,
                    state: '0'
                },
                options_goods: [],
                options_store: [],
                options_suppliers:[],
                selectGoods: {},
                rules: {
                    supplierId:[
                        {required: true, message: '供应商不能为空', trigger: 'blur'},
                    ],
                    goodsId: [
                        {required: true, message: '商品编号不能为空', trigger: 'blur'},
                    ],
                    storeId: [
                        {required: true, message: '仓库编号不能为空', trigger: 'blur'},
                    ],
                    goodsNum: [
                        {required: true, message: '商品数量不能为空', trigger: 'blur'},
                    ],
                    goodsPrice: [
                        {required: true, message: '商品价格不能为空', trigger: 'blur'},
                    ],
                    expiryTime: [
                        {required: true, message: '过期时间不能为空', trigger: 'blur'},
                    ],
                    birthTime: [
                        {required: true, message: '生产时间不能为空', trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            init() {
                queryPageNoticeIn(this.searchForm).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        console.log(res.data)
                        this.tableData = res.data.records
                        this.searchForm.total = res.data.total
                        this.searchForm.pageSize = res.data.size
                        this.searchForm.currentPage = res.data.current
                    } else {
                        popup(res.msg, "error")
                    }
                })
            },
            /*分页*/
            handleSizeChange(val) {
                this.searchForm.pageSize = val
                this.init()
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.searchForm.currentPage = val
                this.init()
                console.log(`当前页: ${val}`);
            },
            submitSearchForm() {
                this.init()
            },

            detailStoreGoodsIn_goodsAll() {
                selected_goodsAll().then(res => {
                    res = res.data
                    if (res.code == 200) {
                        this.options_goods = res.data
                    } else {
                        popup(res.msg, 'error')
                    }
                })
            },
            detailStoreGoodsIn_suppliers(){
                queryOptionsSuppliers().then(res=>{
                        res=res.data
                        if (res.code==200){
                            this.options_suppliers=res.data
                        }else {
                            popup(res.msg,"error")
                        }
                    }
                )
            },
            detailStoreGoodsIn_storeAll() {
                storeList({state: '0'}).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        for (var item of res.data) {
                            this.options_store.push({id: item.id, name: item.name})
                        }
                    } else {
                        popup(res.msg, "error")
                    }
                })
            },
            selectedGoods(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        queryGoodsById({id: this.selectGoods.goodsId}).then(res => {
                            res = res.data
                            if (res.code == 200) {
                                this.selectGoodsVisable = false
                                this.newVisable = true
                                this.newForm.storeId = this.selectGoods.storeId
                                this.newForm.goodsId = res.data.id
                                this.newForm.goodsName = res.data.name
                                this.newForm.goodsPrice = res.data.purchashPrice
                            } else {
                                popup(res.msg, "error")
                            }
                        })
                    }
                })

            },
            closeSelectedGoods(formName){
                this.$refs[formName].resetFields()
                this.goodsInVisable=false
                this.newVisable = false
                this.selectGoodsVisable = true
                this.selectGoods={}

            },
            submitNewForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        saveIn(this.newForm).then(res => {
                            res = res.data
                            if (res.code == 200) {
                                popup("入库成功")
                                this.goodsInVisable=false
                                this.selectGoodsVisable = true
                                this.newVisable = false
                                this.init()
                            } else {
                                popup(res.msg, "error")
                            }
                        })
                    }
                })

            },
            saveCancel(formName) {
                this.$refs[formName].resetFields()
                this.newVisable = false
                this.selectGoodsVisable = true
            },
            goodsInBtn(row) {
                this.selectGoods.goodsId=row.id
                this.newGoodsNum=row.goodsNum
                this.goodsInVisable = true
            },
        },
        mounted() {
            this.searchForm.state1 = '0'
            this.detailStoreGoodsIn_goodsAll()
            this.detailStoreGoodsIn_storeAll()
            this.detailStoreGoodsIn_suppliers()
            this.init()
        }
    }
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
