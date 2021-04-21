export default class OptionWithRelevance {
  id: number | null = null;
  sequence!: number | null;
  content: string = '';
  correct: boolean = false;
  relevance: number = 0;

  constructor(jsonObj?: OptionWithRelevance) {
    if (jsonObj) {
      this.id = jsonObj.id;
      this.sequence = jsonObj.sequence;
      this.content = jsonObj.content;
      this.correct = jsonObj.correct;
      this.relevance = jsonObj.relevance;
    }
  }
}
