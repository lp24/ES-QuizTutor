
package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.AnswerDetails;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.ItemCombinationAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuestionAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.ItemCombinationAnswerItem;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Item;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuestionAnswerItem;

import javax.persistence.Transient;

public class ItemCombinationStatementAnswerDetailsDto extends StatementAnswerDetailsDto {

    private Integer itemId;

    public ItemCombinationStatementAnswerDetailsDto() {
    }

    public ItemCombinationStatementAnswerDetailsDto(ItemCombinationAnswer questionAnswer) {
        if (questionAnswer.getItem() != null) {
            this.itemId = questionAnswer.getItem().getId();
        }
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Transient
    private ItemCombinationAnswer createdItemCombinationAnswer;

    @Override
    public AnswerDetails getAnswerDetails(QuestionAnswer questionAnswer) {
        createdItemCombinationAnswer = new ItemCombinationAnswer(questionAnswer);
        questionAnswer.getQuestion().getQuestionDetails().update(this);
        return createdItemCombinationAnswer;
    }

    @Override
    public boolean emptyAnswer() {
        return itemId == null;
    }

    @Override
    public QuestionAnswerItem getQuestionAnswerItem(String username, int quizId, StatementAnswerDto statementAnswerDto) {
        return new ItemCombinationAnswerItem(username, quizId, statementAnswerDto, this);
    }

    @Override
    public void update(ItemCombinationQuestion question) {
        return;
    }

    @Override
    public String toString() {
        return "ItemCombinationStatementAnswerDto{" +
                "itemId=" + itemId +
                '}';
    }
}