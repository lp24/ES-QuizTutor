package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.CorrectAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OpenAnswerQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDetailsDto;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(Question.QuestionTypes.OPEN_ANSWER_QUESTION)
public class OpenAnswerQuestion extends QuestionDetails {


    private String _correctAnswer;

    public String getCorrectAnswer() {
        return _correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        _correctAnswer = correctAnswer;
    }

    public OpenAnswerQuestion(Question question, OpenAnswerQuestionDto questionDto) {
        super(question);
        setCorrectAnswer(questionDto.getCorrectAnswer());
    }

    public OpenAnswerQuestion( ) {super(); }

    public OpenAnswerQuestion(Question question){
            super(question);
    }

    @Override
    public void update(Updator updator) {
        updator.update(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitQuestionDetails(this);
    }


    @Override
    public QuestionDetailsDto getQuestionDetailsDto() {
        return new OpenAnswerQuestionDto(this);
    }


    public CorrectAnswerDetailsDto getCorrectAnswerDetailsDto(){return null;}

    public StatementQuestionDetailsDto getStatementQuestionDetailsDto(){return null;}

    public StatementAnswerDetailsDto getEmptyStatementAnswerDetailsDto(){return null;}

    public AnswerDetailsDto getEmptyAnswerDetailsDto(){return null;}

    public String getCorrectAnswerRepresentation(){
        String correctAnswer = getCorrectAnswer();
        return correctAnswer != null && correctAnswer != "" ? correctAnswer : "-";
    }

    public String getAnswerRepresentation(List<Integer> selectedIds){return null;}

    public void update(OpenAnswerQuestionDto questionDetails) {
        setCorrectAnswer(questionDetails.getCorrectAnswer());
    }

}
