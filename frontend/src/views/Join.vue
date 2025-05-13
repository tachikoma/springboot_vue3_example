<script setup lang="ts">
import { reactive } from 'vue';
import { join} from '@/services/accountService';
import { useRouter } from 'vue-router';

const state = reactive({
    form: {
        name: '',
        loginId: '',
        loginPw: '',
    },
    });

const router = useRouter();

const submit = async () => {
    if (!state.form.name.trim()) {
        window.alert('이름을 입력해주세요.');
        document.getElementById('name')?.focus();
        return;
    } else if (!state.form.loginId.trim()) {
        window.alert('아이디를 입력해주세요.');
        document.getElementById('loginId')?.focus();
        return;
    } else if (!state.form.loginPw.trim()) {
        window.alert('비밀번호를 입력해주세요.');
        document.getElementById('loginPw')?.focus();
        return;
    }

    if (!state.form.loginId.includes('@')) {
        window.alert('이메일 형식이 아닙니다.');
        document.getElementById('loginId')?.focus();
        return;
    }
    if (state.form.loginPw.length < 8) {
        window.alert('비밀번호는 8자 이상이어야 합니다.');
        document.getElementById('loginPw')?.focus();
        return;
    }

    const res: any = await join(state.form);
    if (res.status === 200) {
        window.alert('회원가입이 완료되었습니다.');
        await router.push('/');
    } else if (res.status === 409) {
        console.log(res);
        window.alert('이미 사용중인 아이디입니다. 다른 아이디를 입력해주세요.');
        document.getElementById('loginId')?.focus(); 
    } else {
        window.alert('회원가입에 실패했습니다. 다시 시도해주세요.');
    }
};
</script>

<template>
    <div class="join">
        <div class="container">
            <form class="py-5 d-flex flex-column gap-3" @submit.prevent="submit">
                <h1 class="h5 mb-3">회원가입</h1>
                <div class="form-floating">
                    <label for="name">이름</label>
                    <input type="text" class="form-control" id="name" placeholder="이름" v-model="state.form.name"
                        required />
                </div>
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
                <button type="submit" class="w-100 h6 btn py-3 btn-primary">회원가입</button>
            </form>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.join {
    .container {
        max-width: 576px;
    }
}
</style>