package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

import java.util.List;
import java.util.stream.Collectors;

public class OpenAnswerStatementQuestionDetailsDto extends StatementQuestionDetailsDto {

    public OpenAnswerStatementQuestionDetailsDto(OpenAnswerQuestion question) {
    }

    @Override
    public String toString() {
        return "OpenAnswerStatementQuestionDetailsDto{" +
                '}';
    }
}