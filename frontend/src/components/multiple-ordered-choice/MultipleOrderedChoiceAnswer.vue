<template>
  <div class="multiple-ordered-choice-options">
    <v-row>
      <v-col cols="1" offset="9"> Correct </v-col>
      <v-col cols="2"> Relevance </v-col>
    </v-row>
    <v-row
      v-for="(option, index) in questionDetails.options"
      :key="index"
      data-cy="questionOrderedOptionsInput"
    >
      <v-col cols="9">
        <v-textarea
          v-model="option.content"
          :data-cy="`Option${index + 1}`"
          rows="1"
          auto-grow
        ></v-textarea>
      </v-col>
      <v-col cols="1">
        <v-switch
          :data-cy="`Switch${index + 1}`"
        />
      </v-col>
      <v-col
        cols="1"
        sm="2"
        md="1"
      >
        <v-text-field
          label="Relevance"
        ></v-text-field>
      </v-col>

    </v-row>



  </div>

</template>

<script lang="ts">
import { Component, Emit, Prop, Vue } from 'vue-property-decorator';
import MultipleOrderedChoiceStatementQuestionDetails from '@/models/statement/questions/MultipleOrderedChoiceStatementQuestionDetails';
import MultipleOrderedChoiceStatementAnswerDetails from '@/models/statement/questions/MultipleOrderedChoiceStatementAnswerDetails';
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Image from '@/models/management/Image';
import draggable from 'vuedraggable';
import CodeOrderSlotStatementQuestionDetails from '@/models/statement/questions/CodeOrderSlotStatementQuestionDetails';
import CodeOrderSlotStatementAnswerDetails from '@/models/statement/questions/CodeOrderSlotStatementAnswerDetails';
import BaseCodeEditor from '@/components/BaseCodeEditor.vue';
import MultipleOrderedChoiceStatementCorrectAnswerDetails from '@/models/statement/questions/MultipleOrderedChoiceStatementCorrectAnswerDetails';

@Component
export default class MultipleOrderedChoiceAnswer extends Vue {
  @Prop(MultipleOrderedChoiceStatementQuestionDetails)
  readonly questionDetails!: MultipleOrderedChoiceStatementQuestionDetails;
  @Prop(MultipleOrderedChoiceStatementAnswerDetails)
  answerDetails!: MultipleOrderedChoiceStatementAnswerDetails;
  @Prop(MultipleOrderedChoiceStatementCorrectAnswerDetails)
  correctAnswerDetails!: MultipleOrderedChoiceStatementCorrectAnswerDetails;


}
</script>