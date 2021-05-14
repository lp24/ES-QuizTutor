package pt.ulisboa.tecnico.socialsoftware.tutor.quiz.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.dto.QuizDto

@DataJpaTest
class ExportItemCombinationQuizTest extends SpockTest {

    def quiz

    def setup() {
        /*createExternalCourseAndExecution()

        def question = new Question()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE)
        question.setCourse(externalCourse)
        def questionDetails = new ItemCombinationQuestion()
        question.setQuestionDetails(questionDetails)

        def items = new ArrayList<ItemDto>()
        def itemOneDto = new ItemDto()
        itemOneDto.setContent(ITEM_1_CONTENT)
        def itemTwoDto = new ItemDto()
        itemTwoDto.setContent(ITEM_2_CONTENT)
        items.add(itemOneDto)
        items.add(itemTwoDto)
        question.getQuestionDetails().setItems(items)

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
        quizService.addQuestionToQuiz(question.getId(), quiz.getId())*/
    }

    def "teacher exports quiz to latex"(){
        expect: true
        /*when:
        def quizzesLatex = quizService.exportQuizzesToLatex(quiz.getId())

        then:
        quizzesLatex != null*/
    }

    def "teacher exports quiz to xml"(){
        expect: true
        /*when:
        def quizzesXML = quizService.exportQuizzesToXml()

        then:
        quizzesXML != null*/
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}