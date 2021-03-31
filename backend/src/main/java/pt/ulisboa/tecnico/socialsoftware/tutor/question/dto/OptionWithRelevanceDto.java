package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance;

import java.io.Serializable;

public class OptionWithRelevanceDto implements Serializable {
    private Integer relevance;
    private Integer id;
    private Integer sequence;
    private boolean correct;
    private String content;

    public OptionWithRelevanceDto(){}
    public OptionWithRelevanceDto(OptionWithRelevance option){
        this.relevance = option.getRelevance();
        this.id = option.getId();
        this.content = option.getContent();
        this.correct = option.isCorrect();
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public int getRelevance(){ return relevance; }

    public Integer getId(){ return id; }

    public Integer getSequence(){ return sequence; }

    public void setSequence(Integer sequence){ this.sequence = sequence; }

    public boolean isCorrect(){
        return correct;
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

    public String getContent() {
        return content;
    }
}
