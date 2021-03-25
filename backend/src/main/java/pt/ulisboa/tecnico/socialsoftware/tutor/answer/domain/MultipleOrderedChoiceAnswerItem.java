package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleOrderedChoiceStatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Arrays;

@Entity
@DiscriminatorValue(Question.QuestionTypes.MULTIPLE_CHOICE_ORDER_QUESTION)
public class MultipleOrderedChoiceAnswerItem extends QuestionAnswerItem {

    private Integer optionId;

    public MultipleOrderedChoiceAnswerItem() {
    }

    public MultipleOrderedChoiceAnswerItem(String username, int quizId, StatementAnswerDto answer, MultipleOrderedChoiceStatementAnswerDetailsDto detailsDto) {
        super(username, quizId, answer);
        this.optionId = detailsDto.getOptionId();
    }

    @Override
    public String getAnswerRepresentation(QuestionDetails questionDetails) {
        return this.getOptionId() != null ? questionDetails.getAnswerRepresentation(Arrays.asList(optionId)) : "-";
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }
}
