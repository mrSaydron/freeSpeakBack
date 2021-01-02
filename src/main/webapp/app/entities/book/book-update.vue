<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="libFourApp.book.home.createOrEditLabel" v-text="$t('libFourApp.book.home.createOrEditLabel')">Create or edit a Book</h2>
                <div>
                    <div class="form-group" v-if="book.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="book.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.book.title')" for="book-title">Title</label>
                        <input type="text" class="form-control" name="title" id="book-title"
                            :class="{'valid': !$v.book.title.$invalid, 'invalid': $v.book.title.$invalid }" v-model="$v.book.title.$model"  required/>
                        <div v-if="$v.book.title.$anyDirty && $v.book.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.book.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.book.author')" for="book-author">Author</label>
                        <input type="text" class="form-control" name="author" id="book-author"
                            :class="{'valid': !$v.book.author.$invalid, 'invalid': $v.book.author.$invalid }" v-model="$v.book.author.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.book.source')" for="book-source">Source</label>
                        <input type="text" class="form-control" name="source" id="book-source"
                            :class="{'valid': !$v.book.source.$invalid, 'invalid': $v.book.source.$invalid }" v-model="$v.book.source.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.book.text')" for="book-text">Text</label>
                        <input type="text" class="form-control" name="text" id="book-text"
                            :class="{'valid': !$v.book.text.$invalid, 'invalid': $v.book.text.$invalid }" v-model="$v.book.text.$model"  required/>
                        <div v-if="$v.book.text.$anyDirty && $v.book.text.$invalid">
                            <small class="form-text text-danger" v-if="!$v.book.text.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.book.publicBook')" for="book-publicBook">Public Book</label>
                        <input type="checkbox" class="form-check" name="publicBook" id="book-publicBook"
                            :class="{'valid': !$v.book.publicBook.$invalid, 'invalid': $v.book.publicBook.$invalid }" v-model="$v.book.publicBook.$model"  required/>
                        <div v-if="$v.book.publicBook.$anyDirty && $v.book.publicBook.$invalid">
                            <small class="form-text text-danger" v-if="!$v.book.publicBook.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.book.dictionary')" for="book-dictionary">Dictionary</label>
                        <select class="form-control" id="book-dictionary" name="dictionary" v-model="book.dictionaryId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="dictionaryOption.id" v-for="dictionaryOption in dictionaries" :key="dictionaryOption.id">{{dictionaryOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.book.loadedUser')" for="book-loadedUser">Loaded User</label>
                        <select class="form-control" id="book-loadedUser" name="loadedUser" v-model="book.loadedUserId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userOption.id" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('libFourApp.book.user')" for="book-user">User</label>
                        <select class="form-control" id="book-user" multiple name="user" v-model="book.users">
                            <option v-bind:value="getSelected(book.users, userOption)" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.book.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./book-update.component.ts">
</script>
