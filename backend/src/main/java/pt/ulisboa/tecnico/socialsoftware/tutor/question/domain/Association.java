package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.DomainEntity;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.AssociationDto;

public class Association implements DomainEntity {
    private Integer itemOne;
    private Integer itemTwo;

    public Association(Integer itemOne, Integer itemTwo) {
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    public Association(AssociationDto association) {
        this.itemOne = association.getItemOne();
        this.itemTwo = association.getItemTwo();
    }

    public Integer getItemOne() {
        return itemOne;
    }

    public Integer getItemTwo() {
        return itemTwo;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAssociation(this);
    }

    @Override
    public String toString() {
        return "Association {" +
                "itemOne = " + itemOne +
                ", itemTwo = '" + itemTwo + '\'' +
                '}';
    }
}
