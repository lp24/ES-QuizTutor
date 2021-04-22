import Option from '@/models/management/Option';
import OptionWithRelevance from '@/models/management/OptionWithRelevance';
import QuestionDetails from '@/models/management/questions/QuestionDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class MultipleOrderedChoiceQuestionDetails extends QuestionDetails {
  options: Option[] = [new Option(), new Option(), new Option(), new Option()];
  /**
  optionsWithRelevance: OptionWithRelevance[] = [new OptionWithRelevance(), new OptionWithRelevance(), new OptionWithRelevance(), new OptionWithRelevance()];
  */

  constructor(jsonObj?: MultipleOrderedChoiceQuestionDetails) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      this.options = jsonObj.options.map(
        (option: Option) => new Option(option)
      );
    }
    /*if (jsonObj) {
      this.optionsWithRelevance = jsonObj.optionsWithRelevance.map(
        (optionsWithRelevance: OptionWithRelevance) => new OptionWithRelevance(optionsWithRelevance)
      );
    }*/
  }

  setAsNew(): void {
    this.options.forEach((option) => {
      option.id = null;
    });
    /*this.optionsWithRelevance.forEach((optionsWithRelevance) => {
      optionsWithRelevance.id = null;
    });*/
  }
}
