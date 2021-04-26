package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import java.io.Serializable;

public class AssociationDto implements Serializable {

    private Integer itemOne;
    private Integer itemTwo;

    public AssociationDto() {}

    public AssociationDto(Integer itemOne, Integer itemTwo) {
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    public Integer getItemOne() {return itemOne;}

    public Integer getItemTwo() {return itemTwo;}

    @Override
    public String toString() {
        return "AssociationDto {" +
                "itemOne = " + itemOne +
                ", itemTwo = '" + itemTwo + '\'' +
                '}';
    }
}