<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="libFourApp.chat.home.createOrEditLabel" v-text="$t('libFourApp.chat.home.createOrEditLabel')">Create or edit a Chat</h2>
                <div>
                    <div class="form-group" v-if="chat.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="chat.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.chat.chatType')" for="chat-chatType">Chat Type</label>
                        <select class="form-control" name="chatType" :class="{'valid': !$v.chat.chatType.$invalid, 'invalid': $v.chat.chatType.$invalid }" v-model="$v.chat.chatType.$model" id="chat-chatType"  required>
                            <option value="SUPPORT" v-bind:label="$t('libFourApp.chatTypeEnum.SUPPORT')">SUPPORT</option>
                            <option value="COMMON" v-bind:label="$t('libFourApp.chatTypeEnum.COMMON')">COMMON</option>
                        </select>
                        <div v-if="$v.chat.chatType.$anyDirty && $v.chat.chatType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.chat.chatType.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.chat.title')" for="chat-title">Title</label>
                        <input type="text" class="form-control" name="title" id="chat-title"
                            :class="{'valid': !$v.chat.title.$invalid, 'invalid': $v.chat.title.$invalid }" v-model="$v.chat.title.$model" />
                    </div>
                    <div class="form-group">
                        <label v-text="$t('libFourApp.chat.user')" for="chat-user">User</label>
                        <select class="form-control" id="chat-user" multiple name="user" v-model="chat.users">
                            <option v-bind:value="getSelected(chat.users, userOption)" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.chat.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./chat-update.component.ts">
</script>
