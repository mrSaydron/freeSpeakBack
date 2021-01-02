import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDictionary } from '@/shared/model/dictionary.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DictionaryService from './dictionary.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Dictionary extends mixins(AlertMixin) {
  @Inject('dictionaryService') private dictionaryService: () => DictionaryService;
  private removeId: number = null;

  public dictionaries: IDictionary[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDictionarys();
  }

  public clear(): void {
    this.retrieveAllDictionarys();
  }

  public retrieveAllDictionarys(): void {
    this.isFetching = true;

    this.dictionaryService()
      .retrieve()
      .then(
        res => {
          this.dictionaries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDictionary): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDictionary(): void {
    this.dictionaryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('libFourApp.dictionary.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllDictionarys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
