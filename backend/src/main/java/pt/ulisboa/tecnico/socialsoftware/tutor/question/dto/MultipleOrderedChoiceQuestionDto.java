package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleOrderedChoiceQuestionDto extends QuestionDetailsDto {
    private List<OptionWithRelevanceDto> options = new ArrayList<>();

    public MultipleOrderedChoiceQuestionDto() {
    }

    public MultipleOrderedChoiceQuestionDto(MultipleOrderedChoiceQuestion question) {
       this.options = question.getOptions().stream().map(OptionWithRelevanceDto::new).collect(Collectors.toList());
    }


    public List<OptionWithRelevanceDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionWithRelevanceDto> options) {
        this.options = options;
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new MultipleOrderedChoiceQuestion(question, this);
    }

    @Override
    public String toString() {
        return "MultipleChoiceOrderQuestionDtoDto{" +
                "options=" + options +
                '}';
    }

}
