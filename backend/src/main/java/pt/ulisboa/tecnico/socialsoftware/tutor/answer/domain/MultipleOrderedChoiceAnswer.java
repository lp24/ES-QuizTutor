package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleOrderedChoiceStatementAnswerDetailsDto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.QUESTION_OPTION_MISMATCH;

@Entity
@DiscriminatorValue(Question.QuestionTypes.MULTIPLE_CHOICE_QUESTION)
public class MultipleOrderedChoiceAnswer extends AnswerDetails {
    @ManyToOne
    @JoinColumn(name = "option_id")
    private OptionWithRelevance option;

    public MultipleOrderedChoiceAnswer() {
        super();
    }

    public MultipleOrderedChoiceAnswer(QuestionAnswer questionAnswer){
        super(questionAnswer);
    }

    public MultipleOrderedChoiceAnswer(QuestionAnswer questionAnswer, OptionWithRelevance option){
        super(questionAnswer);
        this.setOption(option);
    }

    public OptionWithRelevance getOption() {
        return option;
    }

    public void setOption(OptionWithRelevance option) {
        this.option = option;

        if (option != null)
            option.addQuestionAnswer(this);
    }

    public void setOption(MultipleOrderedChoiceQuestion question, MultipleOrderedChoiceStatementAnswerDetailsDto multipleChoiceStatementAnswerDetailsDto) {
        if (multipleChoiceStatementAnswerDetailsDto.getOptionId() != null) {
            OptionWithRelevance option = question.getOptions().stream()
                    .filter(option1 -> option1.getId().equals(multipleChoiceStatementAnswerDetailsDto.getOptionId()))
                    .findAny()
                    .orElseThrow(() -> new TutorException(QUESTION_OPTION_MISMATCH, multipleChoiceStatementAnswerDetailsDto.getOptionId()));

            if (this.getOption() != null) {
                this.getOption().getQuestionAnswers().remove(this);
            }

            this.setOption(option);
        } else {
            this.setOption(null);
        }
    }

    @Override
    public boolean isCorrect() {
        return getOption() != null && getOption().isCorrect();
    }


    public void remove() {
        if (option != null) {
            option.getQuestionAnswers().remove(this);
            option = null;
        }
    }

    @Override
    public AnswerDetailsDto getAnswerDetailsDto() {
        return new MultipleOrderedChoiceAnswerDto(this);
    }

    @Override
    public boolean isAnswered() {
        return this.getOption() != null;
    }

    @Override
    public String getAnswerRepresentation() {
        return this.getOption() != null ? MultipleChoiceQuestion.convertSequenceToLetter(this.getOption().getSequence()) : "-";
    }

    @Override
    public StatementAnswerDetailsDto getStatementAnswerDetailsDto() {
        return new MultipleOrderedChoiceStatementAnswerDetailsDto(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAnswerDetails(this);
    }
}
