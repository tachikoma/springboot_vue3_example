<script setup lang="ts">
import { reactive } from 'vue';

interface Order {
    id: number;
    name: string;
    payment: string;
    amount: number;
    created: string;
}

import { getOrders } from '@/services/orderService';

const state = reactive({
    args: {
        page: 0,
        size: 5,
    },
    page: {
        index: 0,
        totalPages: 0,
        totalElements: 0,
    },
    orders: [] as Order[],
});

const getListNumber = (index: number) => {
    return state.page.totalElements - (state.page.index * state.args.size) - index;
};

// 주문 목록 불러오기
const load = async (pageIndex: number | undefined) => {

    if (pageIndex != undefined) {
        state.args.page = pageIndex;
    }

    const res = await getOrders(state.args);
    if (res.status === 200) {
        state.orders = res.data.content;
    
        state.page.index = res.data.number;
        state.page.totalPages = res.data.totalPages;
        state.page.totalElements = res.data.totalElements;
        
    } else {
        alert('주문 목록 불러오기 실패');
    }
   
};

(async function onCreated() {
    await load(undefined); 
})();

</script>

<template>
    <div class="orders">
        <div class="container">
            <table class="table table-bordered my-4">
                <thead>
                    <tr>
                        <th class="text-center">번호</th>
                <th>주문자</th>
                <th>결제 수단</th>
                <th>결제 금액</th>
                <th>결제 일시</th>
                <th>자세히 보기</th>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="(order, idx) in state.orders">
                        <td class="text-center">{{ getListNumber(idx) }}</td>
                        <td>{{ order.name }}</td>
                        <td>{{ order.payment === 'card' ? '카드' : '무통장입금' }}</td>
                        <td>{{ order.amount.toLocaleString() }}원</td>
                        <td>{{ order.created.toLocaleString() }}</td>
                        <td><router-link :to="`/orders/${order.id}`">자세히 보기</router-link>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="pagination d-flex justify-content-center pt-2">
                <button class="btn btn-outline-primary" @click="load(0)" :disabled="state.page.index === 0">처음</button>
                <div class="btn-group" role="group">
                    <button class="btn py-2 px-3" :class="[state.page.index === idx ? 'btn-primary' : 'btn-outline-secondary']" v-for="(i, idx) in state.page.totalPages" @click="load(idx)"> 
                        {{ i }}
                    </button>
                    <span class="mx-2">페이지 {{ state.page.index + 1 }} / {{ state.page.totalPages }}</span>
                </div>
                <button class="btn btn-outline-primary" @click="load(state.page.index - 1)" :disabled="state.page.index === 0">이전</button>
                <button class="btn btn-outline-primary" @click="load(state.page.index + 1)" :disabled="state.page.index === state.page.totalPages - 1">다음</button>
                <button class="btn btn-outline-primary" @click="load(state.page.totalPages - 1)" :disabled="state.page.index === state.page.totalPages - 1">마지막</button>
            </div>
        </div>
    
    </div>
</template>

