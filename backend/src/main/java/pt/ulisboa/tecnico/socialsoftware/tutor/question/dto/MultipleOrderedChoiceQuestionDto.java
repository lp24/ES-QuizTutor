package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleOrderedChoiceQuestionDto extends QuestionDetailsDto {
    private List<OptionWithRelevance> options = new ArrayList<>();
// TODO
    public MultipleOrderedChoiceQuestionDto(){}

    public MultipleOrderedChoiceQuestionDto(MultipleOrderedChoiceQuestion question) {
       this.options = question.getOptions().stream().map(OptionWithRelevance::new).collect(Collectors.toList());
    }

    public List<OptionWithRelevance> getOptions() {
        return options;
    }

    public void setOptions(List<OptionWithRelevance> options) {
        this.options = options;
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new MultipleOrderedChoiceQuestion(question, this);
    }

    @Override
    public void update(MultipleOrderedChoiceQuestion question) {
        question.update(this);
    }

    @Override
    public String toString() {
        return "MultipleOrderedChoiceQuestionDto{" +
                "options=" + options +
                '}';
    }
}
