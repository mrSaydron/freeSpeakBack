<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('libFourApp.dictionary.home.title')" id="dictionary-heading">Dictionaries</span>
            <router-link :to="{name: 'DictionaryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-dictionary">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('libFourApp.dictionary.home.createLabel')">
                    Create a new Dictionary
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
        <div class="alert alert-warning" v-if="!isFetching && dictionaries && dictionaries.length === 0">
            <span v-text="$t('libFourApp.dictionary.home.notFound')">No dictionaries found</span>
        </div>
        <div class="table-responsive" v-if="dictionaries && dictionaries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('libFourApp.dictionary.baseLanguage')">Base Language</span></th>
                    <th><span v-text="$t('libFourApp.dictionary.targerLanguage')">Targer Language</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="dictionary in dictionaries"
                    :key="dictionary.id">
                    <td>
                        <router-link :to="{name: 'DictionaryView', params: {dictionaryId: dictionary.id}}">{{dictionary.id}}</router-link>
                    </td>
                    <td>{{dictionary.baseLanguage}}</td>
                    <td>{{dictionary.targerLanguage}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DictionaryView', params: {dictionaryId: dictionary.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DictionaryEdit', params: {dictionaryId: dictionary.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(dictionary)"
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
            <span slot="modal-title"><span id="libFourApp.dictionary.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-dictionary-heading" v-text="$t('libFourApp.dictionary.delete.question', {'id': removeId})">Are you sure you want to delete this Dictionary?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-dictionary" v-text="$t('entity.action.delete')" v-on:click="removeDictionary()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./dictionary.component.ts">
</script>
