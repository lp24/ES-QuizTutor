<!--
Used on:
  - QuestionComponent.vue
  - ResultComponent.vue
-->

<template>
  <ul data-cy="optionList" class="option-list">
    <li
      v-for="(n, index) in questionDetails.options.length"
      :key="index"
      v-bind:class="['option', optionClass(index)]"
      @click="
        !isReadonly && selectOption(questionDetails.options[index].optionId)
      "
    >
      <span
        v-if="
          isReadonly &&
          correctAnswerDetails.correctOptionId ===
            questionDetails.options[index].optionId
        "
        class="fas fa-check option-letter"
      />
      <span
        v-else-if="
          isReadonly &&
          answerDetails.optionId === questionDetails.options[index].optionId
        "
        class="fas fa-times option-letter"
      />
      <span v-else class="option-letter">{{
        String.fromCharCode(65 + index)
      }}</span>
      <span
        class="option-content"
        v-html="convertMarkDown(questionDetails.options[index].content)"
      />
    </li>
  </ul>
</template>

<script lang="ts">
import { Component, Vue, Prop, Model, Emit } from 'vue-property-decorator';
import MultipleOrderedChoiceStatementQuestionDetails from '@/models/statement/questions/MultipleOrderedChoiceStatementQuestionDetails';
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Image from '@/models/management/Image';
import MultipleOrderedChoiceStatementAnswerDetails from '@/models/statement/questions/MultipleOrderedChoiceStatementAnswerDetails';
import MultipleOrderedChoiceStatementCorrectAnswerDetails from '@/models/statement/questions/MultipleOrderedChoiceStatementCorrectAnswerDetails';

@Component
export default class MultipleOrderedChoiceAnswer extends Vue {
  @Prop(MultipleOrderedChoiceStatementQuestionDetails)
  readonly questionDetails!: MultipleOrderedChoiceStatementQuestionDetails;
  @Prop(MultipleOrderedChoiceStatementAnswerDetails)
  answerDetails!: MultipleOrderedChoiceStatementAnswerDetails;
  @Prop(MultipleOrderedChoiceStatementCorrectAnswerDetails)
  readonly correctAnswerDetails?: MultipleOrderedChoiceStatementCorrectAnswerDetails;

  get isReadonly() {
    return !!this.correctAnswerDetails;
  }

  optionClass(index: number) {
    if (this.isReadonly) {
      if (
        !!this.correctAnswerDetails &&
        this.correctAnswerDetails.correctOptionId ===
          this.questionDetails.options[index].optionId
      ) {
        return 'correct';
      } else if (
        this.answerDetails.optionId ===
        this.questionDetails.options[index].optionId
      ) {
        return 'wrong';
      } else {
        return '';
      }
    } else {
      return this.answerDetails.optionId ===
        this.questionDetails.options[index].optionId
        ? 'selected'
        : '';
    }
  }

  @Emit('question-answer-update')
  selectOption(optionId: number) {
    if (this.answerDetails.optionId === optionId) {
      this.answerDetails.optionId = null;
    } else {
      this.answerDetails.optionId = optionId;
    }
  }

  convertMarkDown(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }
}
</script>

<style lang="scss" scoped>
.unanswered {
  .correct {
    .option-content {
      background-color: #333333;
      color: rgb(255, 255, 255) !important;
    }

    .option-letter {
      background-color: #333333 !important;
      color: rgb(255, 255, 255) !important;
    }
  }
}

.correct-question {
  .correct {
    .option-content {
      background-color: #299455;
      color: rgb(255, 255, 255) !important;
    }

    .option-letter {
      background-color: #299455 !important;
      color: rgb(255, 255, 255) !important;
    }
  }
}

.incorrect-question {
  .wrong {
    .option-content {
      background-color: #cf2323;
      color: rgb(255, 255, 255) !important;
    }

    .option-letter {
      background-color: #cf2323 !important;
      color: rgb(255, 255, 255) !important;
    }
  }
  .correct {
    .option-content {
      background-color: #333333;
      color: rgb(255, 255, 255) !important;
    }

    .option-letter {
      background-color: #333333 !important;
      color: rgb(255, 255, 255) !important;
    }
  }
}
</style>
