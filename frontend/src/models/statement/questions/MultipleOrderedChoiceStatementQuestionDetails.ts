import StatementQuestionDetails from '@/models/statement/questions/StatementQuestionDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';
import StatementOptionWithRelevance from '@/models/statement/StatementOptionWithRelevance';
import { _ } from 'vue-underscore';

export default class MultipleOrderedChoiceStatementQuestionDetails extends StatementQuestionDetails {
  options: StatementOptionWithRelevance[] = [];

  constructor(jsonObj?: MultipleOrderedChoiceStatementQuestionDetails) {
    super(QuestionTypes.MultipleOrderedChoice);
    if (jsonObj) {
      if (jsonObj.options) {
        this.options = _.shuffle(
          jsonObj.options.map(
            (option: StatementOptionWithRelevance) => new StatementOptionWithRelevance(option)
          )
        );
      }
    }
  }
}
