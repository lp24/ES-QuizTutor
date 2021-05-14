package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Item;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.QUESTION_OPTION_MISMATCH;

@Entity
@DiscriminatorValue(Question.QuestionTypes.ITEM_COMBINATION_QUESTION)
public class ItemCombinationAnswer extends AnswerDetails {
    @ManyToOne
    private Item item;

    public ItemCombinationAnswer() {
        super();
    }

    public ItemCombinationAnswer(QuestionAnswer questionAnswer) {
        super(questionAnswer);
    }

    public ItemCombinationAnswer(QuestionAnswer questionAnswer, Item item){
        super(questionAnswer);
        this.setItem(item);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean isCorrect() {
        return true;
    }

    @Override
    public AnswerDetailsDto getAnswerDetailsDto() {
        return new ItemCombinationAnswerDto(this);
    }

    @Override
    public boolean isAnswered() {
        return this.getItem() != null;
    }

    @Override
    public String getAnswerRepresentation() {
        return "item";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAnswerDetails(this);
    }

    @Override
    public StatementAnswerDetailsDto getStatementAnswerDetailsDto() {
        return new ItemCombinationStatementAnswerDetailsDto(this);
    }

    public void remove() {
        this.setItem(null);
    }
}