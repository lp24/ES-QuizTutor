import StatementAnswerDetails from '@/models/statement/questions/StatementAnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class OpenAnswerStatementAnswerDetails extends StatementAnswerDetails {
  public selectedAnswer: string | null = null;

  constructor(jsonObj?: OpenAnswerStatementAnswerDetails) {
    super(QuestionTypes.OpenAnswer);
    if (jsonObj) {
      this.selectedAnswer = jsonObj.selectedAnswer || this.selectedAnswer;
    }
  }

  isQuestionAnswered(): boolean {
    return this.selectedAnswer != null && this.selectedAnswer.length > 0;
  }

  isAnswerCorrect(): boolean {
    return true;
  }
}
