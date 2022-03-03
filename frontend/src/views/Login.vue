<template>
    <v-card max-width="500" class="mt-10 mx-auto text-center">
      <v-img
        alt="Vuetify Logo"
        class="shrink mx-auto"
        contain
        src="../assets/main_logo.png"
        transition="scale-transition"
        width="150"
      />
      <div class="text-h4 font-weight-bold">운동 못하니?</div>
      <v-divider class="mt-5 mx-3"></v-divider>
      <ValidationObserver
        ref="observer"
        v-slot="{ invalid }"
      >
        <form @submit.prevent="submit"
         class="pa-10"
        >
          <ValidationProvider
            v-slot="{ errors }"
            name="아이디"
            rules="required"
          >
              <v-text-field
                v-model="id"
                :error-messages="errors"
                label="ID"
                required
                class="mx-10"
              ></v-text-field>
          </ValidationProvider>
          <ValidationProvider
            v-slot="{ errors }"
            name="비밀번호"
            rules="required"
          >
              <v-text-field
                v-model="password"
                :error-messages="errors"
                type="password"
                label="Password"
                class="mx-10"
                required
              ></v-text-field>
          </ValidationProvider>
          <v-btn
           type="submit"
           color="primary"
           :disabled="invalid"
           width="180"
          >
            로그인
          </v-btn>
          <div class="mt-3">아직 회원이 아니신가요?</div>
          <v-btn
            href="/signUp"
            color="grey"
            class="white--text mt-3"
            width="180"
          >
            <!-- <v-icon>mdi-home-import-outline</v-icon> -->
            <span>회원가입</span>
          </v-btn>
          <div class="mt-3">
              <a href="/find">아이디 / 비밀번호 찾기</a>
          </div>
        </form>
      </ValidationObserver>
    </v-card>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator'
import {required} from 'vee-validate/dist/rules'
import {extend, ValidationObserver, ValidationProvider} from 'vee-validate'
// import authToken from '../axios/auth'

@Component({
    components:{
        ValidationObserver,
        ValidationProvider
    }
})
export default class Login extends Vue {
    id = ""
    password = ""
    loading = false;
    message = ""

    submit(){
      const user = { userId : `${this.id}`, password : `${this.password}`}
        this.$store.dispatch('auth/login', user).then(
            () => {
              location.href='/home';
            },
            error => {
              this.loading = false;
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
            }
        )
    }
  
}
extend('required', {
  ...required,
  message: '{_field_}를 입력해주세요',
})

</script>