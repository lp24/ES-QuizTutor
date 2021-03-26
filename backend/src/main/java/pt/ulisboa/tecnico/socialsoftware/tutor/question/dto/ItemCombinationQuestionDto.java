package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Item;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import java.util.ArrayList;
import java.util.List;

public class ItemCombinationQuestionDto extends QuestionDetailsDto {
    private ArrayList<ItemDto> items = new ArrayList<>();

    public void setItems(List<ItemDto> items) {
        this.items = (ArrayList<ItemDto>) items;
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
