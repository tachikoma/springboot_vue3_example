<script setup lang="ts">
import { useAccountStore } from '@/stores/account';
import { logout } from '@/services/accountService';
import { useRouter } from 'vue-router';

const accountStore = useAccountStore();

const router = useRouter();

// 로그아웃
const logoutAccount = async () => {
    const res: any = await logout();
    if (res.status === 200) {
        accountStore.setAccessToken("");
        accountStore.setLoggedIn(false);
        await router.push('/');
    }
};
</script>

<template>
    <header>
        <div class="navbar navbar-dark bg-dark text-white shadow-sm">
            <div class="container">
                <router-link to="/" class="navbar-brand">
                    <strong>Gallery</strong>
                </router-link>
                <div class="menus d-flex gap-3">
                    <template v-if="!accountStore.loggedIn">
                        <router-link to="/login">로그인</router-link>
                        <router-link to="/join">회원가입</router-link>
                    </template>
                    <template v-else>
                        <a @click="logoutAccount()">로그아웃</a>
                        <router-link to="/orders">주문 내역</router-link>
                        <router-link to="/cart">장바구니</router-link>
                    </template>
                </div>
            </div>
        </div>
    </header>
</template>

<style lang="scss">
header {
    .menus {
        a {
            cursor: pointer;
            color: #fff;
            text-decoration: none;

            &:hover {
                text-decoration: underline;
            }
        }
    }
}
</style>
