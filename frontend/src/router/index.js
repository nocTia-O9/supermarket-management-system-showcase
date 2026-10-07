import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../views/Login.vue'),
    },
    {
        path: '/index',
        name: 'index',
        redirect:'/home',
        component: () => import('../views/index.vue'),
        children: [
            {
                path: '/home',
                name: '/home',
                component: () => import('../views/Home.vue')
            },
            {
                path: '/personal/edit_pwd',
                name: '/personal/edit_pwd',
                component: () => import('../views/personal/Edit_pwd.vue')
            },

            {
                path: '/personal/information',
                name: '/personal/information',
                component: () => import('../views/personal/Information.vue')
            },
            {
                path: '/system/role/list',
                name: '/system/role/list',
                component: () => import('../views/system/role/List.vue')
            },
            {
                path: '/system/menu/list',
                name: '/system/menu/list',
                component: () => import('../views/system/menu/List.vue')
            },

            {
                path: '/inventory_management/store/list',
                name: '/inventory_management/store/list',
                component: () => import('../views/inventory_management/store/List.vue')
            },

            {
                path: '/inventory_management/detail_store_goods_in/list',
                name: '/inventory_management/detail_store_goods_in/list',
                component: () => import('../views/inventory_management/detail_store_goods_in/List.vue')
            },
            {
                path: '/inventory_management/detail_store_goods_out/list',
                name: '/inventory_management/detail_store_goods_out/list',
                component: () => import('../views/inventory_management/detail_store_goods_out/List.vue')
            },

            {
                path: '/inventory_management/supplier/list',
                name: '/inventory_management/supplier/list',
                component: () => import('../views/inventory_management/supplier/List.vue')
            },
            {
                path: '/inventory_management/detail_store_goods_in/notice/list',
                name: '/inventory_management/detail_store_goods_in/notice/list',
                component: () => import('../views/inventory_management/detail_store_goods_in/notice/List.vue')
            },
            {
                path: '/inventory_management/detail_store_goods_out/notice/list',
                name: '/inventory_management/detail_store_goods_out/notice/list',
                component: () => import('../views/inventory_management/detail_store_goods_out/notice/List.vue')
            },

            {
                path: '/inventory_management/store/storage_situation',
                name: '/inventory_management/store/storage_situation',
                component: () => import('../views/inventory_management/store/StorageSituation.vue')
            },
            {
                path: '/goods_management/goods_category/list',
                name: '/goods_management/goods_category/list',
                component: () => import('../views/goods_management/goods_category/List.vue')
            },
            {
                path: '/goods_management/goods/list',
                name: '/goods_management/goods/list',
                component: () => import('../views/goods_management/goods/List.vue')
            },
            {
                path: '/goods_management/goods_store/list',
                name: '/goods_management/goods_store/list',
                component: () => import('../views/goods_management/goods_store/List.vue')
            },

            {
                path: '/goods_management/statistic_sale/list',
                name: '/goods_management/statistic_sale/list',
                component: () => import('../views/goods_management/statistic_sale/List.vue')
            },
            {
                path: '/goods_management/point_products/list',
                name: '/goods_management/point_products/list',
                component: () => import('../views/goods_management/point_products/List.vue')
            },
            {
                path: '/personnel_management/dept/list',
                name: '/personnel_management/dept/list',
                component: () => import('../views/personnel_management/dept/List.vue')
            },
            {
                path: '/personnel_management/employee/list',
                name: '/personnel_management/employee/list',
                component: () => import('../views/personnel_management/employee/List.vue')
            },
            {
                path: '/member_management/member/list',
                name: '/member_management/member/list',
                component: () => import('../views/member_management/member/List.vue')
            },

            {
                path: '/sale_management/sale_cmd/list',
                name: '/sale_management/sale_cmd/list',
                component: () => import('../views/sale_management/sale_cmd/List.vue')
            },

            {
                path: '/sale_management/sale_records/list',
                name: '/sale_management/sale_records/list',
                component: () => import('../views/sale_management/sale_records/List.vue')
            },

            {
                path: '/sale_management/exchange_point_products_records/list',
                name: '/sale_management/exchange_point_products_records/list',
                component: () => import('../views/sale_management/exchange_point_products_records/List.vue')
            },

        ]
    },
]

const router = new VueRouter({
    mode: "history",
    routes
})

export default router
