package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.CorrectAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDetailsDto;

import java.util.List;

public class ItemCombinationQuestion extends QuestionDetails {
    public ItemCombinationQuestion() {
    }

    public ItemCombinationQuestion(Question question, ItemCombinationQuestionDto dto) {
        super(question);
    }

    public CorrectAnswerDetailsDto getCorrectAnswerDetailsDto() {
        return null;
    }

    public StatementQuestionDetailsDto getStatementQuestionDetailsDto() {
        return null;
    }

    public StatementAnswerDetailsDto getEmptyStatementAnswerDetailsDto() {
        return null;
    }

    public AnswerDetailsDto getEmptyAnswerDetailsDto() {
        return null;
    }

    public QuestionDetailsDto getQuestionDetailsDto() {
        return null;
    }

    public void update(Updator updator) { }

    public String getCorrectAnswerRepresentation() {
        return null;
    }

    public String getAnswerRepresentation(List<Integer> selectedIds) {
        return null;
    }

    @Override
    public void accept(Visitor visitor) { }
}
