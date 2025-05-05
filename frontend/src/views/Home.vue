<script setup lang="ts">
import { getItems } from '@/services/itemService';
import { reactive } from 'vue';
import Card from '@/components/Card.vue';

const state = reactive({
  items: [],
});

(async function onCreated() {
  const res = await getItems();
  console.log(res);
  if (res.status === 200) {
    console.log('상품 목록:', res.data);
    state.items = res.data;
  } else {
    console.error('상품 목록을 가져오는 데 실패했습니다.', res);
  }
})();
</script>

<template>
  <div class="home">
    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3 g-3">
          <div class="col" v-for="item in state.items">
            <Card :item="item" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
