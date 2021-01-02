<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="libFourApp.dictionaryHasWord.home.createOrEditLabel" v-text="$t('libFourApp.dictionaryHasWord.home.createOrEditLabel')">Create or edit a DictionaryHasWord</h2>
                <div>
                    <div class="form-group" v-if="dictionaryHasWord.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="dictionaryHasWord.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.dictionaryHasWord.count')" for="dictionary-has-word-count">Count</label>
                        <input type="number" class="form-control" name="count" id="dictionary-has-word-count"
                            :class="{'valid': !$v.dictionaryHasWord.count.$invalid, 'invalid': $v.dictionaryHasWord.count.$invalid }" v-model.number="$v.dictionaryHasWord.count.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.dictionaryHasWord.dictionary')" for="dictionary-has-word-dictionary">Dictionary</label>
                        <select class="form-control" id="dictionary-has-word-dictionary" name="dictionary" v-model="dictionaryHasWord.dictionaryId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="dictionaryOption.id" v-for="dictionaryOption in dictionaries" :key="dictionaryOption.id">{{dictionaryOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.dictionaryHasWord.word')" for="dictionary-has-word-word">Word</label>
                        <select class="form-control" id="dictionary-has-word-word" name="word" v-model="dictionaryHasWord.wordId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="wordOption.id" v-for="wordOption in words" :key="wordOption.id">{{wordOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.dictionaryHasWord.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./dictionary-has-word-update.component.ts">
</script>
