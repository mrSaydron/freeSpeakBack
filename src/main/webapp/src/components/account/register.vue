<template>
  <v-container>
    <h1 class="text-center">Создайте аккаунт</h1>
    <div class="text-center" v-if="loginError">
      <p class="red--text">{{ loginError }}</p>
    </div>
    <v-row>
      <v-col>
        <v-form
          ref="form"
          v-model="valid"
          lazy-validation
        >
          <v-text-field
            v-model="user.email"
            label="Почта"
            :rules="emailRules"
            required
          ></v-text-field>
          <v-text-field
            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
            :type="passwordShow ? 'text' : 'password'"
            v-model="user.password"
            label="Пароль"
            required
            :rules="passwordRules"
            @click:append="passwordShow = !passwordShow"
          ></v-text-field>
          <v-btn
            class="mr-4"
            @click="signUp"
            :disabled="!valid"
          >Создать</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import AccountService from '../../services/accountService'
import { UserDto } from '@/model/userDto'
import { ErrorDto } from '@/model/errorDto'

@Component({
  components: {}
})
export default class Register extends Vue {
  @Inject() readonly accountService!: AccountService

  $refs!: {
    form: HTMLFormElement;
  }

  public user = new UserDto()
  public passwordShow = false
  public loginError: string | null = null
  public valid = true

  public emailRules = [
    (v: any) => !!v || 'Обязательно для заполнения',
    (v: any) => /.+@.+\..+/.test(v) || 'Не верная почта',
    (v: any) => (v && v.length <= 50) || 'Не может превышать 50 символов'
  ]

  public passwordRules = [
    (v: any) => !!v || 'Обязательно для заполнения',
    (v: any) => (v && v.length >= 4) || 'Не короче четырех символов',
    (v: any) => (v && v.length <= 100) || 'Не может превышать 100 символов'
  ]

  public async signUp (): Promise<void> {
    if (this.$refs.form.validate()) {
      try {
        this.user.login = this.user.email
        await this.accountService.register(this.user)
        await this.$router.push('/')
      } catch (err) {
        this.loginError = err.title
      }
    }
  }
}
</script>
