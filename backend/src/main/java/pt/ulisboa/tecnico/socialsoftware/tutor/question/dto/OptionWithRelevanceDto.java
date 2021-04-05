package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance;

import java.io.Serializable;


import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.INVALID_SEQUENCE_FOR_OPTION;

public class OptionWithRelevanceDto implements Serializable {
    private Integer relevance;
    private Integer id;
    private Integer sequence;
    private boolean correct;
    private String content;

    public OptionWithRelevanceDto(){}

    public OptionWithRelevanceDto(OptionWithRelevance option){
        this.id = option.getId();
        this.relevance = option.getRelevance();
        this.sequence = option.getSequence();
        this.content = option.getContent();
        this.correct = option.isCorrect();
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public Integer getRelevance() { return this.relevance; }

    public Integer getId(){ return id; }

    public Integer getSequence() { return sequence; }

    public void setSequence(Integer sequence) {
        if (sequence == null || sequence < 0)
            throw new TutorException(INVALID_SEQUENCE_FOR_OPTION);

        this.sequence = sequence;
    }

    public Integer getRelevance(){ return relevance; }

    public Integer getId(){ return id; }

    public Integer getSequence(){ return sequence; }

    public void setSequence(Integer sequence){ this.sequence = sequence; }

    public boolean isCorrect(){
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "OptionWithRelevanceDto{" +
                "id=" + id +
                ", correct=" + correct +
                ", relevance=" + relevance +
                ", content='" + content + '\'' +
                '}';
    }
}
