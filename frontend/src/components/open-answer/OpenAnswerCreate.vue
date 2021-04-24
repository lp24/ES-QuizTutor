<template>
  <div class="open-answer-answer">
    <v-row data-cy="questionAnswer">
      <v-col>
        <v-textarea
          :label="`Answer`"
          :data-cy="`Answer`"
          rows="4"
          auto-grow
        ></v-textarea>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, PropSync, Vue } from 'vue-property-decorator';
import OpenAnswerQuestionDetails from '@/models/management/questions/OpenAnswerQuestionDetails';

@Component
export default class OpenAnswerCreate extends Vue {
  @PropSync('questionDetails', { type: OpenAnswerQuestionDetails })
  sQuestionDetails!: OpenAnswerQuestionDetails;
}
</script>
