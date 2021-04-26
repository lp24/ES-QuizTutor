import OptionWithRelevance from '@/models/management/OptionWithRelevance';
import AnswerDetails from '@/models/management/questions/AnswerDetails';
import { QuestionTypes, convertToLetter } from '@/services/QuestionHelpers';

export default class MultipleOrderedChoiceAnswerType extends AnswerDetails {
  option!: OptionWithRelevance;

  constructor(jsonObj?: MultipleOrderedChoiceAnswerType) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      this.option = new OptionWithRelevance(jsonObj.option);
    }
  }

  isCorrect(): boolean {
    return this.option.correct;
  }
  answerRepresentation(): string {
    return convertToLetter(this.option.sequence);
  }
}
