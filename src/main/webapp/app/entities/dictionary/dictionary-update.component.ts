import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IDictionary, Dictionary } from '@/shared/model/dictionary.model';
import DictionaryService from './dictionary.service';

const validations: any = {
  dictionary: {
    baseLanguage: {},
    targerLanguage: {},
  },
};

@Component({
  validations,
})
export default class DictionaryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('dictionaryService') private dictionaryService: () => DictionaryService;
  public dictionary: IDictionary = new Dictionary();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dictionaryId) {
        vm.retrieveDictionary(to.params.dictionaryId);
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
    if (this.dictionary.id) {
      this.dictionaryService()
        .update(this.dictionary)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.dictionary.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.dictionaryService()
        .create(this.dictionary)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.dictionary.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDictionary(dictionaryId): void {
    this.dictionaryService()
      .find(dictionaryId)
      .then(res => {
        this.dictionary = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
