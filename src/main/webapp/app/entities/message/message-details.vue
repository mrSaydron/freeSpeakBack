<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="message">
                <h2 class="jh-entity-heading"><span v-text="$t('libFourApp.message.detail.title')">Message</span> {{message.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('libFourApp.message.message')">Message</span>
                    </dt>
                    <dd>
                        <span>{{message.message}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.message.time')">Time</span>
                    </dt>
                    <dd>
                        <span v-if="message.time">{{$d(Date.parse(message.time), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.message.user')">User</span>
                    </dt>
                    <dd>
                        {{message.userLogin}}
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.message.chat')">Chat</span>
                    </dt>
                    <dd>
                        <div v-if="message.chatId">
                            <router-link :to="{name: 'ChatView', params: {chatId: message.chatId}}">{{message.chatId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="message.id" :to="{name: 'MessageEdit', params: {messageId: message.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./message-details.component.ts">
</script>
