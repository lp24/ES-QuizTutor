package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionDto;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.OPTION_NOT_FOUND;

public class MultipleOrderedChoiceQuestion extends QuestionDetails {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionDetails", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<OptionWithRelevance> options = new ArrayList<>();

    public MultipleOrderedChoiceQuestion(Question question, MultipleOrderedChoiceQuestionDto questionDto) {
        super(question);
        setOptionsWithRelevance(questionDto.getOptions());
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


}
