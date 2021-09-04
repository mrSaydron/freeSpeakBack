<template>
  <v-container>
    <h1 class="text-center">Войти</h1>
    <div class="text-center" v-if="loginError">
      <p class="red--text">Неправильный логин или пароль. Пожалуйста, повторите попытку</p>
    </div>
    <v-row>
      <v-col>
        <v-form
          ref="form"
          v-model="valid"
          lazy-validation
        >
          <v-text-field
            v-model="login"
            label="Почта"
            :rules="emailRules"
            required
          ></v-text-field>
          <v-text-field
            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
            :type="passwordShow ? 'text' : 'password'"
            v-model="password"
            label="Пароль"
            required
            @click:append="passwordShow = !passwordShow"
          ></v-text-field>
          <v-btn
            class="mr-4"
            @click="signIn"
            :disabled="!valid"
          >Войти</v-btn>
          <v-btn
            class="mr-4"
            to="/reset-init"
          >Восстановить пароль</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue, Inject } from 'vue-property-decorator'

import AccountService from '@/services/accountService.js'

@Component({
  components: {}
})
export default class LoginForm extends Vue {
  @Inject() readonly accountService!: AccountService

  $refs!: {
    form: HTMLFormElement;
  }

  public login = ''
  public password = ''
  public passwordShow = false
  public loginError = false
  public valid = true

  public async signIn (): Promise<void> {
    if (this.$refs.form.validate()) {
      const result = await this.accountService.login(this.login, this.password, true)
      if (result) {
        this.$router.push('/')
      } else {
        this.loginError = true
      }
    }
  }

  public emailRules = [
    (v: any) => !!v || 'Обязательно для заполнения',
    (v: any) => /.+@.+\..+/.test(v) || 'Не верная почта',
    (v: any) => (v && v.length <= 50) || 'Не может превышать 50 символов'
  ]
}
</script>
