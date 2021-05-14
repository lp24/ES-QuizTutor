
package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.ItemCombinationAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto;

public class ItemCombinationAnswerDto extends AnswerDetailsDto {
    private ItemDto item;

    public ItemCombinationAnswerDto() {
    }

    public ItemCombinationAnswerDto(ItemCombinationAnswer itemcombinationanswer) {
        this.item = new ItemDto(itemcombinationanswer.getItem());
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }
}