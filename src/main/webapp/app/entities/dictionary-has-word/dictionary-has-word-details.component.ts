import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDictionaryHasWord } from '@/shared/model/dictionary-has-word.model';
import DictionaryHasWordService from './dictionary-has-word.service';

@Component
export default class DictionaryHasWordDetails extends Vue {
  @Inject('dictionaryHasWordService') private dictionaryHasWordService: () => DictionaryHasWordService;
  public dictionaryHasWord: IDictionaryHasWord = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dictionaryHasWordId) {
        vm.retrieveDictionaryHasWord(to.params.dictionaryHasWordId);
      }
    });
  }

  public retrieveDictionaryHasWord(dictionaryHasWordId) {
    this.dictionaryHasWordService()
      .find(dictionaryHasWordId)
      .then(res => {
        this.dictionaryHasWord = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
