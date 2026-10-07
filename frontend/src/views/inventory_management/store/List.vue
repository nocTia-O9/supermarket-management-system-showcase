<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>库存管理 </el-breadcrumb-item>
            <el-breadcrumb-item>仓库管理</el-breadcrumb-item> </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="8" style="text-align: left;padding-right: 10px;">
                <el-input placeholder="仓库名称" v-model="searchForm.name"/>
                
            </el-col>
            <el-col :span="8">
                <el-select v-model="searchForm.state" placeholder="请选择状态" clearable>
                    <el-option label="正常" value="0"></el-option>
                    <el-option label="停用" value="-1"></el-option>
                </el-select>
            </el-col>
        </el-row>
        <br />
        <el-row>
            <el-col :span="24" style="text-align: left">
                <el-button
                    type="primary"
                    @click="subSearchForm"
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
                    创建仓库
                </el-button>
            </el-col>
        </el-row>
        <br />
        <el-table
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    type="index"
                    label="序号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名称"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="地址"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="info"
                    :show-overflow-tooltip="true"
                    label="描述">
            </el-table-column>
            <el-table-column
                    prop="state"
                    label="状态">
                <template v-slot="scope">
                    <el-tag v-if="scope.row.state=='0'" type="success">正常</el-tag>
                    <el-tag v-else type="danger">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column width="240" fixed="right"
                    label="操作">
                <template v-slot="scope">
                    <el-button type="success" style="font-size: 18px;" @click="editStore(scope.row)">
                        <i class="iconfont icon-r-edit" style="font-size: 18px;"> </i>
                         修改
                    </el-button>
                    <el-button  style="font-size: 18px;" type="danger" @click="forbidden(scope.row.id)">
                        <i class="iconfont icon-r-no" style="font-size: 18px;"> </i>
                         停用
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--修改仓库弹出框-->
        <el-dialog
                title="仓库信息修改"
                :visible.sync="dialogVisible"
                width="50%">
            <el-form v-model="editForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="名称：">
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
                <el-form-item label="地址：">
                    <el-input type="text" v-model="editForm.address"></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input type="text" v-model="editForm.info"></el-input>
                </el-form-item>
                <el-form-item label="状态：">
                    <el-select v-model="editForm.state"
                               clearable
                               @change="$forceUpdate()"
                               placeholder="请选择状态">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitEditForm" style="font-size: 18px;">
                        <i class="iconfont icon-r-yes" style="font-size: 18px;"> </i>
                         提交
                    </el-button>
                    <el-button  style="font-size: 18px;" @click="editCancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--创建仓库弹出框-->
        <el-dialog
                title="创建仓库"
                :visible.sync="newVisable"
                width="50%">
            <el-form :model="newForm" :rules="rules" ref="newForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="名称：" prop="name">
                    <el-input v-model="newForm.name"></el-input>
                </el-form-item>
                <el-form-item label="地址：" prop="address">
                    <el-input type="text" v-model="newForm.address"></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input type="text" v-model="newForm.info"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitNewForm('newForm')" style="font-size: 18px;">
                        <i class="iconfont icon-r-yes" style="font-size: 18px;"> </i> 提交</el-button>
                    <el-button @click="saveCancel" style="font-size: 18px;">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
    import {deactivate, save, storeList, update} from "@/api/inventory_management/store/storeApi";
    import {popup} from "@/assets/js/common";

    export default {
        data() {
            return {
                tableData: [],
                newStoreVisable: false,
                dialogVisible: false,
                editForm: {},
                searchForm:{},
                newVisable: false,
                newForm:{},
                options: [
                    {label: '正常', value: '0'},
                    {label: '停用', value: '-1'}
                ],
                rules: {
                    name:[
                        {required: true, message: '名称不能为空', trigger: 'blur'},
                    ],
                    address:[
                        {required: true, message: '地名不能为空', trigger: 'blur'},
                    ],
                }

            }
        },
        methods: {
            subSearchForm(){
                this.init()
            },
            init() {
                storeList(this.searchForm).then(res => {
                    if (res.data.code == 200) {
                        this.tableData = res.data.data
                    } else {
                        popup(res.data.msg, "error")
                    }
                })

            },
            editStore(row) {
                this.editForm = {...row}
                this.dialogVisible = true
            },
            forbidden(sid) {
                deactivate(sid).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        popup("操作成功")
                    } else {
                        popup(res.msg, "error")
                    }
                    this.init()
                })
            },
            submitEditForm() {
                update(this.editForm).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        popup("操作成功")
                        this.init()
                        this.dialogVisible=false
                    } else {
                        popup(res.msg, "error")
                    }

                })
            },
            editCancel() {
                this.dialogVisible = false
                this.editForm = {}
                popup("已取消操作", "info")
            },
            submitNewForm(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        save(this.newForm).then(res=>{
                            res=res.data
                            if (res.code==200){
                                popup("操作成功")
                                this.newForm={}
                                this.newVisable=false
                                this.init()
                            }else {
                                popup(res.msg, "error")
                            }
                        })
                    }
                });
            },
            saveCancel(){
                this.newVisable=false
                this.$refs["newForm"].resetFields()
            }
        },
        mounted() {
            this.init()
        }
    }
</script>
<style>
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
