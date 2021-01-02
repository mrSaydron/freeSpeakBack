<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('libFourApp.book.home.title')" id="book-heading">Books</span>
            <router-link :to="{name: 'BookCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-book">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('libFourApp.book.home.createLabel')">
                    Create a new Book
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
        <div class="alert alert-warning" v-if="!isFetching && books && books.length === 0">
            <span v-text="$t('libFourApp.book.home.notFound')">No books found</span>
        </div>
        <div class="table-responsive" v-if="books && books.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('libFourApp.book.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('author')"><span v-text="$t('libFourApp.book.author')">Author</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'author'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('source')"><span v-text="$t('libFourApp.book.source')">Source</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('text')"><span v-text="$t('libFourApp.book.text')">Text</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'text'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('publicBook')"><span v-text="$t('libFourApp.book.publicBook')">Public Book</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'publicBook'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('dictionaryId')"><span v-text="$t('libFourApp.book.dictionary')">Dictionary</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dictionaryId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="book in books"
                    :key="book.id">
                    <td>
                        <router-link :to="{name: 'BookView', params: {bookId: book.id}}">{{book.id}}</router-link>
                    </td>
                    <td>{{book.title}}</td>
                    <td>{{book.author}}</td>
                    <td>{{book.source}}</td>
                    <td>{{book.text}}</td>
                    <td>{{book.publicBook}}</td>
                    <td>
                        <div v-if="book.dictionaryId">
                            <router-link :to="{name: 'DictionaryView', params: {dictionaryId: book.dictionaryId}}">{{book.dictionaryId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'BookView', params: {bookId: book.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'BookEdit', params: {bookId: book.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(book)"
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
            <span slot="modal-title"><span id="libFourApp.book.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-book-heading" v-text="$t('libFourApp.book.delete.question', {'id': removeId})">Are you sure you want to delete this Book?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-book" v-text="$t('entity.action.delete')" v-on:click="removeBook()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./book.component.ts">
</script>
