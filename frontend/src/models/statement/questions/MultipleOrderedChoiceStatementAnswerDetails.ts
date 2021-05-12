import StatementAnswerDetails from '@/models/statement/questions/StatementAnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';
import MultipleChoiceStatementCorrectAnswerDetails from '@/models/statement/questions/MultipleChoiceStatementCorrectAnswerDetails';

export default class MultipleOrderedChoiceStatementAnswerDetails extends StatementAnswerDetails {
  public optionId: number | null = null;

  constructor(jsonObj?: MultipleOrderedChoiceStatementAnswerDetails) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      this.optionId = jsonObj.optionId;
    }
  }

  isQuestionAnswered(): boolean {
    return this.optionId != null;
  }

  isAnswerCorrect(
    correctAnswerDetails: MultipleChoiceStatementCorrectAnswerDetails
  ): boolean {
    return (
      !!correctAnswerDetails &&
      this.optionId === correctAnswerDetails.correctOptionId
    );
  }
}
