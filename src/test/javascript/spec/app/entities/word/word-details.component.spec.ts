/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import WordDetailComponent from '@/entities/word/word-details.vue';
import WordClass from '@/entities/word/word-details.component';
import WordService from '@/entities/word/word.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Word Management Detail Component', () => {
    let wrapper: Wrapper<WordClass>;
    let comp: WordClass;
    let wordServiceStub: SinonStubbedInstance<WordService>;

    beforeEach(() => {
      wordServiceStub = sinon.createStubInstance<WordService>(WordService);

      wrapper = shallowMount<WordClass>(WordDetailComponent, { store, i18n, localVue, provide: { wordService: () => wordServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundWord = { id: 123 };
        wordServiceStub.find.resolves(foundWord);

        // WHEN
        comp.retrieveWord(123);
        await comp.$nextTick();

        // THEN
        expect(comp.word).toBe(foundWord);
      });
    });
  });
});
