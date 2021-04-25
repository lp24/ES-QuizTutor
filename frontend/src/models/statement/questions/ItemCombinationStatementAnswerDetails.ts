import StatementAnswerDetails from '@/models/statement/questions/StatementAnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class ItemCombinationStatementAnswerDetails extends StatementAnswerDetails {
  public connectedItems: string | null = null;

  constructor(jsonObj?: ItemCombinationStatementAnswerDetails) {
    super(QuestionTypes.ItemCombination);
    if (jsonObj) {

    }
  }

  isQuestionAnswered(): boolean {
    return false;
  }

  isAnswerCorrect(): boolean {
    //TODO
    return false;
  }
}