import AnswerDetails from '@/models/management/questions/AnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';
import OpenAnswerQuestionDetails from '@/models/management/questions/OpenAnswerQuestionDetails';

export default class OpenAnswerAnswerType extends AnswerDetails {
  answer: string = '';

  constructor(jsonObj?: OpenAnswerAnswerType) {
    super(QuestionTypes.OpenAnswer);
    if (jsonObj) {
      this.answer = jsonObj.answer;
    }
  }

  isCorrect(questionDetails: OpenAnswerQuestionDetails): boolean {
    return false;
  }

  answerRepresentation(questionDetails: OpenAnswerQuestionDetails): string {
    return this.answer;
  }
}
