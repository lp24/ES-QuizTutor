import AnswerDetails from '@/models/management/questions/AnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';
import OpenAnswerQuestionDetails from '@/models/management/questions/OpenAnswerQuestionDetails';

export default class OpenAnswerAnswerType extends AnswerDetails {
  studentAnswer: string = '';

  constructor(jsonObj?: OpenAnswerAnswerType) {
    super(QuestionTypes.OpenAnswer);
    if (jsonObj) {
      this.studentAnswer = jsonObj.studentAnswer;
    }
  }

  isCorrect(questionDetails: OpenAnswerQuestionDetails): boolean {
    return this.studentAnswer===questionDetails.correctAnswer;
  }

  answerRepresentation(questionDetails: OpenAnswerQuestionDetails): string {
    return this.studentAnswer;
  }
}
