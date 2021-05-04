import QuestionDetails from '@/models/management/questions/QuestionDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class OpenAnswerQuestionDetails extends QuestionDetails {
  correctAnswer: string = ''; // This is not optional

  constructor(jsonObj?: OpenAnswerQuestionDetails) {
    super(QuestionTypes.OpenAnswer);
    if (jsonObj) {
      this.correctAnswer = jsonObj.correctAnswer;
    }
  }

  setAsNew(): void {}
}
