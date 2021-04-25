//TO DO
import StatementQuestionDetails from '@/models/statement/questions/StatementQuestionDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class ItemCombinationStatementQuestionDetails extends StatementQuestionDetails {
  public connectedItems: string | null = null;

  constructor(jsonObj?: ItemCombinationStatementQuestionDetails) {
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