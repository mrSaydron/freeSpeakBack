/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DictionaryDetailComponent from '@/entities/dictionary/dictionary-details.vue';
import DictionaryClass from '@/entities/dictionary/dictionary-details.component';
import DictionaryService from '@/entities/dictionary/dictionary.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Dictionary Management Detail Component', () => {
    let wrapper: Wrapper<DictionaryClass>;
    let comp: DictionaryClass;
    let dictionaryServiceStub: SinonStubbedInstance<DictionaryService>;

    beforeEach(() => {
      dictionaryServiceStub = sinon.createStubInstance<DictionaryService>(DictionaryService);

      wrapper = shallowMount<DictionaryClass>(DictionaryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { dictionaryService: () => dictionaryServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDictionary = { id: 123 };
        dictionaryServiceStub.find.resolves(foundDictionary);

        // WHEN
        comp.retrieveDictionary(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dictionary).toBe(foundDictionary);
      });
    });
  });
});
