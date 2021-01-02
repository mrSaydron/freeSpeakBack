import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DictionaryService from '../dictionary/dictionary.service';
import { IDictionary } from '@/shared/model/dictionary.model';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IBook, Book } from '@/shared/model/book.model';
import BookService from './book.service';

const validations: any = {
  book: {
    title: {
      required,
    },
    author: {},
    source: {},
    text: {
      required,
    },
    publicBook: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class BookUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('bookService') private bookService: () => BookService;
  public book: IBook = new Book();

  @Inject('dictionaryService') private dictionaryService: () => DictionaryService;

  public dictionaries: IDictionary[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bookId) {
        vm.retrieveBook(to.params.bookId);
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
    if (this.book.id) {
      this.bookService()
        .update(this.book)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.book.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.bookService()
        .create(this.book)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('libFourApp.book.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveBook(bookId): void {
    this.bookService()
      .find(bookId)
      .then(res => {
        this.book = res;
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
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
