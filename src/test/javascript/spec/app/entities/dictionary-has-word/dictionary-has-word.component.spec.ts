/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DictionaryHasWordComponent from '@/entities/dictionary-has-word/dictionary-has-word.vue';
import DictionaryHasWordClass from '@/entities/dictionary-has-word/dictionary-has-word.component';
import DictionaryHasWordService from '@/entities/dictionary-has-word/dictionary-has-word.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('DictionaryHasWord Management Component', () => {
    let wrapper: Wrapper<DictionaryHasWordClass>;
    let comp: DictionaryHasWordClass;
    let dictionaryHasWordServiceStub: SinonStubbedInstance<DictionaryHasWordService>;

    beforeEach(() => {
      dictionaryHasWordServiceStub = sinon.createStubInstance<DictionaryHasWordService>(DictionaryHasWordService);
      dictionaryHasWordServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DictionaryHasWordClass>(DictionaryHasWordComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          dictionaryHasWordService: () => dictionaryHasWordServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dictionaryHasWordServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDictionaryHasWords();
      await comp.$nextTick();

      // THEN
      expect(dictionaryHasWordServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dictionaryHasWords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      dictionaryHasWordServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDictionaryHasWord();
      await comp.$nextTick();

      // THEN
      expect(dictionaryHasWordServiceStub.delete.called).toBeTruthy();
      expect(dictionaryHasWordServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
