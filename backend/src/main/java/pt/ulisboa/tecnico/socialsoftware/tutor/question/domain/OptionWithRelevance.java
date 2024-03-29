package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.MultipleOrderedChoiceAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.DomainEntity;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.INVALID_CONTENT_FOR_OPTION;
import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.INVALID_SEQUENCE_FOR_OPTION;

@Entity
@Table(name = "options_with_relevance")
public class OptionWithRelevance implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer relevance=0;

    @Column(nullable = false)
    private Integer sequence;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean correct;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    @JoinColumn(name = "questions_details_id")
    private MultipleOrderedChoiceQuestion questionDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "option", fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<MultipleOrderedChoiceAnswer> questionAnswers = new HashSet<>();

    public OptionWithRelevance() {
    }

    public OptionWithRelevance(OptionWithRelevanceDto option) {
        setSequence(option.getSequence());
        setContent(option.getContent());
        setCorrect(option.isCorrect());
        setRelevance(option.getRelevance());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitOptionWithRelevance(this);
    }

    public Integer getId() {
        return id;
    }

    public Integer getRelevance() { return relevance; }

    public void setRelevance(Integer relevance) { this.relevance = relevance; }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        if (sequence == null || sequence < 0)
            throw new TutorException(INVALID_SEQUENCE_FOR_OPTION);

        this.sequence = sequence;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.isBlank())
            throw new TutorException(INVALID_CONTENT_FOR_OPTION);

        this.content = content;
    }

    public MultipleOrderedChoiceQuestion getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestionDetails(MultipleOrderedChoiceQuestion question) {
        this.questionDetails = question;
        question.addOption(this);
    }

    public Set<MultipleOrderedChoiceAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void addQuestionAnswer(MultipleOrderedChoiceAnswer questionAnswer) {
        questionAnswers.add(questionAnswer);
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", sequence=" + sequence +
                ", correct=" + correct +
                ", relevance=" + relevance +
                ", content='" + content + '\'' +
                ", question=" + questionDetails.getId() +
                ", questionAnswers=" + questionAnswers +
                '}';
    }

    public void remove() {
        this.questionDetails = null;
        this.questionAnswers.clear();
    }
}

