package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.MultipleOrderedChoiceAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto;

public class MultipleOrderedChoiceAnswerDto extends AnswerDetailsDto {
    private OptionWithRelevanceDto option;

    public MultipleOrderedChoiceAnswerDto() {
    }

    public MultipleOrderedChoiceAnswerDto(MultipleOrderedChoiceAnswer answer) {
        if (answer.getOption() != null)
            this.option = new OptionWithRelevanceDto(answer.getOption());
    }

    public OptionWithRelevanceDto getOption() {
        return option;
    }

    public void setOption(OptionWithRelevanceDto option) {
        this.option = option;
    }
}
