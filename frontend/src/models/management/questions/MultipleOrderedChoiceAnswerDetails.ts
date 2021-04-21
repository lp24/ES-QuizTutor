import Option from '@/models/management/Option';
import AnswerDetails from '@/models/management/questions/AnswerDetails';
import { QuestionTypes, convertToLetter } from '@/services/QuestionHelpers';

export default class MultipleOrderedChoiceAnswerType extends AnswerDetails {
  option!: Option;

  constructor(jsonObj?: MultipleOrderedChoiceAnswerType) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      this.option = new Option(jsonObj.option);
    }
  }

  isCorrect(): boolean {
    return this.option.correct;
  }
  answerRepresentation(): string {
    return convertToLetter(this.option.sequence);
  }
}
