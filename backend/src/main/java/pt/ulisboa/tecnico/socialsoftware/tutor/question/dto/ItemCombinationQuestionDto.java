package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import java.util.ArrayList;

public class ItemCombinationQuestionDto extends QuestionDetailsDto {
    private ArrayList<ItemDto> _items = new ArrayList<>();

    public ItemCombinationQuestionDto() { }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new ItemCombinationQuestion(question, this);
    }

    public void setItems(ArrayList<ItemDto> items) {
        this._items = items;
    }
}
