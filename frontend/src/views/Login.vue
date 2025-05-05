<script setup>
import { reactive } from 'vue';
import { login } from '@/services/accountService';
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/account';

const state = reactive({
    form: {
        loginId: '',
        loginPw: '',
    },
});

const router = useRouter();

const accountStore = useAccountStore();

const submit = async () => {
    const res = await login(state.form);

    switch (res.status) {
        case 200:
            accountStore.setAccessToken(res.data);
            alert('로그인에 성공했습니다.');
            await router.push('/');
            break;
        case 404:
            alert('입력하신 정보가 일치하는 사용자가 없습니다.');
            break;
        default:
            alert('로그인에 실패했습니다. 다시 시도해주세요.');
            break;
    }
};

</script>

<template>
    <div class="login">
        <div class="container">
            <form class="py-5 d-flex flex-column gap-3" @submit.prevent="submit">
                <h1 class="h5 mb-3">로그인</h1>
                <div class="form-floating">
                    <label for="loginId">아이디</label>
                    <input type="email" class="form-control" id="loginId" placeholder="아이디" v-model="state.form.loginId"
                        required />
                </div>
                <div class="form-floating">
                    <label for="loginPw">비밀번호</label>
                    <input type="password" class="form-control" id="loginPw" placeholder="비밀번호"
                        v-model="state.form.loginPw" required />
                </div>
                <button type="submit" class="w-100 h6 btn py-3 btn-primary">로그인</button>
            </form>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.login {
    .container {
        max-width: 576px;
    }
}
</style>