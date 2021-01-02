<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="word">
                <h2 class="jh-entity-heading"><span v-text="$t('libFourApp.word.detail.title')">Word</span> {{word.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('libFourApp.word.word')">Word</span>
                    </dt>
                    <dd>
                        <span>{{word.word}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.word.translate')">Translate</span>
                    </dt>
                    <dd>
                        <span>{{word.translate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.word.partOfSpeech')">Part Of Speech</span>
                    </dt>
                    <dd>
                        <span>{{word.partOfSpeech}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="word.id" :to="{name: 'WordEdit', params: {wordId: word.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./word-details.component.ts">
</script>
