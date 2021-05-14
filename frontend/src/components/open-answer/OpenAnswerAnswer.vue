<template>
  <div class="open-answer-answer">
    <v-row>
      <v-col>
        <input data-cy="studentAnswer" v-model="studentAnswer" placeholder="Answer:">
      </v-col>
      <v-col>
        <button data-cy="submitAnswer" v-on:click="submit">Submit</button>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <span>
          <b> Submitted: </b>{{this.answerDetails.studentAnswer}}
        </span>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop, Model, Emit } from 'vue-property-decorator';
import OpenAnswerStatementQuestionDetails from '@/models/statement/questions/OpenAnswerStatementQuestionDetails';
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Image from '@/models/management/Image';
import OpenAnswerStatementAnswerDetails from '@/models/statement/questions/OpenAnswerStatementAnswerDetails';
import OpenAnswerStatementCorrectAnswerDetails from '@/models/statement/questions/OpenAnswerStatementCorrectAnswerDetails';

@Component
export default class OpenAnswerAnswer extends Vue {
  @Prop(OpenAnswerStatementQuestionDetails)
  readonly questionDetails!: OpenAnswerStatementQuestionDetails;
  @Prop(OpenAnswerStatementAnswerDetails)
  answerDetails!: OpenAnswerStatementAnswerDetails;
  @Prop(OpenAnswerStatementCorrectAnswerDetails)
  readonly correctAnswerDetails?: OpenAnswerStatementCorrectAnswerDetails;
  studentAnswer : string = '';
  //FIXME: CSS

  get isReadonly() {
    return !!this.correctAnswerDetails;
  }

  @Emit('question-answer-update')
  submit() {
    this.answerDetails.studentAnswer = this.studentAnswer;
  }

  convertMarkDown(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }

}
</script>

