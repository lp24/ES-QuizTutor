package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion;

public class MultipleOrderedChoiceCorrectAnswerDto extends CorrectAnswerDetailsDto {
    private Integer correctOptionId;

    public MultipleOrderedChoiceCorrectAnswerDto(MultipleOrderedChoiceQuestion question) {
        this.correctOptionId = question.getCorrectOptionId();
    }

    public Integer getCorrectOptionId() {
        return correctOptionId;
    }

    public void setCorrectOptionId(Integer correctOptionId) {
        this.correctOptionId = correctOptionId;
    }

    @Override
    public String toString() {
        return "MultipleOrderedChoiceCorrectAnswerDto{" +
                "correctOptionId=" + correctOptionId +
                '}';
    }
}