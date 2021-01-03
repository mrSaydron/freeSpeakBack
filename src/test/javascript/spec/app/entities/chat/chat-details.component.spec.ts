/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ChatDetailComponent from '@/entities/chat/chat-details.vue';
import ChatClass from '@/entities/chat/chat-details.component';
import ChatService from '@/entities/chat/chat.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Chat Management Detail Component', () => {
    let wrapper: Wrapper<ChatClass>;
    let comp: ChatClass;
    let chatServiceStub: SinonStubbedInstance<ChatService>;

    beforeEach(() => {
      chatServiceStub = sinon.createStubInstance<ChatService>(ChatService);

      wrapper = shallowMount<ChatClass>(ChatDetailComponent, { store, i18n, localVue, provide: { chatService: () => chatServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundChat = { id: 123 };
        chatServiceStub.find.resolves(foundChat);

        // WHEN
        comp.retrieveChat(123);
        await comp.$nextTick();

        // THEN
        expect(comp.chat).toBe(foundChat);
      });
    });
  });
});
