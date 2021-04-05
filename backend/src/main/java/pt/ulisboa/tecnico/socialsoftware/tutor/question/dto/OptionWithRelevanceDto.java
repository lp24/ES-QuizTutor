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
        this.id = option.getId();
        this.relevance = option.getRelevance();
        this.sequence = option.getSequence();
        this.content = option.getContent();
        this.correct = option.isCorrect();
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
