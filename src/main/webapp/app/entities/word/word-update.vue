<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="libFourApp.word.home.createOrEditLabel" v-text="$t('libFourApp.word.home.createOrEditLabel')">Create or edit a Word</h2>
                <div>
                    <div class="form-group" v-if="word.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="word.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.word.word')" for="word-word">Word</label>
                        <input type="text" class="form-control" name="word" id="word-word"
                            :class="{'valid': !$v.word.word.$invalid, 'invalid': $v.word.word.$invalid }" v-model="$v.word.word.$model"  required/>
                        <div v-if="$v.word.word.$anyDirty && $v.word.word.$invalid">
                            <small class="form-text text-danger" v-if="!$v.word.word.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.word.translate')" for="word-translate">Translate</label>
                        <input type="text" class="form-control" name="translate" id="word-translate"
                            :class="{'valid': !$v.word.translate.$invalid, 'invalid': $v.word.translate.$invalid }" v-model="$v.word.translate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.word.partOfSpeech')" for="word-partOfSpeech">Part Of Speech</label>
                        <input type="text" class="form-control" name="partOfSpeech" id="word-partOfSpeech"
                            :class="{'valid': !$v.word.partOfSpeech.$invalid, 'invalid': $v.word.partOfSpeech.$invalid }" v-model="$v.word.partOfSpeech.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.word.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./word-update.component.ts">
</script>
