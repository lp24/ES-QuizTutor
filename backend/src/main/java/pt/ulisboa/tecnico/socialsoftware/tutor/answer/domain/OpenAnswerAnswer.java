package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Option;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.QUESTION_OPTION_MISMATCH;

@Entity
@DiscriminatorValue(Question.QuestionTypes.OPEN_ANSWER_QUESTION)
public class OpenAnswerAnswer extends AnswerDetails {
    private String answerString;
    public OpenAnswerAnswer() {
        super();
    }

    public OpenAnswerAnswer(QuestionAnswer questionAnswer) {
        super(questionAnswer);
    }
    public OpenAnswerAnswer(QuestionAnswer questionAnswer, String answer){
        super(questionAnswer);
        this.setAnswerString(answer);
    }

    private void setAnswerString(String answer) {
        this.answerString=answer;
    }

    public String getAnswerString(){
        return answerString;
    }

    @Override
    public boolean isCorrect() {
        return getAnswerString() != null;
        //FIXME: compare to right answer
    }

    @Override
    public AnswerDetailsDto getAnswerDetailsDto() {
        return new OpenAnswerAnswerDto(this);
    }

    @Override
    public boolean isAnswered() {
        return this.getAnswerString() != null;
    }

    @Override
    public String getAnswerRepresentation() {
        return this.getAnswerString() != null ? getAnswerString() : "-";
    }

    @Override
    public StatementAnswerDetailsDto getStatementAnswerDetailsDto() {
        return new OpenAnswerStatementAnswerDetailsDto(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAnswerDetails(this);
    }

    public void setAnswerString(OpenAnswerQuestion question) {
        setAnswerString(question.getCorrectAnswer());
    }
    public void remove() {
        answerString=null;
    }
}
