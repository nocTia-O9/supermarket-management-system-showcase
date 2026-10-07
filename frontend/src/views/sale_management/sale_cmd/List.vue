<template>
    <div id="sale_cmd">
        <div class="head">
            <i style="font-size: 42px" class="iconfont icon-r-home"> </i>
            销售主页
        </div>

        <div class="cmd">
            <el-col :span="12">
                <button @click="saleBtn">
                    <i style="font-size: 32px" class="iconfont icon-r-yes"> </i>
                    购物结账
                </button>
            </el-col>
            <el-col :span="12">
                <button @click="exchangePointProductBtn">
                    <i style="font-size: 32px" class="iconfont icon-r-refresh">
                    </i>
                    积分兑换
                </button>
            </el-col>
            <el-col :span="12">
                <button @click="saleRecordsBtn">
                    <i style="font-size: 32px" class="iconfont icon-r-paper">
                    </i>
                    销售记录
                </button>
            </el-col>
            <el-col :span="12">
                <button @click="exchangePointProductsRecordsBtn">
                    <i style="font-size: 32px" class="iconfont icon-r-find">
                    </i>
                    兑换记录
                </button>
            </el-col>
        </div>
        <!--销售-->
        <el-dialog title="销售商品" :visible.sync="newVisable" width="80%">
            <el-form
                :model="newForm"
                :rules="rules"
                ref="newForm"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-row>
                    <el-col :span="24">
                        <el-form-item style="width: 60%" label="销售编号：">
                            <el-input
                                disabled
                                v-model="newForm.cn"
                                placeholder="如：销售编号"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item
                            v-if="!newForm.sellTime"
                            label="支付方式："
                            prop="sellway"
                        >
                            <el-radio
                                type="radio"
                                v-model="newForm.sellway"
                                label="0"
                                >支付宝</el-radio
                            >
                            <el-radio
                                type="radio"
                                v-model="newForm.sellway"
                                label="1"
                                >微信</el-radio
                            >
                            <el-radio
                                type="radio"
                                v-model="newForm.sellway"
                                label="2"
                                >现金</el-radio
                            >
                            <el-radio
                                type="radio"
                                v-model="newForm.sellway"
                                label="3"
                                >银行卡</el-radio
                            >
                        </el-form-item>
                        <el-form-item
                            v-else
                            style="width: 80%"
                            label="支付方式："
                            prop="sellway"
                        >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="newForm.sellway"
                                label="0"
                                >支付宝</el-radio
                            >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="newForm.sellway"
                                label="1"
                                >微信</el-radio
                            >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="newForm.sellway"
                                label="2"
                                >现金</el-radio
                            >
                            <el-radio
                                disabled
                                type="radio"
                                v-model="newForm.sellway"
                                label="3"
                                >银行卡</el-radio
                            >
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item
                            v-if="!newForm.sellTime"
                            style="width: 60%"
                            label="顾客类型："
                            prop="type"
                        >
                            <el-radio
                                @change="
                                    () => {
                                        if (this.newForm.type == '1') {
                                            this.newForm.sellTotalmoney = (
                                                this.totalMoney * 0.9
                                            ).toFixed(2);
                                        } else {
                                            this.newForm.sellTotalmoney =
                                                this.totalMoney.toFixed(2);
                                        }
                                    }
                                "
                                type="radio"
                                v-model="newForm.type"
                                label="1"
                                >会员
                            </el-radio>
                            <el-radio
                                @change="
                                    () => {
                                        if (this.newForm.type == '1') {
                                            this.newForm.sellTotalmoney = (
                                                this.totalMoney * 0.9
                                            ).toFixed(2);
                                        } else {
                                            this.newForm.sellTotalmoney =
                                                this.totalMoney.toFixed(2);
                                        }
                                    }
                                "
                                type="radio"
                                v-model="newForm.type"
                                label="0"
                                >非会员
                            </el-radio>
                        </el-form-item>
                        <el-form-item
                            v-else
                            style="width: 60%"
                            label="顾客类型："
                            prop="type"
                        >
                            <el-radio
                                disabled
                                @change="
                                    () => {
                                        if (this.newForm.type == '1') {
                                            this.newForm.sellTotalmoney = (
                                                this.totalMoney * 0.9
                                            ).toFixed(2);
                                        } else {
                                            this.newForm.sellTotalmoney =
                                                this.totalMoney.toFixed(2);
                                        }
                                    }
                                "
                                type="radio"
                                v-model="newForm.type"
                                label="1"
                                >会员
                            </el-radio>
                            <el-radio
                                disabled
                                @change="
                                    () => {
                                        if (this.newForm.type == '1') {
                                            this.newForm.sellTotalmoney = (
                                                this.totalMoney * 0.9
                                            ).toFixed(2);
                                        } else {
                                            this.newForm.sellTotalmoney =
                                                this.totalMoney.toFixed(2);
                                        }
                                    }
                                "
                                type="radio"
                                v-model="newForm.type"
                                label="0"
                                >非会员
                            </el-radio>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" v-if="newForm.type == '1'">
                        <el-form-item
                            style="width: 60%"
                            label="会员账号："
                            prop="memberPhone"
                        >
                            <el-input
                                @change="
                                    queryMemberByPhone(newForm.memberPhone)
                                "
                                v-model="newForm.memberPhone"
                                placeholder="如：电话号码"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col
                        :span="12"
                        v-if="newForm.type == '1' && newForm.sellTime"
                    >
                        <el-form-item
                            style="width: 60%"
                            label="会员账号："
                            prop="memberPhone"
                        >
                            <el-input
                                readonly
                                v-model="newForm.memberPhone"
                                placeholder="如：电话号码"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <hr />
                <el-row>
                    <el-col
                        :span="24"
                        style="text-align: left; margin-bottom: 10px"
                    >
                        <el-button type="success" @click="addGoodsBtn">
                            <i class="iconfont icon-r-add"> </i>
                            添加商品</el-button
                        >
                    </el-col>
                    <el-col :span="24">
                        <el-table
                            :data="newForm.detailSaleRecords"
                            style="width: 100%; border-top: 1px solid lightgrey"
                            size="medium"
                        >
                            <el-table-column prop="goodsId" label="商品编号">
                            </el-table-column>
                            <el-table-column prop="goodsName" label="商品名">
                            </el-table-column>
                            <el-table-column
                                prop="goodsNum"
                                width="400px"
                                label="数量"
                            >
                                <template
                                    v-if="!newForm.sellTime"
                                    v-slot="scope"
                                >
                                    <el-button
                                        @click="redueGoodsNum(scope.row)"
                                        type="success"
                                        >-</el-button
                                    >
                                    <el-input
                                        readonly
                                        style="width: 100px"
                                        type="number"
                                        min="1"
                                        v-model="scope.row.goodsNum"
                                    ></el-input>
                                    <el-button
                                        @click="addGoodsNum(scope.row)"
                                        type="success"
                                        >+</el-button
                                    >
                                </template>
                                <template v-else v-slot="scope">
                                    <el-button type="success">+</el-button>
                                    <el-input
                                        readonly
                                        style="width: 100px"
                                        type="number"
                                        min="1"
                                        v-model="scope.row.goodsNum"
                                    ></el-input>
                                    <el-button type="success">-</el-button>
                                </template>
                            </el-table-column>
                            <el-table-column prop="goodsPrice" label="商品单价">
                            </el-table-column>
                            <el-table-column
                                v-if="!newForm.sellTime"
                                label="操作"
                            >
                                <template v-slot="scope">
                                    <el-button
                                        @click="removedetailRecords(scope.row)"
                                        type="danger"
                                        >移除</el-button
                                    >
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-col>
                </el-row>
                <br />
                <el-row>
                    <el-col :span="24">
                        <el-form-item
                            v-if="!newForm.sellTime"
                            style="width: 100%"
                            label="备注："
                        >
                            <el-input
                                type="textarea"
                                v-model="newForm.info"
                                placeholder="如：订单1"
                            ></el-input>
                        </el-form-item>
                        <el-form-item v-else style="width: 60%" label="备注：">
                            <el-input
                                disabled
                                type="textarea"
                                v-model="newForm.info"
                                placeholder="如：订单1"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div v-if="newForm.detailSaleRecords.length > 0">
                            订单号：{{ newForm.cn }}&nbsp;&nbsp; 商品总数：{{
                                newForm.sellTotal ? newForm.sellTotal : 0
                            }}&nbsp;&nbsp; 总共：{{
                                totalMoney ? totalMoney : 0
                            }}元&nbsp;&nbsp; 消费：{{
                                newForm.sellTotalmoney
                                    ? parseFloat(
                                          newForm.sellTotalmoney
                                      ).toFixed(2)
                                    : 0
                            }}元&nbsp;&nbsp;
                            <span v-if="newForm.sellway"
                                >支付方式：{{
                                    newForm.sellway == "0"
                                        ? "支付宝"
                                        : newForm.sellway == "1"
                                        ? "微信"
                                        : newForm.sellway == "2"
                                        ? "现金"
                                        : "银行卡"
                                }}&nbsp;&nbsp;</span
                            >
                            <span v-if="newForm.type == '1'"
                                >会员享受9折&nbsp;&nbsp;优惠{{
                                    totalMoney
                                        ? parseFloat(
                                              totalMoney -
                                                  newForm.sellTotalmoney
                                          ).toFixed(2)
                                        : 0
                                }}元&nbsp;&nbsp;</span
                            >
                            <span v-if="newForm.sellTime"
                                >消费时间：{{ newForm.sellTime }}&nbsp;&nbsp;
                                操作者编号：{{ newForm.eid }}</span
                            >
                        </div>
                    </el-col>
                </el-row>
                <hr />
                <el-row>
                    <el-col :span="24">
                        <el-form-item>
                            <el-button
                                v-if="!newForm.sellTime"
                                type="success"
                                @click="submitNewForm('newForm')"
                                style="font-size: 22px"
                            >
                                <i
                                    style="font-size: 22px"
                                    class="iconfont icon-r-yes"
                                >
                                </i>
                                支付
                            </el-button>
                            <el-button
                                style="font-size: 22px"
                                type="info"
                                @click="closeNewForm('newForm')"
                            >
                                <i
                                    style="font-size: 22px"
                                    class="iconfont icon-r-no"
                                >
                                </i>
                                关闭</el-button
                            >
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>
        <!--添加商品表单-->
        <el-dialog title="添加商品" :visible.sync="addGoodsVisable" width="70%">
            <el-form
                :model="detailSaleRecords"
                :rules="rules"
                ref="detailSaleRecords"
                class="demo-ruleForm"
            >
                <el-form-item label="商品：" prop="goodsId">
                    <el-select
                        style="width: 100%"
                        v-model="detailSaleRecords.goodsId"
                        placeholder="请选择商品"
                        @change="selectedAddGoods"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="item in options_saleRecordsAddGoods"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数量：">
                    <el-input
                        type="number"
                        @change="
                            () => {
                                this.$forceUpdate();
                                if (
                                    this.goodsNum_max <
                                    this.detailSaleRecords.goodsNum
                                ) {
                                    this.detailSaleRecords.goodsNum =
                                        this.goodsNum_max;
                                    return;
                                }
                                if (this.detailSaleRecords.goodsNum < 1) {
                                    this.detailSaleRecords.goodsNum = 1;
                                    return;
                                }
                            }
                        "
                        min="1"
                        :max="goodsNum_max"
                        v-model="detailSaleRecords.goodsNum"
                        :placeholder="` ${
                            goodsNum_max ? '库存数量：' + goodsNum_max : ''
                        }`"
                    ></el-input>
                </el-form-item>
                <el-form-item label="单价：" prop="goodsId">
                    <el-input
                        disabled
                        v-model="detailSaleRecords.goodsPrice"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="success"
                        @click="addDetailSaleRecords"
                        style="font-size: 22px"
                    >
                        <i
                            class="iconfont icon-r-add"
                            style="font-size: 22px"
                        ></i>
                        添加</el-button
                    >
                    <el-button
                        type="info"
                        @click="
                            () => {
                                this.addGoodsVisable = false;
                                this.detailSaleRecords = {};
                                this.options_saleRecordsAddGoods = [];
                                this.goodsNum_max = '';
                            }
                        "
                        style="font-size: 22px"
                    >
                        <i
                            class="iconfont icon-r-no"
                            style="font-size: 22px"
                        ></i>
                        取消
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!--积分商品-->
        <el-dialog
            title="积分兑换"
            :visible.sync="pointProductsVisable"
            label-width="200"
            width="40%"
        >
            <el-form
                :model="pointProductsForm"
                :rules="rules"
                ref="pointProductsForm"
                class="demo-ruleForm"
            >
                <el-form-item v-if="confirmVisiable">
                    <img
                        width="100px"
                        :src="BaseApi + pointProductsForm.coverUrl"
                        alt="商品图片"
                    />
                </el-form-item>
                <el-form-item
                    v-if="!confirmVisiable"
                    label="会员账号："
                    prop="memberId"
                >
                    <el-select
                        @change="queryPointProductByMemberId"
                        v-model="pointProductsForm.memberId"
                        placeholder="请选择会员"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="item in options_members"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item
                    v-if="confirmVisiable"
                    label="会员账号："
                    prop="memberId"
                >
                    <el-select
                        disabled
                        @change="queryPointProductByMemberId"
                        v-model="pointProductsForm.memberId"
                        placeholder="请选择会员"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="item in options_members"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item
                    v-if="!confirmVisiable"
                    label="积分商品："
                    prop="goodsId"
                >
                    <el-select
                        @change="queryPointProductByGoodsId"
                        v-model="pointProductsForm.goodsId"
                        placeholder="请选择积分商品"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="item in options_pointProducts"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item
                    v-if="confirmVisiable"
                    label="积分商品："
                    prop="goodsId"
                >
                    <el-select
                        disabled
                        @change="queryPointProductByGoodsId"
                        v-model="pointProductsForm.goodsId"
                        placeholder="请选择积分商品"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="item in options_pointProducts"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-if="confirmVisiable" label="所需积分：">
                    <el-input
                        disabled
                        type="number"
                        v-model="pointProductsForm.integral"
                        placeholder="所需积分"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button
                        v-if="confirmVisiable"
                        type="primary"
                        @click="submitPointProductsForm('pointProductsForm')"
                        style="font-size: 22px"
                    >
                        <i class="iconfont icon-r-yes" style="font-size: 22px">
                        </i>
                        确认兑换
                    </el-button>
                    <el-button
                        v-if="confirmVisiable"
                        type="info"
                        @click="closePointProductsForm"
                        style="font-size: 22px"
                    >
                        <i class="iconfont icon-r-no" style="font-size: 22px">
                        </i>
                        关闭</el-button
                    >
                    <el-button
                        type="success"
                        v-if="!confirmVisiable"
                        @click="confirmPointProducts('pointProductsForm')"
                        style="font-size: 22px"
                    >
                        <i class="iconfont icon-r-yes" style="font-size: 22px">
                        </i>
                        兑换
                    </el-button>
                    <el-button
                        v-if="!confirmVisiable"
                        type="info"
                        @click="closeConfirmPointProducts"
                        style="font-size: 22px"
                    >
                        <i class="iconfont icon-r-yes" style="font-size: 22px">
                        </i>
                        关闭</el-button
                    >
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { popup } from "@/assets/js/common";
import {
    getCn,
    getOptionSaleRecordsGoods,
    saveSaleRecords,
} from "@/api/sale_management/saleRecordsApi";
import { queryGoodsById } from "@/api/goods_management/goods/goodsApi";
import { queryMemberByPhone } from "@/api/member_management/member/memberApi";
import {
    queryMemberByGoodsId,
    queryOptionsMember,
    queryOptionsMemberPhone,
    queryOptionsPointProducts,
    queryPointProductByGoodsId,
    queryPointProductBymemberId,
    saveExchangePointProductRecords,
} from "@/api/sale_management/exchange_point_products/exchangePointProductsApi";

export default {
    data() {
        return {
            BaseApi: this.$store.state.BaseApi,
            confirmVisiable: false,
            newVisable: false,
            addGoodsVisable: false,
            paymentCodeVisable: false,
            goodsNum_max: "",
            totalMoney: "",
            options_saleRecordsAddGoods: [],
            detailSaleRecords: {},
            newForm: {
                cn: "",
                sellway: "1",
                info: "",
                sellTotal: "",
                sellTotalmoney: "",
                type: "0",
                detailSaleRecords: [],
            },
            pointProductsForm: {
                memberId: "",
                goodsId: "",
                coverUrl: "",
                integral: "",
            },
            options_members: [],
            options_pointProducts: [],
            pointProductsVisable: false,
            rules: {
                sellway: [
                    {
                        required: true,
                        message: "支付方式未选择",
                        trigger: "blur",
                    },
                ],
                type: [
                    {
                        required: true,
                        message: "顾客类型未选择",
                        trigger: "blur",
                    },
                ],
                memberPhone: [
                    {
                        required: true,
                        message: "会员电话号码未填写",
                        trigger: "blur",
                    },
                ],
                goodsId: [
                    {
                        required: true,
                        message: "商品不能为空",
                        trigger: "blur",
                    },
                ],
                memberId: [
                    {
                        required: true,
                        message: "会员编号不能为空",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        confirmPointProducts(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    queryPointProductByGoodsId({
                        goodsId: this.pointProductsForm.goodsId,
                    }).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            if (!res.data) {
                                this.pointProductsForm = {
                                    memberId: this.pointProductsForm.memberId,
                                    goodsId: this.pointProductsForm.goodsId,
                                    coverUrl: "",
                                    integral: "",
                                };
                                return;
                            }
                            this.pointProductsForm.integral = res.data.integral;
                            this.pointProductsForm.coverUrl = res.data.coverUrl;
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                    this.confirmVisiable = true;
                }
            });
        },
        closeConfirmPointProducts() {
            this.confirmVisiable = false;
            this.pointProductsVisable = false;
            this.pointProductsForm = {
                memberId: "",
                goodsId: "",
                coverUrl: "",
                integral: "",
            };
        },
        queryPointProductByMemberId() {
            queryPointProductBymemberId({
                memberId: this.pointProductsForm.memberId,
            }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_pointProducts = res.data;
                    if (!this.options_pointProducts.length) {
                        popup("该会员积分数无法兑换任何商品", "warning");
                        this.pointProductsForm.goodsId = "";
                        return;
                    }
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        queryPointProductByGoodsId() {
            queryMemberByGoodsId({
                goodsId: this.pointProductsForm.goodsId,
            }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_members = res.data;
                    if (!this.options_members.length) {
                        popup("该商品没有会员符合条件进行兑换", "warning");
                        this.pointProductsForm.memberId = "";
                        return;
                    }
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        queryOptionsPointProducts() {
            queryOptionsPointProducts().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_pointProducts = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        queryOptionsMember() {
            queryOptionsMember().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_members = res.data;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        submitPointProductsForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    saveExchangePointProductRecords(
                        this.pointProductsForm
                    ).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            popup("兑换成功");
                            this.pointProductsForm = {};

                            this.pointProductsForm = {
                                memberId: "",
                                goodsId: "",
                                coverUrl: "",
                                integral: "",
                            };
                            this.pointProductsVisable = false;

                            this.confirmVisiable = !this.confirmVisiable;
                        } else {
                            popup(res.msg, "error");
                        }
                    });
                }
            });
        },
        closePointProductsForm() {
            this.confirmVisiable = false;
        },
        moreFuns() {
            popup("正在研发...", "warning");
        },
        queryMemberByPhone(phone) {
            queryMemberByPhone({ phone: phone }).then((res) => {
                res = res.data;
                if (res.code != 200) {
                    popup(res.msg, "error");
                    this.newForm.memberPhone = "";
                }
            });
        },
        saleRecordsBtn() {
            this.$router.push("/sale_management/sale_records/list");
        },
        exchangePointProductsRecordsBtn() {
            this.$router.push(
                "/sale_management/exchange_point_products_records/list"
            );
        },
        submitNewForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.newForm.detailSaleRecords.length <= 0) {
                        popup("还没选择出售的商品", "error");
                        return;
                    }
                    saveSaleRecords(this.newForm).then((res) => {
                        res = res.data;
                        if (res.code == 200) {
                            this.newForm = { ...res.data };
                            console.log(this.newForm);
                            popup("支付成功");
                            this.paymentCodeVisable = false;
                        } else {
                            popup("系统正忙,支付失败", "error");
                        }
                    });
                }
            });
        },
        closeNewForm(formName) {
            this.$refs[formName].resetFields();
            (this.newForm = {
                cn: "",
                sellway: "1",
                info: "",
                sellTotal: "",
                sellTotalmoney: "",
                type: "0",
                detailSaleRecords: [],
            }),
                (this.newVisable = false);
        },
        removedetailRecords(row) {
            this.$confirm("确定删除这条记录？", "警示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.newForm.sellTotal =
                        parseInt(this.newForm.sellTotal) - row.goodsNum;
                    this.totalMoney = (
                        parseFloat(this.totalMoney) -
                        parseFloat(
                            parseInt(row.goodsNum) * parseFloat(row.goodsPrice)
                        )
                    ).toFixed(2);
                    if (this.newForm.type == "1") {
                        this.newForm.sellTotalmoney =
                            parseFloat(this.totalMoney) * (0.9).toFixed(2);
                    } else {
                        this.newForm.sellTotalmoney = parseFloat(
                            this.totalMoney
                        ).toFixed(2);
                    }
                    for (var i in this.newForm.detailSaleRecords) {
                        if (
                            this.newForm.detailSaleRecords[i].goodsId ==
                            row.goodsId
                        ) {
                            this.newForm.detailSaleRecords.splice(i, 1);
                            return;
                        }
                    }
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消操作",
                    });
                });
        },
        addGoodsNum(row) {
            queryGoodsById({ id: row.goodsId }).then((res) => {
                res = res.data;
                if (res.code == 200) {
                    if (row.goodsNum >= res.data.residueNum) {
                        popup(
                            "货架商品数量没这么多，请联系仓库管理者",
                            "warning"
                        );
                        return;
                    } else {
                        row.goodsNum = parseInt(row.goodsNum) + 1;
                        this.newForm.sellTotal =
                            parseInt(this.newForm.sellTotal) + 1;

                        this.totalMoney = (
                            parseFloat(this.totalMoney) +
                            parseFloat(row.goodsPrice)
                        ).toFixed(2);
                        if (this.newForm.type == "1") {
                            this.newForm.sellTotalmoney =
                                parseFloat(this.totalMoney) * (0.9).toFixed(2);
                        } else {
                            this.newForm.sellTotalmoney = parseFloat(
                                this.totalMoney
                            ).toFixed(2);
                        }
                    }
                } else {
                    popup(res.msg, "error");
                    return;
                }
            });
        },
        redueGoodsNum(row) {
            if (parseInt(row.goodsNum) == 1) {
                popup("已是最小数量", "error");
                return;
            }
            row.goodsNum = parseInt(row.goodsNum) - 1;
            this.newForm.sellTotal = parseInt(this.newForm.sellTotal) - 1;
            this.totalMoney = (
                parseFloat(this.totalMoney) - parseFloat(row.goodsPrice)
            ).toFixed(2);
            if (this.newForm.type == "1") {
                this.newForm.sellTotalmoney =
                    parseFloat(this.totalMoney) * (0.9).toFixed(2);
            } else {
                this.newForm.sellTotalmoney = parseFloat(
                    this.totalMoney
                ).toFixed(2);
            }
        },
        initOptionSaleRecordsAddGoodsAll() {
            getOptionSaleRecordsGoods().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.options_saleRecordsAddGoods = res.data;
                    if (this.options_saleRecordsAddGoods.length > 0) {
                        this.addGoodsVisable = true;
                    } else {
                        popup("目前没有可售卖的商品", "warning");
                    }
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        addGoodsBtn() {
            this.detailSaleRecords = {};
            this.goodsNum_max = "";
            this.initOptionSaleRecordsAddGoodsAll();
        },
        addDetailSaleRecords() {
            if (!this.detailSaleRecords.goodsNum) {
                popup("数量不能为空", "warning");
                return;
            }
            this.newForm.sellTotal =
                Number.parseInt(
                    this.newForm.sellTotal ? this.newForm.sellTotal : 0
                ) + Number.parseInt(this.detailSaleRecords.goodsNum);
            this.totalMoney =
                (this.totalMoney ? parseFloat(this.totalMoney) : 0) +
                parseInt(this.detailSaleRecords.goodsNum) *
                    parseFloat(this.detailSaleRecords.goodsPrice).toFixed(2);
            if (this.totalMoney) {
                if (this.newForm.type == "1") {
                    this.newForm.sellTotalmoney = this.totalMoney * 0.9;
                } else {
                    this.newForm.sellTotalmoney = this.totalMoney;
                }
            }
            if (this.newForm.detailSaleRecords.length == 0) {
                this.newForm.detailSaleRecords.unshift(this.detailSaleRecords);
            } else {
                for (var item of this.newForm.detailSaleRecords) {
                    if (item.goodsId == this.detailSaleRecords.goodsId) {
                        item.goodsNum =
                            Number.parseInt(item.goodsNum) +
                            Number.parseInt(this.detailSaleRecords.goodsNum);
                        this.addGoodsVisable = false;
                        return;
                    }
                }
                this.newForm.detailSaleRecords.unshift(this.detailSaleRecords);
            }
            this.addGoodsVisable = false;
        },
        saleBtn() {
            getCn().then((res) => {
                res = res.data;
                if (res.code == 200) {
                    this.newForm.cn = res.data;
                    this.newVisable = true;
                } else {
                    popup(res.msg, "error");
                }
            });
        },
        selectedAddGoods() {
            queryGoodsById({ id: this.detailSaleRecords.goodsId }).then(
                (res) => {
                    res = res.data;
                    if (res.code == 200) {
                        this.detailSaleRecords.goodsPrice =
                            res.data.purchashPrice;
                        this.detailSaleRecords.goodsName = res.data.name;
                        this.goodsNum_max = res.data.residueNum;
                    }
                }
            );
        },
        paymentCode(sellway) {
            if (this.newForm.detailSaleRecords.length <= 0) {
                popup("还没选择出售的商品", "error");
                return;
            }
            if (sellway != "2") {
                this.paymentCodeVisable = true;
            }
        },
        exchangePointProductBtn() {
            this.queryOptionsMember();
            this.queryOptionsPointProducts();
            this.pointProductsVisable = true;
        },
    },
    mounted() {},
};
</script>


<style>
#sale_cmd {
    top: 0;
    left: 0;
    width: 100%;
    overflow-y: auto;
    height: 90vh;
    background: url("../../../assets/33.png") center top / cover no-repeat;
    margin: 0 auto;
}

#sale_cmd .head {
    height: 100px;
    font-size: 42px;
    font-weight: bolder;
    margin-top: 50px;
    color: black;
}

#sale_cmd .cmd {
    width: 80%;
    margin: 0 auto;
}

#sale_cmd .cmd button {
    width: 85%;
    height: 100px;
    margin: 2%;
    border-radius: 3px;
    font-size: 32px;
    cursor: pointer;
    font-weight: 600;
}

#sale_cmd .cmd button:hover {
    background-color: #6495ed;
}

#sale_cmd .paymentCode {
    height: 182px;
    width: 182px;
    margin: 0 auto;
    background-color: #efefef;
}

#sale_cmd .paymentCode img {
    height: 182px;
    width: 182px;
    text-align: center;
}
</style>
