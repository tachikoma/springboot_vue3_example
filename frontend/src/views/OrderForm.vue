<script setup lang="ts">
import { computed, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { createOrder } from '@/services/orderService';
import { getItems } from '@/services/cartService';

const state = reactive({
    items: [],
    form: {
        name: '',
        address: '',
        payment: 'card',
        cardNumber: '',
    },
});

const router = useRouter();

const computedTotalPrice = computed(() => {
    return state.items.reduce((acc, item) => {
        return acc + (item.price - (item.price * item.discountPer / 100));
    }, 0);
});

// 주문하기
const submit = async () => {
    
    if (state.form.payment !== 'card') {
        state.form.cardNumber = '';
    }

    state.form.itemIds = state.items.map(item => item.id);
    const res = await createOrder(state.form);
    if (res.status === 200) {
        const messages =['주문이 완료되었습니다.'];
        if (state.form.payment === 'bank') {
            const price = computedTotalPrice.value.toLocaleString();
            messages.push(`한국은행 123-456789-777 계좌로 ${price}원을 입금해주시기 바랍니다.`);
        }
        alert(messages.join('\n'));
        await router.push('/');
    } else {
        alert('주문하기 실패');
    }
};

(async function onCreated() {
    const res = await getItems();
    if (res.status === 200) {
        state.items = res.data;
    } else {
        alert('장바구니 불러오기 실패');
    }
})();

</script>

<template>
    <form class="order-form" @submit.prevent="submit">
        <div class="container">
            <div class="py-5 text-center">
                <div class="h4"><b>주문하기</b></div>
                <p class="h6 font-lg mt-3">
                    주문 내역을 확인하시고 주문하기 버튼을 클릭해주세요.
                </p>
            </div>
            <div class="row g-5">
                <div class="col-md-5 col-lg-4 order-md-last">
                    <div class="mb-3">
                        <span class="h5 mb-3 align-middle me-2"><b>주문 목록</b></span>
                        <span class="badge bg-primary rounded-pill align-middle">{{ state.items.length }}</span>
                    </div>
                    <ul class="items list-group mb-3">
                        <li v-for="item in state.items" class="p-3 list-group-item d-flex justify-content-between">
                            <div>
                                <h6 class="my-0">{{ item.name }}</h6>
                            </div>
                            <span class="text-muted">
                                {{ (item.price - (item.price * item.discountPer / 100)).toLocaleString() }}원</span>
                        </li>
                    </ul>
                    <div class="border p-4 bg-light h5 rounded text-center total-price">
                        <span>총 주문금액</span>
                        <b>{{ computedTotalPrice.toLocaleString() }}원</b>
                    </div>
                    <button type="submit" class="w-100 btn btn-primary py-4 mt-4">결제하기</button>
                </div>
                <div class="col-md-7 col-lg-8">
                    <div class="h5 mb-3">
                        <b>주문자 정보</b>
                    </div>
                    <div class="row g-3">
                        <div class="col-12">
                            <label for="name" class="form-label">이름</label>
                            <input type="text" class="form-control p-3" id="name" v-model="state.form.name"
                                placeholder="이름을 입력해주세요" required />
                        </div>
                        <div class="col-12 pt-1">
                            <label for="address" class="form-label">주소</label>
                            <input type="text" class="form-control p-3" id="address" v-model="state.form.address"
                                placeholder="주소를 입력해주세요" required />
                        </div>
                        <div class="h5 mt-5 mb-3">
                            <b>결제 수단</b>
                        </div>
                        <div class="my-3">
                            <div class="form-check">
                                <input id="card" class="form-check-input" type="radio" name="payment" value="card"
                                    v-model="state.form.payment" checked />
                                <label for="card" class="form-check-label">신용카드</label>
                            </div>
                            <div class="form-check">
                                <input id="bank" class="form-check-input" type="radio" name="payment" value="bank"
                                    v-model="state.form.payment" />
                                <label for="bank" class="form-check-label">무통장입금</label>
                            </div>
                        </div>
                        <div class="pt-1" v-if="state.form.payment === 'card'">
                            <label for="cardNumber" class="form-label">카드번호</label>
                            <input type="text" class="form-control p-3" id="cardNumber" v-model="state.form.cardNumber"
                                placeholder="카드번호를 입력해주세요" required />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</template>