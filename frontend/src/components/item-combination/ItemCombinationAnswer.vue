<template>
  <div class="item_combination-answer">
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

import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Image from '@/models/management/Image';
import ItemCombinationStatementAnswerDetails from '@/models/statement/questions/ItemCombinationStatementAnswerDetails';
import ItemCombinationStatementCorrectAnswerDetails from '@/models/statement/questions/ItemCombinationStatementCorrectAnswerDetails';
@Component
export default class ItemCombinationAnswer extends Vue {


  @Prop(ItemCombinationStatementAnswerDetails)
  answerDetails!: ItemCombinationStatementAnswerDetails;
  @Prop(ItemCombinationStatementCorrectAnswerDetails)
  readonly correctAnswerDetails?: ItemCombinationStatementCorrectAnswerDetails;
  studentAnswer : string = '';

  get isReadonly() {
    return !!this.correctAnswerDetails;
  }

  convertMarkDown(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }
}
</script>