<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="dictionaryHasWord">
                <h2 class="jh-entity-heading"><span v-text="$t('libFourApp.dictionaryHasWord.detail.title')">DictionaryHasWord</span> {{dictionaryHasWord.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('libFourApp.dictionaryHasWord.count')">Count</span>
                    </dt>
                    <dd>
                        <span>{{dictionaryHasWord.count}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.dictionaryHasWord.dictionary')">Dictionary</span>
                    </dt>
                    <dd>
                        <div v-if="dictionaryHasWord.dictionaryId">
                            <router-link :to="{name: 'DictionaryView', params: {dictionaryId: dictionaryHasWord.dictionaryId}}">{{dictionaryHasWord.dictionaryId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.dictionaryHasWord.word')">Word</span>
                    </dt>
                    <dd>
                        <div v-if="dictionaryHasWord.wordId">
                            <router-link :to="{name: 'WordView', params: {wordId: dictionaryHasWord.wordId}}">{{dictionaryHasWord.wordId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="dictionaryHasWord.id" :to="{name: 'DictionaryHasWordEdit', params: {dictionaryHasWordId: dictionaryHasWord.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./dictionary-has-word-details.component.ts">
</script>
