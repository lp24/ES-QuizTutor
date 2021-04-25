import StatementAnswerDetails from '@/models/statement/questions/StatementAnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class ItemCombinationStatementAnswerDetails extends StatementAnswerDetails {
  public connectedItems: string | null = null;

  constructor(jsonObj?: ItemCombinationStatementAnswerDetails) {
    super(QuestionTypes.ItemCombination);
    if (jsonObj) {
      this.connectedItems = jsonObj.connectedItems || [];
    }
  }

  isQuestionAnswered(): boolean {
    return this.selectedAnswer != null && this.selectedAnswer.length > 0;
  }

  isAnswerCorrect(): boolean {
    //TODO
    return false;
  }
}