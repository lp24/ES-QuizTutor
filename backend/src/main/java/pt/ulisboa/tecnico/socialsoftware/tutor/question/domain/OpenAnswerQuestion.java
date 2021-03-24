package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.CorrectAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.CodeOrderQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OpenAnswerQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDetailsDto;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(Question.QuestionTypes.OPEN_ANSWER_QUESTION)
public class OpenAnswerQuestion extends QuestionDetails {

    public OpenAnswerQuestion( ) {super(); }

    public OpenAnswerQuestion(Question question, OpenAnswerQuestionDto openAnswerQuestionDto) {
            super(question);
            update(openAnswerQuestionDto);
    }

    public CorrectAnswerDetailsDto getCorrectAnswerDetailsDto(){return null;}

    public StatementQuestionDetailsDto getStatementQuestionDetailsDto(){return null;}

    public StatementAnswerDetailsDto getEmptyStatementAnswerDetailsDto(){return null;}

    public AnswerDetailsDto getEmptyAnswerDetailsDto(){return null;}

    public QuestionDetailsDto getQuestionDetailsDto(){return null;}

    public void update(Updator updator){}

    public String getCorrectAnswerRepresentation(){return null;}

    public String getAnswerRepresentation(List<Integer> selectedIds){return null;}

    public void update(OpenAnswerQuestionDto questionDetails) {
    }

    @Override
    public void accept(Visitor visitor) {
    }
}
