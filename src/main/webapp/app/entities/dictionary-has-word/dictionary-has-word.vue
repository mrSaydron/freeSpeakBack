<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('libFourApp.dictionaryHasWord.home.title')" id="dictionary-has-word-heading">Dictionary Has Words</span>
            <router-link :to="{name: 'DictionaryHasWordCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-dictionary-has-word">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('libFourApp.dictionaryHasWord.home.createLabel')">
                    Create a new Dictionary Has Word
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
        <div class="alert alert-warning" v-if="!isFetching && dictionaryHasWords && dictionaryHasWords.length === 0">
            <span v-text="$t('libFourApp.dictionaryHasWord.home.notFound')">No dictionaryHasWords found</span>
        </div>
        <div class="table-responsive" v-if="dictionaryHasWords && dictionaryHasWords.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('libFourApp.dictionaryHasWord.count')">Count</span></th>
                    <th><span v-text="$t('libFourApp.dictionaryHasWord.dictionary')">Dictionary</span></th>
                    <th><span v-text="$t('libFourApp.dictionaryHasWord.word')">Word</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="dictionaryHasWord in dictionaryHasWords"
                    :key="dictionaryHasWord.id">
                    <td>
                        <router-link :to="{name: 'DictionaryHasWordView', params: {dictionaryHasWordId: dictionaryHasWord.id}}">{{dictionaryHasWord.id}}</router-link>
                    </td>
                    <td>{{dictionaryHasWord.count}}</td>
                    <td>
                        <div v-if="dictionaryHasWord.dictionaryId">
                            <router-link :to="{name: 'DictionaryView', params: {dictionaryId: dictionaryHasWord.dictionaryId}}">{{dictionaryHasWord.dictionaryId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="dictionaryHasWord.wordId">
                            <router-link :to="{name: 'WordView', params: {wordId: dictionaryHasWord.wordId}}">{{dictionaryHasWord.wordId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DictionaryHasWordView', params: {dictionaryHasWordId: dictionaryHasWord.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DictionaryHasWordEdit', params: {dictionaryHasWordId: dictionaryHasWord.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(dictionaryHasWord)"
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
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="libFourApp.dictionaryHasWord.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-dictionaryHasWord-heading" v-text="$t('libFourApp.dictionaryHasWord.delete.question', {'id': removeId})">Are you sure you want to delete this Dictionary Has Word?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-dictionaryHasWord" v-text="$t('entity.action.delete')" v-on:click="removeDictionaryHasWord()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./dictionary-has-word.component.ts">
</script>
