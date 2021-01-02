<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('libFourApp.word.home.title')" id="word-heading">Words</span>
            <router-link :to="{name: 'WordCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-word">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('libFourApp.word.home.createLabel')">
                    Create a new Word
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && words && words.length === 0">
            <span v-text="$t('libFourApp.word.home.notFound')">No words found</span>
        </div>
        <div class="table-responsive" v-if="words && words.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('word')"><span v-text="$t('libFourApp.word.word')">Word</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'word'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('translate')"><span v-text="$t('libFourApp.word.translate')">Translate</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'translate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('partOfSpeech')"><span v-text="$t('libFourApp.word.partOfSpeech')">Part Of Speech</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'partOfSpeech'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="word in words"
                    :key="word.id">
                    <td>
                        <router-link :to="{name: 'WordView', params: {wordId: word.id}}">{{word.id}}</router-link>
                    </td>
                    <td>{{word.word}}</td>
                    <td>{{word.translate}}</td>
                    <td>{{word.partOfSpeech}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'WordView', params: {wordId: word.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'WordEdit', params: {wordId: word.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(word)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
                <infinite-loading
                    ref="infiniteLoading"
                    v-if="totalItems > itemsPerPage"
                    :identifier="infiniteId"
                    slot="append"
                    @infinite="loadMore"
                    force-use-infinite-wrapper=".el-table__body-wrapper"
                    :distance='20'>
                </infinite-loading>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="libFourApp.word.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-word-heading" v-text="$t('libFourApp.word.delete.question', {'id': removeId})">Are you sure you want to delete this Word?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-word" v-text="$t('entity.action.delete')" v-on:click="removeWord()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./word.component.ts">
</script>
