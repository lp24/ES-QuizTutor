import StatementCorrectAnswerDetails from '@/models/statement/questions/StatementCorrectAnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class MultipleOrderedChoiceStatementCorrectAnswerDetails extends StatementCorrectAnswerDetails {
  public correctOptionId: number | null = null;
  //TODO public correctRelevance: number | null = null;

  constructor(jsonObj?: MultipleOrderedChoiceStatementCorrectAnswerDetails) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      this.correctOptionId = jsonObj.correctOptionId;
    }
  }
}
