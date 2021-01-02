import { Component, Vue, Inject } from 'vue-property-decorator';

import { IWord } from '@/shared/model/word.model';
import WordService from './word.service';

@Component
export default class WordDetails extends Vue {
  @Inject('wordService') private wordService: () => WordService;
  public word: IWord = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.wordId) {
        vm.retrieveWord(to.params.wordId);
      }
    });
  }

  public retrieveWord(wordId) {
    this.wordService()
      .find(wordId)
      .then(res => {
        this.word = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
