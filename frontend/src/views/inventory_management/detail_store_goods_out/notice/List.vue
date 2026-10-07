<template>
    <div id="detail_store_goods_in">
        <div class="noticeOut">
            <h1>查看出库商品面板</h1>
            <div class="btns">
                <el-button @click="outGoodsBtn" style="width: 200px;height: 100px;background-color: #c09dff">出库货架商品
                </el-button>
                <el-button @click="outUntreated" style="width: 200px;height: 100px;background-color: #c09dff">过期/下架商品
                </el-button>

            </div>
        </div>
        <!--商品上货架-->
        <el-dialog
                :fullscreen="true"
                :visible.sync="visable1">
            <div class="searchForm">
                <div class="column">
                    <span>商品名：</span>
                    <el-input style="height: 21px;width: 300px" placeholder="请输入商品名" v-model="searchForm.name"/>
                </div>
                <div class="column">
                    <el-button type="success" @click="submitSearchForm">搜索</el-button>
                    <el-button type="primary" @click="visable1=false">关闭</el-button>
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
                            prop="goodsNum"
                            label="出货数量">
                    </el-table-column>
                    <el-table-column
                            width="100px"
                            label="操作">
                        <template v-slot="scope">
                            <el-button type="warning"
                                       @click="outGoodsBtn1(scope.row)" plain>出货
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
        </el-dialog>
        <!--商品出库-->
        <el-dialog
                title="商品出库"
                :visible.sync="goodsOutVisable"
                width="50%">
            <el-form v-if="selectGoodsVisable" :model="selectGoods" :rules="rules" ref="selectGoods" label-width="100px"
                     class="demo-ruleForm">
                <el-form-item style="width:40%" label="商品：" prop="goodsId">
                    <el-select disabled v-model="selectGoods.goodsId"
                               placeholder="请选择商品"
                               filterable
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
                               @change="$forceUpdate"
                               placeholder="请选择仓库"
                               filterable
                               clearable>
                        <el-option
                                v-for="item in options_store"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="width:40%" label="类型：" prop="state">
                    <el-select
                            v-model="selectGoods.state"
                            @change="$forceUpdate()"
                            filterable
                            placeholder="请选择类型" clearable>
                        <el-option label="出库" value="0"></el-option>
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
                        <el-form-item style="width:60%" label="类型：" prop="state">
                            <el-select disabled
                                       v-model="newForm.state"
                                       filterable
                                       @change="$forceUpdate()"
                                       placeholder="请选择类型" clearable>
                                <el-option label="出库" value="0"></el-option>
                                <el-option label="过期" value="1"></el-option>
                                <el-option label="下架" value="2"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="商品数量：" prop="goodsNum">
                            <el-input type="number" @change="()=>{
                                if(this.goodsNum_max<this.newForm.goodsNum){
                                    this.newForm.goodsNum=this.goodsNum_max
                                }
                                if(this.newForm.goodsNum<1){
                                    this.newForm.goodsNum=1
                                }

                            }" min="1" :max="goodsNum_max" v-model="newForm.goodsNum" placeholder="如：1"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item style="width:60%" label="仓库：" prop="storeId">
                            <el-select disabled v-model="newForm.storeId"
                                       placeholder="请选择仓库"
                                       filterable
                                       clearable>
                                <el-option
                                        v-for="item in options_store"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="出库日期：" prop="createTime">
                            <el-date-picker
                                    size="mini"
                                    style="width: 140px"
                                    value-format="yyyy-MM-dd"
                                    v-model="newForm.createTime"
                                    type="date"
                                    placeholder="出库日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item style="width: 60%" label="备注：">
                            <el-input type="textarea" v-model="newForm.info" placeholder="如：某商品因什么原因出库"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button type="primary" @click="submitNewForm('newForm')">出库</el-button>
                    <el-button @click="saveCancel('newForm')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--商品过期/下架列表显示-->
        <el-dialog
                :fullscreen="true"
                :visible.sync="visable2">
            <div class="searchForm">
                <div class="column">
                    <span>商品名：</span>
                    <el-input style="height: 21px;width: 300px" placeholder="请输入商品名" v-model="searchForm1.name"/>
                </div>
                <div class="column">
                    <span>类型：</span>
                    <el-select style="width: 120px"
                               v-model="searchForm1.state"
                               @change="$forceUpdate()"
                               placeholder="请选择类型" clearable>
                        <el-option label="过期" value="1"></el-option>
                        <el-option label="下架" value="2"></el-option>
                    </el-select>
                </div>
                <div class="column">
                    <el-button type="success" @click="submitSearchForm1">搜索</el-button>
                    <el-button type="primary" @click="visable2=false">关闭</el-button>
                </div>
            </div>
            <div class="table">
                <el-table
                        :data="tableData1"
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
                            prop="goodsName"
                            label="商品名">
                    </el-table-column>
                    <el-table-column
                            prop="storeName"
                            label="仓库">
                    </el-table-column>
                    <el-table-column
                            prop="untreatedNum"
                            label="处理数量">
                    </el-table-column>
                    <el-table-column
                            prop="state"
                            label="类型">
                        <template v-slot="scope">
                            <el-tag type="warning" v-if="scope.row.state=='1'">过期</el-tag>
                            <el-tag type="danger" v-if="scope.row.state=='2'">下架</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="通知时间">
                    </el-table-column>
                    <el-table-column
                            width="100px"
                            label="操作">
                        <template v-slot="scope">
                            <el-button type="warning"
                                       @click="outUntreatedBtn(scope.row)" plain>处理
                            </el-button>
                        </template>
                    </el-table-column>

                </el-table>
                <div style="margin: 10px 0 15px 0;">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page.sync="searchForm1.currentPage"
                            :page-sizes="[5, 10, 20, 50]"
                            :page-size="searchForm1.pageSize"
                            layout="total,sizes, prev, pager, next,jumper"
                            :total="searchForm1.total">
                    </el-pagination>
                </div>
            </div>
        </el-dialog>

        <!--处理表单填写-->
        <el-dialog
                title="过期/下架商品处理表单"
                :visible.sync="visable3"
                width="50%">
            <el-form v-if="" :model="outUntreatedForm" :rules="rules" ref="outUntreatedForm" label-width="100px"
                     class="demo-ruleForm">
                <el-row>
                    <el-col :span="24">
                        <el-form-item style="width: 60%">
                            <img height="200px" :src="outUntreatedForm.coverUrl" alt="商品图片"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item style="width: 60%" label="商品名称:">
                            <el-input readonly v-model="outUntreatedForm.goodsName" placeholder="商品名称"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item style="width: 60%" label="商品数量:" prop="untreatedNum">
                            <el-input
                                    @change="()=>{
                                        if (outUntreatedForm.untreatedNum<=0){
                                            outUntreatedForm.untreatedNum=1
                                        }
                                        if (outUntreatedForm.untreatedNum>untreatedNum){
                                            outUntreatedForm.untreatedNum=untreatedNum
                                        }
                                    }"
                                    :placeholder="'如：'+untreatedNum"
                                    type="number" min="1" :max="untreatedNum" v-model="outUntreatedForm.untreatedNum"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item style="width: 60%" label="仓库:">
                            <el-input readonly v-model="outUntreatedForm.storeName" placeholder="仓库"/>
                        </el-form-item>

                    </el-col>
                    <el-col :span="24">
                        <el-form-item style="width: 40%" label="类型:">
                            <el-select disabled
                                       v-model="outUntreatedForm.state"
                                       @change="$forceUpdate()"
                                       placeholder="请选择类型" clearable>
                                <el-option label="过期" value="1"></el-option>
                                <el-option label="下架" value="2"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="24">
                        <el-form-item style="width: 60%">
                            <el-button type="success" @click="submitOutUntreatedForm('outUntreatedForm')">处理</el-button>
                            <el-button type="primary" @click="closeOutUntreatedForm('outUntreatedForm')">关闭</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>

    </div>
