package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

public class OpenAnswerCorrectAnswerDto extends CorrectAnswerDetailsDto {
    private String answerString;

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public OpenAnswerCorrectAnswerDto(OpenAnswerQuestion question) {
        setAnswerString(question.getCorrectAnswer());
    }

    @Override
    public String toString() {
        return "OpenAnswerCorrectAnswerDto{" +
                "correctAnswer=" + getAnswerString() +
                '}';
    }
}