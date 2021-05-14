import StatementAnswerDetails from '@/models/statement/questions/StatementAnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';
import MultipleOrderedChoiceStatementCorrectAnswerDetails from '@/models/statement/questions/MultipleOrderedChoiceStatementCorrectAnswerDetails';

export default class MultipleOrderedChoiceStatementAnswerDetails extends StatementAnswerDetails {
  public optionId: number | null = null; //TODO Doubt SHould this be an array instead???

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
    correctAnswerDetails: MultipleOrderedChoiceStatementCorrectAnswerDetails
  ): boolean {
    return (
      !!correctAnswerDetails &&
      this.optionId === correctAnswerDetails.correctOptionId
    );
  }
}
