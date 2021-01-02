/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DictionaryUpdateComponent from '@/entities/dictionary/dictionary-update.vue';
import DictionaryClass from '@/entities/dictionary/dictionary-update.component';
import DictionaryService from '@/entities/dictionary/dictionary.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Dictionary Management Update Component', () => {
    let wrapper: Wrapper<DictionaryClass>;
    let comp: DictionaryClass;
    let dictionaryServiceStub: SinonStubbedInstance<DictionaryService>;

    beforeEach(() => {
      dictionaryServiceStub = sinon.createStubInstance<DictionaryService>(DictionaryService);

      wrapper = shallowMount<DictionaryClass>(DictionaryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          dictionaryService: () => dictionaryServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.dictionary = entity;
        dictionaryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dictionaryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dictionary = entity;
        dictionaryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dictionaryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
