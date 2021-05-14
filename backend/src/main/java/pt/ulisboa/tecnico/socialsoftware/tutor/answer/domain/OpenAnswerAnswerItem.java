package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.OpenAnswerStatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Arrays;

@Entity
@DiscriminatorValue(Question.QuestionTypes.OPEN_ANSWER_QUESTION)
public class OpenAnswerAnswerItem extends QuestionAnswerItem {

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    private String studentAnswer;

    public OpenAnswerAnswerItem() {
    }

    public OpenAnswerAnswerItem(String username, int quizId, StatementAnswerDto answer, OpenAnswerStatementAnswerDetailsDto detailsDto) {
        super(username, quizId, answer);
        this.setStudentAnswer(detailsDto.getStudentAnswer());
    }

    @Override
    public String getAnswerRepresentation(QuestionDetails questionDetails) {
        return this.getStudentAnswer() != null ? questionDetails.getAnswerRepresentation(Arrays.asList()) : "-";
    }

}
