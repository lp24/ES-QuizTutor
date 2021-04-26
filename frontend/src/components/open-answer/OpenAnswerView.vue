<template>
  <v-card-text class="text-left">
    <div v-html="convertMarkDown(studentAnswered())" />
  </v-card-text>
</template>

<script lang="ts">
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import { Component, Vue, Prop } from 'vue-property-decorator';
import OpenAnswerAnswerDetails from '@/models/management/questions/OpenAnswerAnswerDetails';
import Image from '@/models/management/Image';

@Component
export default class OpenAnswerView extends Vue {
  @Prop() readonly answerDetails?: OpenAnswerAnswerDetails;

  studentAnswered() {
    return this.answerDetails?.answer;
  }

  convertMarkDown(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }
}
</script>
