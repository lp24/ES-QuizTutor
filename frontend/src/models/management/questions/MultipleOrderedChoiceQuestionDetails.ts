import OptionWithRelevance from '@/models/management/OptionWithRelevance';
import QuestionDetails from '@/models/management/questions/QuestionDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class MultipleOrderedChoiceQuestionDetails extends QuestionDetails {
  optionsWithRelevance: OptionWithRelevance[] = [new OptionWithRelevance(), new OptionWithRelevance(), new OptionWithRelevance(), new OptionWithRelevance()];

  constructor(jsonObj?: MultipleOrderedChoiceQuestionDetails) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      console.log(jsonObj);
      //console.log(jsonObj.optionsWithRelevance);

      this.optionsWithRelevance = jsonObj.optionsWithRelevance.map(
          (optionsWithRelevance: OptionWithRelevance) => new OptionWithRelevance(optionsWithRelevance)
      );
    }
  }

  setAsNew(): void {
    this.optionsWithRelevance.forEach((optionsWithRelevance) => {
      optionsWithRelevance.id = null;
    });
  }
}
