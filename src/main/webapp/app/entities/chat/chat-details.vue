<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="chat">
                <h2 class="jh-entity-heading"><span v-text="$t('libFourApp.chat.detail.title')">Chat</span> {{chat.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('libFourApp.chat.chatType')">Chat Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('libFourApp.chatTypeEnum.' + chat.chatType)">{{chat.chatType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.chat.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{chat.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.chat.user')">User</span>
                    </dt>
                    <dd>
                        <span v-for="(user, i) in chat.users" :key="user.id">{{i > 0 ? ', ' : ''}}
                            {{user.login}}
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="chat.id" :to="{name: 'ChatEdit', params: {chatId: chat.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./chat-details.component.ts">
</script>
