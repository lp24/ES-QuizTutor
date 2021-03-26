package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.Updator;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.*;

@Entity
@DiscriminatorValue(Question.QuestionTypes.MULTIPLE_CHOICE_ORDER_QUESTION)
public class MultipleOrderedChoiceQuestion extends QuestionDetails {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionDetails", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<OptionWithRelevance> options = new ArrayList<>();

    public MultipleOrderedChoiceQuestion(){ super(); }

    public MultipleOrderedChoiceQuestion(Question question, MultipleOrderedChoiceQuestionDto questionDto) {
        super(question);
        setOptions(questionDto.getOptions());
    }

    public List<OptionWithRelevance> getOptions() {
        return options;
    }

    public void setOptions(List<OptionWithRelevanceDto> options) {
        if (options.stream().filter(OptionWithRelevanceDto::isCorrect).count() != 1) {
            throw new TutorException(ONE_CORRECT_OPTION_NEEDED);
        }

        int index = 0;
        for (OptionWithRelevanceDto optionDto : options) {
            if (optionDto.getId() == null) {
                optionDto.setSequence(index++);
                new OptionWithRelevance(optionDto).setQuestionDetails(this);
            } else {
                OptionWithRelevance option = getOptions()
                        .stream()
                        .filter(op -> op.getId().equals(optionDto.getId()))
                        .findAny()
                        .orElseThrow(() -> new TutorException(OPTION_NOT_FOUND, optionDto.getId()));

                option.setContent(optionDto.getContent());
                option.setCorrect(optionDto.isCorrect());
            }
        }
    }

    public void addOption(OptionWithRelevance option) {
        options.add(option);
    }

    public Integer getCorrectOptionId() {
        return this.getOptions().stream()
                .filter(OptionWithRelevance::isCorrect)
                .findAny()
                .map(OptionWithRelevance::getId)
                .orElse(null);
    }

    public void update(MultipleOrderedChoiceQuestionDto questionDetails) {
        setOptions(questionDetails.getOptions());
    }

    @Override
    public void update(Updator updator) {
        updator.update(this);
    }

    @Override
    public String getCorrectAnswerRepresentation() {
        return convertSequenceToLetter(this.getCorrectAnswer());
    }

    public ArrayList<String> getCorrectAnswersRepresentation() {
        ArrayList<String> corrects = new ArrayList<>();

        for(OptionWithRelevance option : this.getOptions() ){
            if (option.isCorrect() ){
                corrects.add(convertSequenceToLetter(option.getSequence()));
            }
        }
        return corrects;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitQuestionDetails(this);
    }

    public void visitOptions(Visitor visitor) {
        for (OptionWithRelevance option : this.getOptions()) {
            option.accept(visitor);
        }
    }

    @Override
    public CorrectAnswerDetailsDto getCorrectAnswerDetailsDto() {
        return new MultipleOrderedChoiceCorrectAnswerDto(this);
    }

    @Override
    public StatementQuestionDetailsDto getStatementQuestionDetailsDto() {
        return new MultipleOrderedChoiceStatementQuestionDetailsDto(this);
    }

    @Override
    public StatementAnswerDetailsDto getEmptyStatementAnswerDetailsDto() {
        return new MultipleChoiceStatementAnswerDetailsDto();
    }

    @Override
    public AnswerDetailsDto getEmptyAnswerDetailsDto() {
        return new MultipleOrderedChoiceAnswerDto();
    }

    @Override
    public QuestionDetailsDto getQuestionDetailsDto() {
        return new MultipleOrderedChoiceQuestionDto(this);
    }

    public Integer getCorrectAnswer() {
        return this.getOptions()
                .stream()
                .filter(OptionWithRelevance::isCorrect)
                .findAny().orElseThrow(() -> new TutorException(NO_CORRECT_OPTION))
                .getSequence();
    }
    /*public ArrayList<Integer> getCorrectAnswers() {
        List<OptionWithRelevance> options = new ArrayList<>();
        ArrayList<Integer> sequences = new ArrayList<>();

        options = this.getOptions().stream().filter(OptionWithRelevance::isCorrect).collect(Collectors.toList());

        if(options.isEmpty()){
            throw new TutorException(NO_CORRECT_OPTION);
            return null;
        }
        return sequences.stream().forEachOrdered(OptionWithRelevance::getRelevance).get;

    }*/

    @Override
    public void delete() {
        super.delete();
        for (OptionWithRelevance option : this.options) {
            option.remove();
        }
        this.options.clear();
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "options=" + options +
                '}';
    }

    public static String convertSequenceToLetter(Integer correctAnswer) {
        return correctAnswer != null ? Character.toString('A' + correctAnswer) : "-";
    }

    @Override
    public String getAnswerRepresentation(List<Integer> selectedIds) {
        var result = this.options
                .stream()
                .filter(x -> selectedIds.contains(x.getId()))
                .map(x -> convertSequenceToLetter(x.getSequence()))
                .collect(Collectors.joining("|"));
        return !result.isEmpty() ? result : "-";
    }
}
