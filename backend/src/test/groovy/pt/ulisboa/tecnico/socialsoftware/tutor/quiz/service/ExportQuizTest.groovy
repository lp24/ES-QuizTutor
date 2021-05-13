package pt.ulisboa.tecnico.socialsoftware.tutor.quiz.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.dto.QuizDto

@DataJpaTest
class ExportQuizTest extends SpockTest {

    def quiz
    def creationDate
    def availableDate
    def conclusionDate

    def setup(){
        createExternalCourseAndExecution()

        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())

        def optionDto = new OptionWithRelevanceDto()
        optionDto.setSequence(1)
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        optionDto.setRelevance(1)

        def options = new ArrayList<OptionWithRelevanceDto>()
        options.add(optionDto)

        optionDto = new OptionWithRelevanceDto()
        optionDto.setSequence(2)
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        optionDto.setRelevance(2)
        options.add(optionDto)

        optionDto = new OptionWithRelevanceDto()
        optionDto.setSequence(3)
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(false)
        optionDto.setRelevance(0)
        options.add(optionDto)

        questionDto.getQuestionDetailsDto().setOptions(options)
        questionDto = questionService.createQuestion(externalCourse.getId(), questionDto)

        def quizDto = new QuizDto()
        quizDto.setKey(1)
        quizDto.setScramble(false)
        quizDto.setQrCodeOnly(true)
        quizDto.setOneWay(false)
        quizDto.setTitle(QUIZ_TITLE)
        quizDto.setCreationDate(STRING_DATE_BEFORE)
        quizDto.setAvailableDate(STRING_DATE_YESTERDAY)
        quizDto.setConclusionDate(STRING_DATE_TOMORROW)
        quizDto.setType(Quiz.QuizType.EXAM.toString())

        quiz = quizService.createQuiz(externalCourseExecution.getId(), quizDto)
        quizService.addQuestionToQuiz(questionDto.getId(), quiz.getId())
    }

    def "teacher exports quiz to latex"(){
        when:
        def quizzesLatex = quizService.exportQuizzesToLatex(quiz.getId())

        then:
        quizzesLatex != null
    }

    def "teacher exports quiz to xml"(){
        when:
        def quizzesXML = quizService.exportQuizzesToXml()

        then:
        quizzesXML != null
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
