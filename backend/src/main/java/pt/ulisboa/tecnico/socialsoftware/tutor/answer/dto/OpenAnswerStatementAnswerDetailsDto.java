package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

import javax.persistence.Transient;

public class OpenAnswerStatementAnswerDetailsDto extends StatementAnswerDetailsDto {
    private String answerString;

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public OpenAnswerStatementAnswerDetailsDto() {
    }

    public OpenAnswerStatementAnswerDetailsDto(OpenAnswerAnswer questionAnswer) {
        if (questionAnswer.getAnswerString() != null) {
            setAnswerString(questionAnswer.getAnswerString());
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
        return getAnswerString() == null;
    }

    @Override
    public QuestionAnswerItem getQuestionAnswerItem(String username, int quizId, StatementAnswerDto statementAnswerDto) {
        return new OpenAnswerAnswerItem(username, quizId, statementAnswerDto, this);
    }

    @Override
    public void update(OpenAnswerQuestion question) {
        createdOpenAnswerAnswer.setAnswerString(question);
    }

    @Override
    public String toString() {
        return "OpenAnswerStatementAnswerDto{" +
                "answer=" + getAnswerString() +
                '}';
    }
}
