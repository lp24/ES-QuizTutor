package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Item;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import java.util.ArrayList;
import java.util.List;

public class ItemCombinationQuestionDto extends QuestionDetailsDto {
    private ArrayList<Item> items = new ArrayList<>();

    public void setItems(List<Item> items) {
        this.items = (ArrayList<Item>) items;
    }

    public List<Item> getItems() {
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
