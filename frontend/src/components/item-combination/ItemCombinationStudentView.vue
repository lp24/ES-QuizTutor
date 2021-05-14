<template>
    <ul data-cy="expectedAnswer" class="expected-answer">

            <span> Answer given by student: </span>

            <span>{{ this.answerDetails.answer }}</span>

            <span>Correct Answer: </span>

            <span>{{ this.correctAnswerDetails.correctAnswer }}</span>

    </ul>
</template>

<script lang="ts">
import { Component, Vue, Prop, Model, Emit } from 'vue-property-decorator';
import ItemCombinationStatementQuestionDetails from '@/models/statement/questions/ItemCombinationStatementQuestionDetails';
import ItemCombinationStatementAnswerDetails from '@/models/statement/questions/ItemCombinationStatementAnswerDetails';
import ItemCombinationStatementCorrectAnswerDetails from '@/models/statement/questions/ItemCombinationStatementCorrectAnswerDetails';
@Component
export default class ItemCombinationStudentView extends Vue {
  @Prop(ItemCombinationStatementQuestionDetails)
  questionDetails!: ItemCombinationStatementQuestionDetails;
  @Prop(ItemCombinationStatementAnswerDetails)
  answerDetails!: ItemCombinationStatementAnswerDetails;
   @Prop(ItemCombinationStatementCorrectAnswerDetails)
   correctAnswerDetails?: ItemCombinationStatementCorrectAnswerDetails;
}

</script>