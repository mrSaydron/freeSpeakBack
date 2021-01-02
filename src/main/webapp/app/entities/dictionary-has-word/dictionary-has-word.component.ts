import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDictionaryHasWord } from '@/shared/model/dictionary-has-word.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DictionaryHasWordService from './dictionary-has-word.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DictionaryHasWord extends mixins(AlertMixin) {
  @Inject('dictionaryHasWordService') private dictionaryHasWordService: () => DictionaryHasWordService;
  private removeId: number = null;

  public dictionaryHasWords: IDictionaryHasWord[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDictionaryHasWords();
  }

  public clear(): void {
    this.retrieveAllDictionaryHasWords();
  }

  public retrieveAllDictionaryHasWords(): void {
    this.isFetching = true;

    this.dictionaryHasWordService()
      .retrieve()
      .then(
        res => {
          this.dictionaryHasWords = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDictionaryHasWord): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDictionaryHasWord(): void {
    this.dictionaryHasWordService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('libFourApp.dictionaryHasWord.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllDictionaryHasWords();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
