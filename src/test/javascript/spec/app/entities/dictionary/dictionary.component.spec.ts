/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DictionaryComponent from '@/entities/dictionary/dictionary.vue';
import DictionaryClass from '@/entities/dictionary/dictionary.component';
import DictionaryService from '@/entities/dictionary/dictionary.service';

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
  describe('Dictionary Management Component', () => {
    let wrapper: Wrapper<DictionaryClass>;
    let comp: DictionaryClass;
    let dictionaryServiceStub: SinonStubbedInstance<DictionaryService>;

    beforeEach(() => {
      dictionaryServiceStub = sinon.createStubInstance<DictionaryService>(DictionaryService);
      dictionaryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DictionaryClass>(DictionaryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          dictionaryService: () => dictionaryServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dictionaryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDictionarys();
      await comp.$nextTick();

      // THEN
      expect(dictionaryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dictionaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      dictionaryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDictionary();
      await comp.$nextTick();

      // THEN
      expect(dictionaryServiceStub.delete.called).toBeTruthy();
      expect(dictionaryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
