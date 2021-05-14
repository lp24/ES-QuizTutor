export default class StatementOptionWithRelevance {
  optionId!: number;
  content!: string;
  relevance!: number;

  constructor(jsonObj?: StatementOptionWithRelevance) {
    if (jsonObj) {
      this.optionId = jsonObj.optionId;
      this.content = jsonObj.content;
      this.relevance = jsonObj.relevance;
    }
  }
}
