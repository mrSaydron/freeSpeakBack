import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IChat, Chat } from '@/shared/model/chat.model';
import ChatService from './chat.service';

const validations: any = {
  chat: {
    chatType: {
      required,
    },
    title: {},
  },
};

@Component({
  validations,
})
export default class ChatUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('chatService') private chatService: () => ChatService;
  public chat: IChat = new Chat();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.chatId) {
        vm.retrieveChat(to.params.chatId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.chat.users = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.chat.id) {
      this.chatService()
        .update(this.chat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.chat.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.chatService()
        .create(this.chat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.chat.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveChat(chatId): void {
    this.chatService()
      .find(chatId)
      .then(res => {
        this.chat = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
