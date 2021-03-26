package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.CorrectAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDetailsDto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(Question.QuestionTypes.ITEM_COMBINATION_QUESTION)
public class ItemCombinationQuestion extends QuestionDetails {
    private ArrayList<Item> items = new ArrayList<>();

    public ItemCombinationQuestion() {
    }

    public ItemCombinationQuestion(Question question, ItemCombinationQuestionDto dto) {
        super(question);
        update(dto);
    }

    public void setItems(List<Item> items) {
        this.items = (ArrayList<Item>) items;
    }

    public void addItem(Item item) {
        items.add(item);
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

    public String getCorrectAnswerRepresentation() {
        return null;
    }

    public String getAnswerRepresentation(List<Integer> selectedIds) {
        return null;
    }

    @Override
    public void update(Updator updator) {
        updator.update(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitQuestionDetails(this);
    }
}
