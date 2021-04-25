import AnswerDetails from '@/models/management/questions/AnswerDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';
import ItemCombinationQuestionDetails from '@/models/management/questions/ItemCombinationQuestionDetails';

export default class ItemCombinationAnswerType extends AnswerDetails {
  associations: Association[] = [];

  constructor(jsonObj?: ItemCombinationAnswerType) {
      super(QuestionTypes.ItemCombination);
      if (jsonObj) {
        this.associations = jsonObj.associations.map(
          (association: Association) => new Association(association)
        );
      }
    }

  isCorrect(questionDetails: ItemCombinationQuestionDetails): boolean {
    //TODO
    return false;
  }

  answerRepresentation(questionDetails: ItemCombinationQuestionDetails): string {
    //TODO
    return this.associations;
  }
}