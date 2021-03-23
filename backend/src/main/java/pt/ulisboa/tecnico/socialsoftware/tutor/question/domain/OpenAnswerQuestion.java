package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.CorrectAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OpenAnswerQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDetailsDto;

import javax.validation.constraints.Null;
import java.util.List;

public class OpenAnswerQuestion extends QuestionDetails {
    public OpenAnswerQuestion(/*empty*/) {}

    public OpenAnswerQuestion(Question question, OpenAnswerQuestionDto questionDto) {
            super(question);
    }

    public CorrectAnswerDetailsDto getCorrectAnswerDetailsDto(){return null;}

    public StatementQuestionDetailsDto getStatementQuestionDetailsDto(){return null;}

    public StatementAnswerDetailsDto getEmptyStatementAnswerDetailsDto(){return null;}

    public AnswerDetailsDto getEmptyAnswerDetailsDto(){return null;}

    public QuestionDetailsDto getQuestionDetailsDto(){return null;}

    public void update(Updator updator){}

    public String getCorrectAnswerRepresentation(){return null;}

    public String getAnswerRepresentation(List<Integer> selectedIds){return null;}

    @Override
    public void accept(Visitor visitor) {
    }
}
