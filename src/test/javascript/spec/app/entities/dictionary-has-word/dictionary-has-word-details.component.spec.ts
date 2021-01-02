/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DictionaryHasWordDetailComponent from '@/entities/dictionary-has-word/dictionary-has-word-details.vue';
import DictionaryHasWordClass from '@/entities/dictionary-has-word/dictionary-has-word-details.component';
import DictionaryHasWordService from '@/entities/dictionary-has-word/dictionary-has-word.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DictionaryHasWord Management Detail Component', () => {
    let wrapper: Wrapper<DictionaryHasWordClass>;
    let comp: DictionaryHasWordClass;
    let dictionaryHasWordServiceStub: SinonStubbedInstance<DictionaryHasWordService>;

    beforeEach(() => {
      dictionaryHasWordServiceStub = sinon.createStubInstance<DictionaryHasWordService>(DictionaryHasWordService);

      wrapper = shallowMount<DictionaryHasWordClass>(DictionaryHasWordDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { dictionaryHasWordService: () => dictionaryHasWordServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDictionaryHasWord = { id: 123 };
        dictionaryHasWordServiceStub.find.resolves(foundDictionaryHasWord);

        // WHEN
        comp.retrieveDictionaryHasWord(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dictionaryHasWord).toBe(foundDictionaryHasWord);
      });
    });
  });
});
