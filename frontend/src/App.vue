<script setup lang="ts">
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { check } from "@/services/accountService";
import { useAccountStore } from '@/stores/account';
import { watch } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const accountStore = useAccountStore();

const checkAccount = async () => {
    const res: any = await check();
    if (res.status === 200) {
        accountStore.setChecked(true);
        accountStore.setLoggedIn(res.data === true);
    } else {
        accountStore.setChecked(false);
    }
};

(async function onCreated() {
    await checkAccount();
})();

watch(() => route.path, () => {
    checkAccount();
});

</script>

<template>
    <template v-if="accountStore.checked">
        <Header />
        <main>
            <router-view></router-view>
        </main>
        <Footer />
    </template>
</template>
