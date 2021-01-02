<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="book">
                <h2 class="jh-entity-heading"><span v-text="$t('libFourApp.book.detail.title')">Book</span> {{book.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('libFourApp.book.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{book.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.book.author')">Author</span>
                    </dt>
                    <dd>
                        <span>{{book.author}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.book.source')">Source</span>
                    </dt>
                    <dd>
                        <span>{{book.source}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.book.text')">Text</span>
                    </dt>
                    <dd>
                        <span>{{book.text}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.book.publicBook')">Public Book</span>
                    </dt>
                    <dd>
                        <span>{{book.publicBook}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.book.dictionary')">Dictionary</span>
                    </dt>
                    <dd>
                        <div v-if="book.dictionaryId">
                            <router-link :to="{name: 'DictionaryView', params: {dictionaryId: book.dictionaryId}}">{{book.dictionaryId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('libFourApp.book.loadedUser')">Loaded User</span>
                    </dt>
                    <dd>
                        {{book.loadedUserLogin}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="book.id" :to="{name: 'BookEdit', params: {bookId: book.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./book-details.component.ts">
</script>
