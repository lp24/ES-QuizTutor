package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Item;

import java.io.Serializable;
import javax.persistence.*;

public class AssociationDto implements Serializable {

    private Item itemOne;
    private Item itemTwo;

    public AssociationDto() {}

    public AssociationDto(Item itemOne, Item itemTwo) {
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    public Item getItemOne() {return itemOne;}

    public Item getItemTwo() {return itemTwo;}

    @Override
    public String toString() {
        return "AssociationDto {" +
                "itemOne = " + itemOne +
                ", itemTwo = '" + itemTwo + '\'' +
                '}';
    }
}