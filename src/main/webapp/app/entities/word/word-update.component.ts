import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IWord, Word } from '@/shared/model/word.model';
import WordService from './word.service';

const validations: any = {
  word: {
    word: {
      required,
    },
    translate: {},
    partOfSpeech: {},
  },
};

@Component({
  validations,
})
export default class WordUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('wordService') private wordService: () => WordService;
  public word: IWord = new Word();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.wordId) {
        vm.retrieveWord(to.params.wordId);
      }
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
    if (this.word.id) {
      this.wordService()
        .update(this.word)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.word.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.wordService()
        .create(this.word)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.word.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveWord(wordId): void {
    this.wordService()
      .find(wordId)
      .then(res => {
        this.word = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
