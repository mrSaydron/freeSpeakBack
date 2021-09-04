<template>
  <v-container>
    <h1 class="text-center">Сменить пароль</h1>
    <v-row>
      <v-col>
        <v-form
          ref="form"
          v-model="valid"
          lazy-validation
        >
          <v-text-field
            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
            :type="passwordShow ? 'text' : 'password'"
            v-model="password"
            label="Пароль"
            required
            :rules="passwordRules"
            @click:append="passwordShow = !passwordShow"
          ></v-text-field>
          <v-btn
            class="mr-4"
            @click="resetFinish"
            :disabled="!valid"
          >Сменить</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue } from 'vue-property-decorator'
import AccountService from '../../services/accountService'
import { UserDto } from '@/model/userDto'
import { ErrorDto } from '@/model/errorDto'

@Component({
  components: {}
})
export default class ResetFinish extends Vue {
  @Inject() readonly accountService!: AccountService;

  @Prop(String) readonly key?: string

  $refs!: {
    form: HTMLFormElement;
  }

  public password = ''
  public passwordShow = false
  public valid = true

  public async resetFinish () {
    console.log('reset finish')
    if (this.$refs.form.validate() && this.key) {
      await this.accountService.resetFinish(this.key, this.password)
      await this.$router.push('/')
    }
  }

  public passwordRules = [
    (v: any) => !!v || 'Обязательно для заполнения',
    (v: any) => (v && v.length >= 4) || 'Не короче четырех символов',
    (v: any) => (v && v.length <= 100) || 'Не может превышать 100 символов'
  ]
}
</script>
