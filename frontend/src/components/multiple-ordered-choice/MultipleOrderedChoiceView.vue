<template>
  <ul>
    <li v-for="option in questionDetails.options" :key="option.id">
      <span
        v-if="option.correct"
        v-html="
          convertMarkDown(
            studentAnswered(option.id) + '**[★]** ' + ' **[**'+ option.relevance + '**]** '+ option.content
          )
        "
        v-bind:class="[option.correct ? 'font-weight-bold' : '']"
      />
      <span
        v-else
        v-html="convertMarkDown(studentAnswered(option.id) + option.content)"
      />
    </li>
  </ul>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Question from '@/models/management/Question';
import Image from '@/models/management/Image';
import MultipleOrderedChoiceQuestionDetails from '@/models/management/questions/MultipleOrderedChoiceQuestionDetails';
import MultipleOrderedChoiceAnswerDetails from '@/models/management/questions/MultipleOrderedChoiceAnswerDetails';

@Component
export default class MultipleOrderedChoiceView extends Vue {
  @Prop() readonly questionDetails!: MultipleOrderedChoiceQuestionDetails;
  @Prop() readonly answerDetails?: MultipleOrderedChoiceAnswerDetails;

  studentAnswered(option: number) {
    return this.answerDetails && this.answerDetails?.option.id === option
      ? '**[S]** '
      : '';
  }

  convertMarkDown(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }
}
</script>
