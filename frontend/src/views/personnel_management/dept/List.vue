<template>
    <div id="dept_list">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item
                >首页</el-breadcrumb-item
            >
            <el-breadcrumb-item
                >人事管理
            </el-breadcrumb-item>
            <el-breadcrumb-item
                >部门管理</el-breadcrumb-item
            > </el-breadcrumb
        ><br />

        <el-row>
            <el-col :span="7">
                <el-input placeholder="部门名称" v-model="searchForm.name"/>
            </el-col>
            <el-col :span="7">
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
                <el-button type="success" @click="newVisable=true"
                    style="font-size: 18px"
                >
                    <i class="iconfont icon-r-add" style="font-size: 18px">
                    </i> 创建部门
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
                    prop="info"
                    label="描述">
            </el-table-column>
            <el-table-column
                    prop="state"
                    label="状态">
                <template v-slot="scope">
                    <el-tag v-if="scope.row.state=='0'" type="success" style="font-size: 14px;">
                        <i class="iconfont icon-r-yes" style="font-size: 14px;"> </i> 正常</el-tag>
                    <el-tag v-else type="danger" style="font-size: 14px;">
                        <i class="iconfont icon-r-no" style="font-size: 14px;"> </i> 停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    label="操作" width="240" fixed="right">
                <template v-slot="scope">
                    <el-button type="success" @click="edit(scope.row)" style="font-size: 18px;">
                        <i class="iconfont icon-r-edit" style="font-size: 18px;"> </i> 修改</el-button>
                    <el-button type="danger" @click="forbidden(scope.row.id)" style="font-size: 18px;">
                        <i class="iconfont icon-r-no" style="font-size: 18px;"> </i> 停用</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--修改描述/状态弹出框-->
        <el-dialog
                title="部门修改"
                :visible.sync="dialogVisible"
                width="50%">
            <el-form v-model="editForm"  label-width="100px" class="demo-ruleForm">
                <el-form-item label="名称：">
                    <el-input disabled v-model="editForm.name"></el-input>
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
                        <i class="iconfont icon-r-yes" style="font-size: 18px;"> </i> 提交</el-button>
                    <el-button @click="canCelEdit" style="font-size: 18px;">
                        关闭</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--创建弹出框-->
        <el-dialog
                title="创建部门"
                :visible.sync="newVisable"
                width="50%">
            <el-form :model="newForm" :rules="rules" ref="newForm"  label-width="100px" class="demo-ruleForm">
                <el-form-item label="名称：" prop="name">
                    <el-input v-model="newForm.name"></el-input>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input type="text" v-model="newForm.info"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitNewForm('newForm')">提交</el-button>
                    <el-button @click="resetNewForm('newForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
    import { popup} from "@/assets/js/common";
    import {deactivate, listByQo, save, update} from "@/api/personnel_management/department/deptApi";

    export default {
        data() {
            return {
                tableData: [],
                editForm: {},
                editRow:{},
                dialogVisible: false,
                newVisable:false,
                newForm:{},
                searchForm: {},
                options: [
                    {label: '正常', value: '0'},
                    {label: '停用', value: '-1'}
                ],
                rules: {
                    name:[
                        {required: true, message: '名称不能为空', trigger: 'blur'},
                    ]
                }
            }
        },
        methods: {
            init() {
                listByQo(this.searchForm).then(res => {
                    if (res.data.code == 200) {
                        this.tableData = res.data.data
                    }else {
                        popup(res.data.msg,"error")
                    }

                })
            },
            forbidden(id) {
                deactivate(id).then(res => {
                    res = res.data
                    if (res.code == 200) {
                        popup("操作成功")
                    } else {
                        popup(res.msg, "error")
                    }
                    this.init()
                })
            },

            /*弹出修改表单*/
            edit(row) {
                this.editForm= {...row}
                this.dialogVisible = true
            },
            /*条件搜索*/
            subSearchForm() {
               this.init()
            },
            /*对接后端修改接口*/
            submitEditForm() {
                update(this.editForm).then(res=>{
                    res=res.data
                    if (res.code==200){
                        popup("操作成功")
                        this.dialogVisible=false
                        this.init()
                    }else {
                        popup(res.msg, "error")
                    }
                })
            },
            /*修改重置按钮*/
            canCelEdit(){
              this.dialogVisible=false
                this.editForm={}
            },
            /*创建新角色*/
            submitNewForm(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(this.newForm)
                        save(this.newForm).then(res=>{
                            res=res.data
                            if (res.code==200){
                                popup("操作成功")
                                this.newVisable=false
                                this.newForm={}
                                this.init()
                            }else {
                                popup(res.msg, "error")
                            }
                        })
                    }
                });
            },
            resetNewForm(formname){
                this.$refs[formname].resetFields()
            },
        },
        created() {
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
