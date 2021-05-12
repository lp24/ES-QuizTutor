package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion;

import java.util.ArrayList;
import java.util.List;

public class MultipleOrderedChoiceCorrectAnswerDto extends CorrectAnswerDetailsDto {
    private Integer correctOptionId;
    private List<Integer> correctOptionsId = new ArrayList<>(); //TODO

    public MultipleOrderedChoiceCorrectAnswerDto(MultipleOrderedChoiceQuestion question) {
        this.correctOptionId = question.getCorrectOptionId();
        this.correctOptionsId = question.getCorrectOptionsId(); //TODO
    }

    public Integer getCorrectOptionId() {
        return correctOptionId;
    }

    public void setCorrectOptionId(Integer correctOptionId) {
        this.correctOptionId = correctOptionId;
    }


    public List<Integer> getCorrectOptionsId() {
        return correctOptionsId;
    } //TODO

    public void setCorrectOptionsId(List<Integer> correctOptionsId) {
        this.correctOptionsId = correctOptionsId;
    } //TODO

    @Override
    public String toString() {
        return "MultipleOrderedChoiceCorrectAnswerDto{" +
                "correctOptionId=" + correctOptionId +
                '}';
    }
}