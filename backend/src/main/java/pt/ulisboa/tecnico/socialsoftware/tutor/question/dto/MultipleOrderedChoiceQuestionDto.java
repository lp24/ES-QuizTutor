package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleOrderedChoiceQuestionDto extends MultipleChoiceQuestionDto {
    private List<OptionDto> options = new ArrayList<>();

    public MultipleOrderedChoiceQuestionDto() {
    }

    public MultipleOrderedChoiceQuestionDto(MultipleOrderedChoiceQuestionDto question) {
        this.options = question.getOptions().stream().map(OptionDto::new).collect(Collectors.toList());

    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new MultipleOrderedChoiceQuestionDto(question);
    }

    @Override
    public String toString() {
        return "MultipleChoiceOrderQuestionDtoDto{" +
                "options=" + options +
                '}';
    }

}
