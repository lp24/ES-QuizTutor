<template>
  <div class="multiple-ordered-choice-options">
    <v-row>
      <v-col cols="1" offset="9"> Correct </v-col>
      <v-col cols="2"> Relevance </v-col>
    </v-row>
    <v-row
      v-for="(option, index) in sQuestionDetails.options"
      :key="index"
      data-cy="questionOrderedOptionsInput"
    >
      <v-col cols="9">
        <v-textarea
          v-model="option.content"
          :label="`OptionWithRelevance ${index + 1}`"
          :data-cy="`OptionWithRelevance${index + 1}`"
          rows="1"
          auto-grow
        ></v-textarea>
      </v-col>
      <v-col cols="1">
        <v-switch
          v-model="option.correct"
          inset
          :data-cy="`Switch${index + 1}`"
        />
      </v-col>
      <v-col
          cols="1"
          sm="2"
          md="1"
      >
        <v-text-field
          v-model="option.relevance"
          :data-cy="`Relevanc${index + 1}`"

        ></v-text-field>
      </v-col>
      <v-col v-if="sQuestionDetails.options.length > 2">
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-icon
              :data-cy="`Delete${index + 1}`"
              small
              class="ma-1 action-button"
              v-on="on"
              @click="removeOption(index)"
              color="red"
            >
              close</v-icon>
          </template>
          <span>Remove Option</span>
        </v-tooltip>
      </v-col>
    </v-row>

    <!--  OptionWith Relevance-->

    <v-row>
      <v-btn
        class="ma-auto"
        color="blue darken-1"
        @click="addOption"
        data-cy="addOptionMultipleOrderedChoice"
        >Add Option</v-btn
      >
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, Model, PropSync, Vue, Watch } from 'vue-property-decorator';
import OptionWithRelevance from '@/models/management/OptionWithRelevance';
import MultipleOrderedChoiceQuestionDetails from '@/models/management/questions/MultipleOrderedChoiceQuestionDetails';

@Component
export default class MultipleOrderedChoiceCreate extends Vue {
  @PropSync('questionDetails', { type: MultipleOrderedChoiceQuestionDetails })
  sQuestionDetails!: MultipleOrderedChoiceQuestionDetails;
  addOption() {
    this.sQuestionDetails.options.push(new OptionWithRelevance());
  }

  removeOption(index: number) {
    this.sQuestionDetails.options.splice(index, 1);
  }
}
</script>


