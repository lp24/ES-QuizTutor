<template>
  <div id="ViewOpenAnswerAnswer">
    <BaseCodeEditor
      ref="myCmView"
      :code.sync="questionDetails.code"
      :language.sync="questionDetails.language"
      :editable="false"
    />
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Image from '@/models/management/Image';
import OpenAnswerQuestionDetails from '@/models/management/questions/OpenAnswerQuestionDetails';
import OpenAnswerAnswerDetails from '@/models/management/questions/OpenAnswerAnswerDetails';

@Component
export default class OpenAnswerView extends Vue {
  @Prop() readonly questionDetails!: OpenAnswerQuestionDetails;
  @Prop() readonly answerDetails?: OpenAnswerAnswerDetails;

  convertMarkDown(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }
}
</script>
