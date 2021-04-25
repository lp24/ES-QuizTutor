package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.CorrectAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDetailsDto;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(Question.QuestionTypes.ITEM_COMBINATION_QUESTION)
public class ItemCombinationQuestion extends QuestionDetails {

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "questionDetails", fetch = FetchType.EAGER)
    private final ArrayList<Item> items = new ArrayList<>();

    public ItemCombinationQuestion() {
    }

    public ItemCombinationQuestion(Question question, ItemCombinationQuestionDto dto) {
        super(question);
        update(dto);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        if (items.size() < 2) {
            throw new TutorException(NOT_ENOUGH_ITEMS);
        }

        var exception = true;
        for (ItemDto itemDto : items) {
            if (itemDto.getConnections().isEmpty()) {
                exception = false;
                break;
            }
        }

        if (exception) {
            throw new TutorException(NOT_ENOUGH_CONNECTIONS);
        }

        for (ItemDto itemDto : items) {
            if (itemDto.getId() == null) {
                new Item(itemDto).setQuestionDetails(this);
            } else {
                Item item = getItems()
                        .stream()
                        .filter(op -> op.getId().equals(itemDto.getId()))
                        .findAny()
                        .orElseThrow(() -> new TutorException(ITEM_NOT_FOUND, itemDto.getId()));

                item.setContent(itemDto.getContent());

                for (ItemDto possibleConnection : items) {
                    if (possibleConnection.getId() != null && itemDto.checkConnection(possibleConnection.getId())) {
                        item.addConnection(possibleConnection.getId());
                    }
                }
            }
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Integer getCorrectItemId() {
        return this.getItems().stream()
                .findAny()
                .map(Item::getId)
                .orElse(null);
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

    public String getCorrectAnswerRepresentation() {
        return null;
    }

    @Override
    public String getAnswerRepresentation(List<Integer> selectedIds) { return null; }

    @Override
    public QuestionDetailsDto getQuestionDetailsDto() {
        return new ItemCombinationQuestionDto(this);
    }

    @Override
    public void delete() {
        super.delete();
        for (Item item : this.items) {
            item.remove();
        }
        this.items.clear();
    }

    public void visitItems(Visitor visitor) {
        for (Item item : this.getItems()) {
            item.accept(visitor);
        }
    }

    public void update(ItemCombinationQuestionDto questionDetails) {
        setItems(questionDetails.getItems());
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
