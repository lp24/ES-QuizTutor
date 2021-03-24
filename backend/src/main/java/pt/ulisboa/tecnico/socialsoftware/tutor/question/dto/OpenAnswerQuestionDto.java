package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.CodeOrderQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

public class OpenAnswerQuestionDto extends QuestionDetailsDto {

    public OpenAnswerQuestionDto() {}

    public OpenAnswerQuestionDto(OpenAnswerQuestion question) {}

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new OpenAnswerQuestion(question,this);
    }

    @Override
    public void update(OpenAnswerQuestion question) {
        question.update(this);
    }
}
