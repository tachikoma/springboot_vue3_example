<script setup lang="ts">
import { reactive } from 'vue';
import { getOrders } from '@/services/orderService';

const state = reactive({
    orders: [],
});

// 주문 목록 불러오기
const load = async () => {
    const res = await getOrders();
    if (res.status === 200) {
        state.orders = res.data;
    } else {
        alert('주문 목록 불러오기 실패');
    }
};

(async function onCreated() {
    await load(); 
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
                        <td class="text-center">{{ state.orders.length - idx }}</td>
                        <td>{{ order.name }}</td>
                        <td>{{ order.payment === 'card' ? '카드' : '무통장입금' }}</td>
                        <td>{{ order.amount.toLocaleString() }}원</td>
                        <td>{{ order.created.toLocaleString() }}</td>
                        <td><router-link :to="`/orders/${order.id}`">자세히 보기</router-link>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

