package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleChoiceOrderQuestionDto extends MultipleChoiceQuestionDto {
    private List<OptionDto> options = new ArrayList<>();

    public MultipleChoiceOrderQuestionDto() {
    }

    public MultipleChoiceOrderQuestionDto(MultipleChoiceOrderQuestionDto question) {
        this.options = question.getOptions().stream().map(OptionDto::new).collect(Collectors.toList());
    }

    public MultipleChoiceOrderQuestionDto(Question question, MultipleChoiceOrderQuestionDto multipleChoiceOrderQuestionDto) {

    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new MultipleChoiceOrderQuestionDto(question, this);
    }

    @Override
    public void update(MultipleChoiceOrderQuestionDto question) {
        question.update(this);
    }

    @Override
    public String toString() {
        return "MultipleChoiceOrderQuestionDtoDto{" +
                "options=" + options +
                '}';
    }

}