</template>
<script>
    import {popup} from "@/assets/js/common";
    import {
        queryPageNoticeOut_shelves,
        queryPageNoticeOut_untreated, resolveOutUntreatedForm,
        saveOut_shelves
    } from "@/api/inventory_management/notice/noticeApi";
    import {
        changeOutGoods,
        changeOutStore,
        initOutOptions, queryOutGoods, saveOut
    } from "@/api/inventory_management/detail_store_goods/detail_store_goodsApi";

    export default {
        data() {
            return {
                visable1: false,
                visable2: false,
                visable3: false,
                untreatedNum: '',
                outUntreatedForm: {},
                goodsOutVisable: false,
                newVisable: false,
                selectGoodsVisable: true,
                selectGoods: {},
                options_goods: [],
                options_store: [],
                /*表格*/
                tableData: [],
                searchForm: {
                    total: 0,
                    currentPage: 1,
                    pageSize: 5,
                    state: '0'
                },
                searchForm1: {
                    total: 0,
                    currentPage: 1,
                    pageSize: 5,
                },
                tableData1: [],
                newForm: {
                    goodsId: '',
                    goodsNum: '',
                    goodsName: '',
                    info: '',
                    createTime: '',
                    storeId: '',
                    state: ''
                },
                rules: {
                    goodsId: [
                        {required: true, message: '商品编号不能为空', trigger: 'blur'},
                    ],
                    storeId: [
                        {required: true, message: '仓库编号不能为空', trigger: 'blur'},
                    ],
                    state: [
                        {required: true, message: '类型不能为空', trigger: 'blur'},
                    ],
                    goodsNum: [
                        {required: true, message: '出库数量不能为空', trigger: 'blur'},
                    ],
                    untreatedNum:[
                        {required: true, message: '处理数量不能为空', trigger: 'blur'},
                    ],
                    createTime: [
                        {required: true, message: '出库日期不能为空', trigger: 'blur'},
                    ]
                },
            }
        },
        methods: {
            outGoodsBtn() {
                this.init()
                this.visable1 = true
            },
            closeOutUntreatedForm(formName) {
                this.$refs[formName].resetFields()
                this.visable3 = false
                this.outUntreatedForm = {}
            },
            outGoodsBtn1(row) {
                this.selectGoods.goodsId = row.id
                var flag = false;
                for (var item of this.options_goods) {
                    if (item.id == this.selectGoods.goodsId) {
                        flag = true;
                    }

                }
                if (!flag) {
                    popup("库存中没有该商品的库存", "error")
                    return;
                }
                changeOutGoods({gid: this.selectGoods.goodsId}).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        this.options_store = res.data
                    }
                })
                this.goodsOutVisable = true
                this.selectGoodsVisable = true
                this.newVisable = false
                this.selectGoods.state = '0'
                this.goodsNum_max = row.goodsNum
                this.newForm = {
                    goodsId: '',
                    goodsNum: row.goodsNum,
                    goodsName: '',
                    info: '',
                    createTime: '',
                    storeId: '',
                    state: ''
                }

            },
            outUntreatedBtn(param) {
                this.outUntreatedForm = {...param}
                this.untreatedNum = param.untreatedNum
                this.visable3 = true
            },
            submitOutUntreatedForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        resolveOutUntreatedForm(this.outUntreatedForm).then(res => {
                                res = res.data
                                if (res.code == 200) {
                                    popup("处理成功")
                                    this.visable3 = false
                                    this.outUntreatedForm = {}
                                    this.init1()
                                } else {
                                    popup(res.msg, "error")
                                }
                            }
                        )
                    }
                })

            },
            outUntreated() {
                this.init1()
                this.visable2 = true
            },
            changeGoods() {
                this.$forceUpdate()
                changeOutGoods({gid: this.selectGoods.goodsId}).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        this.options_store = res.data
                    }
                })
            },
            initOptions() {
                initOutOptions().then(res => {
                    res = res.data
                    if (res.code == 200) {
                        this.options_goods = res.data.goods
                        this.options_store = res.data.stores

                    } else {
                        popup(res.msg, "error")
                    }
                })
            },
            init() {
                queryPageNoticeOut_shelves(this.searchForm).then(res => {
                    res = res.data
                    if (res.code == 200) {
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
            /*过期、下架分页*/
            init1() {
                queryPageNoticeOut_untreated(this.searchForm1).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        this.tableData1 = res.data.records
                        this.searchForm1.total = res.data.total
                        this.searchForm1.pageSize = res.data.size
                        this.searchForm1.currentPage = res.data.current
                    } else {
                        popup(res.msg, "error")
                    }
                })
            },
            handleSizeChange1(val) {
                this.searchForm1.pageSize = val
                this.init1()
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange1(val) {
                this.searchForm1.currentPage = val
                this.init1()
                console.log(`当前页: ${val}`);
            },
            submitSearchForm1() {
                this.init1()
            },
            selectedGoods(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        queryOutGoods(this.selectGoods).then(res => {
                            res = res.data
                            if (res.code == 200) {
                                this.selectGoodsVisable = false
                                this.newVisable = true
                                this.newForm.goodsName = res.data.goodsName
                                this.newForm.state = this.selectGoods.state
                                this.newForm.storeId = this.selectGoods.storeId
                                this.newForm.goodsId = this.selectGoods.goodsId
                                // this.selectGoods = {}
                            } else {
                                popup(res.msg, "error")
                            }
                        })
                    }
                })
            },
            closeSelectedGoods(formName) {
                this.$refs[formName].resetFields()
                this.selectGoods = {}
                this.goodsOutVisable = false


            },
            submitNewForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        saveOut_shelves(this.newForm).then(res => {
                            res = res.data
                            if (res.code == 200) {
                                popup("出库成功")
                                this.searchForm.state1 = '0'
                                this.goodsOutVisable = false
                                this.selectGoods = {}
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
            }
        },
        mounted() {
            this.initOptions()
        }
    }
</script>
<style>
    #detail_store_goods_in .noticeOut {
        height: 660px;
        background-color: #bfdcf6;
    }

    #detail_store_goods_in .noticeOut h1, .btns {
        width: 100%;

    }

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
