import StatementAnswerDetails from '@/models/statement/questions/StatementAnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';
import OpenAnswerStatementCorrectAnswerDetails from '@/models/statement/questions/OpenAnswerStatementCorrectAnswerDetails';

export default class OpenAnswerStatementAnswerDetails extends StatementAnswerDetails {
  public studentAnswer: string | null = null;

  constructor(jsonObj?: OpenAnswerStatementAnswerDetails) {
    super(QuestionTypes.OpenAnswer);
    if (jsonObj) {
      this.studentAnswer = jsonObj.studentAnswer;
    }
  }

  isQuestionAnswered(): boolean {
    return this.studentAnswer != null && this.studentAnswer.length > 0;
  }

  isAnswerCorrect(correctAnswerDetails:OpenAnswerStatementCorrectAnswerDetails): boolean {
    return correctAnswerDetails.correctAnswer===this.studentAnswer;
  }
}
