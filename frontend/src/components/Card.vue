<script setup>
import { addItem } from '@/services/cartService';
import { useRouter } from 'vue-router';
import { computed } from 'vue';
import { useAccountStore } from '@/stores/account';

const props = defineProps({
    item: {
        id: Number,
        imgPath: String,
        name: String,
        price: Number,
        discountPer: Number,
    }
});

const computedItemDiscountPrice = computed(() => {
    const newLocal = props.item.price - (props.item.price * props.item.discountPer / 100);
    console.log('할인가:', newLocal);
    // 3자리마다 쉼표 표기
    // toLocaleString() 메서드를 사용하여 숫자를 지역화된 문자열로 변환
    // 한국어 지역 설정을 사용하여 원화로 표시
    return newLocal.toLocaleString() + '원';
});

const router = useRouter();

const accountStore = useAccountStore();

// 장바구니에 상품 담기
const put = async () => {
    if(!accountStore.loggedIn) {
        if(confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
            await router.push('/login');
        }
        return;
    }

    const res = await addItem(props.item.id);

    if (res.status === 200 && window.confirm('장바구니에 상품을 담았습니다. \n장바구니로 이동하시겠습니까?')) {
        await router.push("/cart");
    } else {
        alert('장바구니 담기 실패');
    }
};
</script>

<template>
    <div class="card shadow-sm">
        <!-- 상품 이미지 -->
        <span class="img" :style="{ backgroundImage: `url(${props.item.imgPath})` }"
            :aria-label="`상품 이미지(${props.item.name})`">
        </span>
        <div class="card-body">
            <p class="card-text">
                <!-- 상품 이름 -->
                <span class="me-2">{{ props.item.name }}</span>
                <!-- 상품 할인율 -->
                <span class="discount badge bg-danger">{{ props.item.discountPer }}%</span>
            </p>
            <div class="d-flex justify-content-between align-items-center">
                <button class="btn btn-primary btn-sm" @click="put">장바구니 담기</button>
                <!-- 상품 정가(숫자 데이터에 3자리마다 쉼표 표기) -->
                <small class="price text-muted">{{ props.item.price.toLocaleString() }}원</small>
                <!-- 상품 할인가 -->
                <small class="real text-danger">{{ computedItemDiscountPrice }}</small>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.card {
    .img {
        display: inline-block;
        width: 100%;
        height: 250px;
        background-size: cover;
        background-position: center;
    }

    .card-body .price {
        text-decoration: line-through;
    }
}
</style>