<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="libFourApp.dictionary.home.createOrEditLabel" v-text="$t('libFourApp.dictionary.home.createOrEditLabel')">Create or edit a Dictionary</h2>
                <div>
                    <div class="form-group" v-if="dictionary.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="dictionary.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.dictionary.baseLanguage')" for="dictionary-baseLanguage">Base Language</label>
                        <input type="text" class="form-control" name="baseLanguage" id="dictionary-baseLanguage"
                            :class="{'valid': !$v.dictionary.baseLanguage.$invalid, 'invalid': $v.dictionary.baseLanguage.$invalid }" v-model="$v.dictionary.baseLanguage.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('libFourApp.dictionary.targerLanguage')" for="dictionary-targerLanguage">Targer Language</label>
                        <input type="text" class="form-control" name="targerLanguage" id="dictionary-targerLanguage"
                            :class="{'valid': !$v.dictionary.targerLanguage.$invalid, 'invalid': $v.dictionary.targerLanguage.$invalid }" v-model="$v.dictionary.targerLanguage.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.dictionary.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./dictionary-update.component.ts">
</script>
