import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDictionary } from '@/shared/model/dictionary.model';
import DictionaryService from './dictionary.service';

@Component
export default class DictionaryDetails extends Vue {
  @Inject('dictionaryService') private dictionaryService: () => DictionaryService;
  public dictionary: IDictionary = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dictionaryId) {
        vm.retrieveDictionary(to.params.dictionaryId);
      }
    });
  }

  public retrieveDictionary(dictionaryId) {
    this.dictionaryService()
      .find(dictionaryId)
      .then(res => {
        this.dictionary = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
