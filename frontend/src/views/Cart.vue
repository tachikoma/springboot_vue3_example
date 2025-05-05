<script setup lang="ts">
import { getItems, removeItem } from '@/services/cartService';
import { reactive } from 'vue';

const state = reactive({
    items: [],
});

// 장바구니에 담긴 상품 목록 불러오기
const load = async () => {
    const res = await getItems();
    if (res.status === 200) {
        state.items = res.data;
    } else {
        alert('장바구니 불러오기 실패');
    }
};

// 장바구니에서 상품 삭제
const remove = async (itemId: any) => {
    const res = await removeItem(itemId);
    if (res.status === 200) {
        alert('장바구니에서 상품을 삭제했습니다.');
        await load();
    } else {
        alert('장바구니에서 상품 삭제 실패');
    }
};

(async function onCreated() {
    await load();
})();
</script>

<template>
    <div class="cart">
        <div class="container">
            <template v-if="state.items.length">
                <ul class="items">
                    <li v-for=" item in state.items">
                    <img :src="item.imgPath" :alt="`상품 이미지(${item.name})`" />
                    <b class="name">{{ item.name }}</b>
                    <span class="price">{{ (item.price - item.price * item.discountPer / 100).toLocaleString()
                        }}원</span>
                    <span class="remove float-end" @click="remove(item.id)" title="삭제">&times;</span>
                    </li>
                </ul>
                <div class="act">
                    <router-link to="/order" class="btn btn-primary">주문하기</router-link>
                </div>
            </template>
            <div class="text-center py-5" v-else>
                장바구니에 상품이 없습니다
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.cart {
    .items {
        list-style: none;
        margin: 0;
        padding: 0;

        li {
            border: 1px solid #eee;
            margin-top: 25px;
            margin-bottom: 25px;
        }

        img {
            width: 150px;
            height: 150px;
        }

        .name {
            margin-left: 25px;
        }

        .price {
            margin-left: 25px;
        }

        .remove {
            cursor: pointer;
            font-size: 30px;
            padding: 5px 15px;
        }
    }
}

.act .btn {
    width: 300px;
    display: block;
    margin: 0 auto;
    padding: 30px 50px;
    font-size: 20px;
}
</style>
