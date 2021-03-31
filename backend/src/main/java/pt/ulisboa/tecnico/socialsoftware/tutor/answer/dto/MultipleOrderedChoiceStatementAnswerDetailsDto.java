package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion;

public class MultipleOrderedChoiceStatementAnswerDetailsDto extends StatementAnswerDetailsDto {
    private Integer optionId;

    public MultipleOrderedChoiceStatementAnswerDetailsDto() {
    }

    public MultipleOrderedChoiceStatementAnswerDetailsDto(MultipleChoiceAnswer questionAnswer) {
        if (questionAnswer.getOption() != null) {
            this.optionId = questionAnswer.getOption().getId();
        }
    }

    public MultipleOrderedChoiceStatementAnswerDetailsDto(MultipleOrderedChoiceAnswer questionAnswer) {
        if (questionAnswer.getOption() != null) {
            this.optionId = questionAnswer.getOption().getId();
        }
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    private MultipleOrderedChoiceAnswer createdMultipleOrderedChoiceAnswer;

    @Override
    public AnswerDetails getAnswerDetails(QuestionAnswer questionAnswer) {
        createdMultipleOrderedChoiceAnswer = new MultipleOrderedChoiceAnswer(questionAnswer);
        questionAnswer.getQuestion().getQuestionDetails().update(this);
        return createdMultipleOrderedChoiceAnswer;
    }

    @Override
    public boolean emptyAnswer() {
        return optionId == null;
    }

    @Override
    public QuestionAnswerItem getQuestionAnswerItem(String username, int quizId, StatementAnswerDto statementAnswerDto) {
        return new MultipleOrderedChoiceAnswerItem(username, quizId, statementAnswerDto, this);
    }

    @Override
    public void update(MultipleOrderedChoiceQuestion question) {
        createdMultipleOrderedChoiceAnswer.setOption(question, this);
    }

    @Override
    public String toString() {
        return "MultipleOrderedChoiceStatementAnswerDto{" +
                "optionId=" + optionId +
                '}';
    }
}
