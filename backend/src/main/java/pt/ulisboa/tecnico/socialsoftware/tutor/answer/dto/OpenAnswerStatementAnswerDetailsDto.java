package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

import javax.persistence.Transient;

public class OpenAnswerStatementAnswerDetailsDto extends StatementAnswerDetailsDto {
    private String studentAnswer;

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public OpenAnswerStatementAnswerDetailsDto() {
    }

    public OpenAnswerStatementAnswerDetailsDto(OpenAnswerAnswer questionAnswer) {
        if (questionAnswer.getStudentAnswer() != null) {
            setStudentAnswer(questionAnswer.getStudentAnswer());
        }
    }

    @Transient
    private OpenAnswerAnswer createdOpenAnswerAnswer;

    @Override
    public AnswerDetails getAnswerDetails(QuestionAnswer questionAnswer) {
        createdOpenAnswerAnswer = new OpenAnswerAnswer(questionAnswer);
        questionAnswer.getQuestion().getQuestionDetails().update(this);
        return createdOpenAnswerAnswer;
    }

    @Override
    public boolean emptyAnswer() {
        return getStudentAnswer() == null;
    }

    @Override
    public QuestionAnswerItem getQuestionAnswerItem(String username, int quizId, StatementAnswerDto statementAnswerDto) {
        return new OpenAnswerAnswerItem(username, quizId, statementAnswerDto, this);
    }

    @Override
    public void update(OpenAnswerQuestion question) {
        createdOpenAnswerAnswer.setStudentAnswer(studentAnswer);
    }

    @Override
    public String toString() {
        return "OpenAnswerStatementAnswerDto{" +
                "answer=" + getStudentAnswer() +
                '}';
    }
}
