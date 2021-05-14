package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

public class OpenAnswerCorrectAnswerDto extends CorrectAnswerDetailsDto {
    private String correctAnswer;

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public OpenAnswerCorrectAnswerDto(OpenAnswerQuestion question) {
        setCorrectAnswer(question.getCorrectAnswer());
    }

    @Override
    public String toString() {
        return "OpenAnswerCorrectAnswerDto{" +
                "correctAnswer=" + getCorrectAnswer() +
                '}';
    }
}