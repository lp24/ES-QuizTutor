package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.OpenAnswerAnswer;

public class OpenAnswerAnswerDto extends AnswerDetailsDto {
    private String answerString;
    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public OpenAnswerAnswerDto() {
    }

    public OpenAnswerAnswerDto(OpenAnswerAnswer answer) {
        if (answer.getAnswerString() != null){
            this.setAnswerString(answer.getAnswerString());
        }
    }

}
