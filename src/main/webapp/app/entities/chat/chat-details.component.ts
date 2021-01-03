import { Component, Vue, Inject } from 'vue-property-decorator';

import { IChat } from '@/shared/model/chat.model';
import ChatService from './chat.service';

@Component
export default class ChatDetails extends Vue {
  @Inject('chatService') private chatService: () => ChatService;
  public chat: IChat = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.chatId) {
        vm.retrieveChat(to.params.chatId);
      }
    });
  }

  public retrieveChat(chatId) {
    this.chatService()
      .find(chatId)
      .then(res => {
        this.chat = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
