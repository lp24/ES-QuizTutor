<template>
  <div class="open-answer-answer">
    <v-row>
      <v-col>
        <v-textarea
          v-model="sQuestionDetails.correctAnswer"
          label="Answer"
          data-cy="Answer"
          rows="4"
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
