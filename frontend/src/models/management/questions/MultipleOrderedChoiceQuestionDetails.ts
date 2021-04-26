import OptionWithRelevance from '@/models/management/OptionWithRelevance';
import QuestionDetails from '@/models/management/questions/QuestionDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class MultipleOrderedChoiceQuestionDetails extends QuestionDetails {
  options: OptionWithRelevance[] = [
    new OptionWithRelevance(),
    new OptionWithRelevance(),
    new OptionWithRelevance(),
    new OptionWithRelevance(),
  ];

  constructor(jsonObj?: MultipleOrderedChoiceQuestionDetails) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      console.log(jsonObj);

      this.options = jsonObj.options.map(
        (options: OptionWithRelevance) => new OptionWithRelevance(options)
      );
    }
  }

  setAsNew(): void {
    this.options.forEach((options) => {
      options.id = null;
    });
  }
}
