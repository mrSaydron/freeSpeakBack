<template>
  <v-container>
    <h1 class="text-center">Востановление пароля</h1>
    <v-row>
      <v-col>
        <v-form
          ref="form"
          v-model="valid"
          lazy-validation
        >
          <v-text-field
            v-model="email"
            label="Почта"
            :rules="emailRules"
            required
          ></v-text-field>
          <v-btn
            class="mr-4"
            @click="resetInit"
            :disabled="!valid"
          >Отправить сообщение</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import AccountService from '../../services/accountService'

@Component({
  components: {}
})
export default class ResetInit extends Vue {
  @Inject() readonly accountService!: AccountService;

  $refs!: {
    form: HTMLFormElement;
  }

  public email = ''
  public valid = true

  public resetInit () {
    if (this.$refs.form.validate()) {
      this.accountService.resetInit(this.email)
      this.$router.push('/')
    }
  }

  public emailRules = [
    (v: any) => !!v || 'Обязательно для заполнения',
    (v: any) => /.+@.+\..+/.test(v) || 'Не верная почта',
    (v: any) => (v && v.length <= 50) || 'Не может превышать 50 символов'
  ]
}
</script>
