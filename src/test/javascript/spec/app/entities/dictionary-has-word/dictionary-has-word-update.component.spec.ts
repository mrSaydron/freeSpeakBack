/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DictionaryHasWordUpdateComponent from '@/entities/dictionary-has-word/dictionary-has-word-update.vue';
import DictionaryHasWordClass from '@/entities/dictionary-has-word/dictionary-has-word-update.component';
import DictionaryHasWordService from '@/entities/dictionary-has-word/dictionary-has-word.service';

import DictionaryService from '@/entities/dictionary/dictionary.service';

import WordService from '@/entities/word/word.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('DictionaryHasWord Management Update Component', () => {
    let wrapper: Wrapper<DictionaryHasWordClass>;
    let comp: DictionaryHasWordClass;
    let dictionaryHasWordServiceStub: SinonStubbedInstance<DictionaryHasWordService>;

    beforeEach(() => {
      dictionaryHasWordServiceStub = sinon.createStubInstance<DictionaryHasWordService>(DictionaryHasWordService);

      wrapper = shallowMount<DictionaryHasWordClass>(DictionaryHasWordUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          dictionaryHasWordService: () => dictionaryHasWordServiceStub,

          dictionaryService: () => new DictionaryService(),

          wordService: () => new WordService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.dictionaryHasWord = entity;
        dictionaryHasWordServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dictionaryHasWordServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dictionaryHasWord = entity;
        dictionaryHasWordServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dictionaryHasWordServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
