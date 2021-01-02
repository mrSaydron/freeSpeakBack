import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DictionaryService from '../dictionary/dictionary.service';
import { IDictionary } from '@/shared/model/dictionary.model';

import WordService from '../word/word.service';
import { IWord } from '@/shared/model/word.model';

import AlertService from '@/shared/alert/alert.service';
import { IDictionaryHasWord, DictionaryHasWord } from '@/shared/model/dictionary-has-word.model';
import DictionaryHasWordService from './dictionary-has-word.service';

const validations: any = {
  dictionaryHasWord: {
    count: {},
  },
};

@Component({
  validations,
})
export default class DictionaryHasWordUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('dictionaryHasWordService') private dictionaryHasWordService: () => DictionaryHasWordService;
  public dictionaryHasWord: IDictionaryHasWord = new DictionaryHasWord();

  @Inject('dictionaryService') private dictionaryService: () => DictionaryService;

  public dictionaries: IDictionary[] = [];

  @Inject('wordService') private wordService: () => WordService;

  public words: IWord[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dictionaryHasWordId) {
        vm.retrieveDictionaryHasWord(to.params.dictionaryHasWordId);
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
  }

  public save(): void {
    this.isSaving = true;
    if (this.dictionaryHasWord.id) {
      this.dictionaryHasWordService()
        .update(this.dictionaryHasWord)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.dictionaryHasWord.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.dictionaryHasWordService()
        .create(this.dictionaryHasWord)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.dictionaryHasWord.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDictionaryHasWord(dictionaryHasWordId): void {
    this.dictionaryHasWordService()
      .find(dictionaryHasWordId)
      .then(res => {
        this.dictionaryHasWord = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.dictionaryService()
      .retrieve()
      .then(res => {
        this.dictionaries = res.data;
      });
    this.wordService()
      .retrieve()
      .then(res => {
        this.words = res.data;
      });
  }
}
