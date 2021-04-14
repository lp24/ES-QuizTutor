package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

public class OpenAnswerQuestionDto extends QuestionDetailsDto {

    private String _correctAnswer;

    public String getCorrectAnswer() {
        return _correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this._correctAnswer = correctAnswer;
    }

    public OpenAnswerQuestionDto() {}

    public OpenAnswerQuestionDto(OpenAnswerQuestion question) {
        setCorrectAnswer(question.getCorrectAnswer());
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new OpenAnswerQuestion(question, this);
    }

    @Override
    public void update(OpenAnswerQuestion question) {
        question.update(this);
    }
}
