package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemCombinationQuestionDto extends QuestionDetailsDto {
    private List<ItemDto> items = new ArrayList<>();

    public ItemCombinationQuestionDto() { }

    public ItemCombinationQuestionDto(ItemCombinationQuestion question) {
        this.items = question.getItems().stream().map(ItemDto::new).collect(Collectors.toList());
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new ItemCombinationQuestion(question, this);
    }

    @Override
    public String toString() {
        return "ItemCombinationQuestionDto{" +
                "items=" + items +
                '}';
    }

}
