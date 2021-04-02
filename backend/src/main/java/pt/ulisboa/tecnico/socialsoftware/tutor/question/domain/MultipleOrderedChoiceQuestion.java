package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.CorrectAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDetailsDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.OPTION_NOT_FOUND;
// Need to add the entity annotation
@Entity
@DiscriminatorValue(Question.QuestionTypes.MULTIPLE_ORDERED_CHOICE_QUESTION)
public class MultipleOrderedChoiceQuestion extends QuestionDetails {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionDetails", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<OptionWithRelevance> options = new ArrayList<>();

    public MultipleOrderedChoiceQuestion(Question question, MultipleOrderedChoiceQuestionDto questionDto) {
        super(question);
        setOptionsWithRelevance(questionDto.getOptions());
    }

    // Mandatory do define the empty constructor
    public MultipleOrderedChoiceQuestion() {
    }


    public void orderOption(Option option) {
        // options.Order(option);
    }

    public List<OptionWithRelevance> getOptionsWithRelevance() {
        return options;
    }

    public void setOptionsWithRelevance(List<OptionDto> options) {

        int index = 0;
        for (OptionDto optionDto : options) {
            if (optionDto.getId() == null) {
                optionDto.setSequence(index++);
                new OptionWithRelevance(optionDto).setQuestionDetails(this);
            } else {
                OptionWithRelevance option = getOptionsWithRelevance()
                        .stream()
                        .filter(op -> op.getId().equals(optionDto.getId()))
                        .findAny()
                        .orElseThrow(() -> new TutorException(OPTION_NOT_FOUND, optionDto.getId()));

                option.setContent(optionDto.getContent());
                option.setCorrect(optionDto.isCorrect());
            }
        }
    }


    // You need to add all these methods because you are extending an abstract class
    @Override
    public CorrectAnswerDetailsDto getCorrectAnswerDetailsDto() {
        return null;
    }

    @Override
    public StatementQuestionDetailsDto getStatementQuestionDetailsDto() {
        return null;
    }

    @Override
    public StatementAnswerDetailsDto getEmptyStatementAnswerDetailsDto() {
        return null;
    }

    @Override
    public AnswerDetailsDto getEmptyAnswerDetailsDto() {
        return null;
    }

    @Override
    public QuestionDetailsDto getQuestionDetailsDto() {
        return null;
    }

    @Override
    public void update(Updator updator) {

    }

    @Override
    public String getCorrectAnswerRepresentation() {
        return null;
    }

    @Override
    public String getAnswerRepresentation(List<Integer> selectedIds) {
        return null;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
