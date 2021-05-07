package pt.ulisboa.tecnico.socialsoftware.tutor.quiz.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.QuizQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.dto.QuizDto

@DataJpaTest
class CheckQuizResults extends SpockTest{
    def  quizDto
    def question1
    def question2
    def questionDto
    def optionOK
    def optionOK2
    def optionKO

    def setup(){
        createExternalCourseAndExecution()
        given: "create a quiz"
        //Criar um quiz
        quizDto = new QuizDto()
        quizDto.setKey(1)
        quizDto.setScramble(true)
        quizDto.setOneWay(true)
        quizDto.setQrCodeOnly(true)
        quizDto.setAvailableDate(STRING_DATE_TODAY)
        quizDto.setConclusionDate(STRING_DATE_TOMORROW)
        quizDto.setResultsDate(STRING_DATE_LATER)

        //and: "create first question"
        //Criar questoes para colocar no quiz
        // Question 1
        question1 = new Question()
        question1.setKey(1)
        question1.setCourse(externalCourse)
        question1.setTitle(QUESTION_1_TITLE)
        question1.setContent(QUESTION_1_CONTENT)
        question1.setStatus(Question.Status.AVAILABLE)
        question1.setNumberOfAnswers(2)
        question1.setNumberOfCorrect(2)
        def questionDetails = new MultipleOrderedChoiceQuestion()
        question1.setQuestionDetails(questionDetails)
        questionDetailsRepository.save(questionDetails)
        questionRepository.save(question1)

        questionDto = new QuestionDto(question1)
        questionDto.setKey(1)
        questionDto.setSequence(1)

        // criar 3 opcoes
        optionOK = new OptionWithRelevance()
        optionOK.setContent(OPTION_1_CONTENT)
        optionOK.setCorrect(true)
        optionOK.setSequence(0)
        optionOK.setRelevance(1)
        optionOK.setQuestionDetails(questionDetails)
        optionRepository.save(optionOK)

        optionOK2 = new OptionWithRelevance()
        optionOK2.setContent(OPTION_2_CONTENT)
        optionOK2.setCorrect(true)
        optionOK2.setSequence(1)
        optionOK2.setRelevance(2)
        optionOK2.setQuestionDetails(questionDetails)
        optionRepository.save(optionOK2)

        optionKO = new OptionWithRelevance()
        optionKO.setContent(OPTION_3_CONTENT)
        optionKO.setCorrect(false)
        optionKO.setSequence(2)
        optionKO.setRelevance(0)
        optionKO.setQuestionDetails(questionDetails)
        optionRepository.save(optionKO)

        //Question 2
        //and: "create second2 question"
        question2 = new Question()
        question2.setKey(1)     //ATT: Chaves iguais...Potencial Problema, talvez ??
        question2.setCourse(externalCourse)
        question2.setTitle(QUESTION_2_TITLE)
        question2.setContent(QUESTION_2_CONTENT)
        question2.setStatus(Question.Status.AVAILABLE)
        question2.setNumberOfAnswers(2)
        question2.setNumberOfCorrect(2)
        def questionDetails2 = new MultipleOrderedChoiceQuestion()
        question2.setQuestionDetails(questionDetails2)
        questionDetailsRepository.save(questionDetails2)
        questionRepository.save(question2)

        questionDto = new QuestionDto(question2)
        questionDto.setKey(1)
        questionDto.setSequence(2)

        //e 3 opcoes
        optionOK = new OptionWithRelevance()
        optionOK.setContent(OPTION_1_CONTENT)
        optionOK.setCorrect(true)
        optionOK.setSequence(0)
        optionOK.setRelevance(2)
        optionOK.setQuestionDetails(questionDetails2)
        optionRepository.save(optionOK)

        optionOK2 = new OptionWithRelevance()
        optionOK2.setContent(OPTION_2_CONTENT)
        optionOK2.setCorrect(true)
        optionOK2.setSequence(1)
        optionOK2.setRelevance(1)
        optionOK2.setQuestionDetails(questionDetails2)
        optionRepository.save(optionOK2)

        optionKO = new OptionWithRelevance()
        optionKO.setContent(OPTION_3_CONTENT)
        optionKO.setCorrect(false)
        optionKO.setSequence(2)
        optionKO.setRelevance(0)
        optionKO.setQuestionDetails(questionDetails2)
        optionRepository.save(optionKO)

        //Criar respostas as questoes ou usar o populateQuizWithAnswers


    }

    def "teacher sees quiz results"(){

        given: "a questionDto"
        questionDto = new QuestionDto(question1)
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto(question1))

        def options = new ArrayList<OptionWithRelevanceDto>()
        def optionDto = new OptionWithRelevanceDto(optionOK)
        options.add(optionDto)
        optionDto = new OptionWithRelevanceDto(optionOK2)
        options.add(optionDto)
        optionDto = new OptionWithRelevanceDto(optionKO)
        options.add(optionDto)

        questionDto.getQuestionDetailsDto().setOptions(options)

        //second question
        questionDto = new QuestionDto(question1)
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto(question1))

        options = new ArrayList<OptionWithRelevanceDto>()
        optionDto = new OptionWithRelevanceDto(optionOK)
        options.add(optionDto)
        optionDto = new OptionWithRelevanceDto(optionOK2)
        options.add(optionDto)
        optionDto = new OptionWithRelevanceDto(optionKO)
        options.add(optionDto)

        questionDto.getQuestionDetailsDto().setOptions(options)

        and: " a quizDto"
        //TODO


    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
