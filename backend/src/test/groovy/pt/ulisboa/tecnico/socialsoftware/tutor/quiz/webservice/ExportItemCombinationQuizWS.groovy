package pt.ulisboa.tecnico.socialsoftware.tutor.quiz.webservice

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.dto.QuizDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExportQuizWebServiceIT extends SpockTest{
    @LocalServerPort
    private int port

    def user
    def quizDto
    def questionDto

    def setup(){
        restClient = new RESTClient("http://localhost:" + port)

        createExternalCourseAndExecution()

        quizDto = new QuizDto()
        quizDto.setKey(1)
        quizDto.setScramble(true)
        quizDto.setOneWay(true)
        quizDto.setQrCodeOnly(true)
        quizDto.setTitle(QUIZ_TITLE)
        quizDto.setAvailableDate(STRING_DATE_YESTERDAY)
        quizDto.setConclusionDate(STRING_DATE_TOMORROW)
        quizDto.setResultsDate(STRING_DATE_LATER)

        def question = new QuestionDto()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE.name())
        def questionDetails = new ItemCombinationQuestionDto()
        question.setQuestionDetailsDto(questionDetails)

        def items = new ArrayList<ItemDto>()
        def itemOneDto = new ItemDto()
        itemOneDto.setContent(ITEM_1_CONTENT)
        def itemTwoDto = new ItemDto()
        itemTwoDto.setContent(ITEM_2_CONTENT)
        items.add(itemOneDto)
        items.add(itemTwoDto)
        question.getQuestionDetailsDto().setItems(items)

        questionDto = questionService.createQuestion(externalCourse.getId(), questionDto)

        quizDto = quizService.createQuiz(externalCourseExecution.getId(), quizDto)
        quizService.addQuestionToQuiz(questionDto.getId(), quizDto.getId())

    }

    def "teacher exports a quiz"(){
        given: "a teacher"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.TEACHER, false, AuthUser.Type.EXTERNAL)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(externalCourseExecution)
        externalCourseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        and:
        restClient.handler.failure = { resp, reader -> [response:resp, reader:reader] }
        restClient.handler.success = { resp, reader -> [response:resp, reader:reader] }

        when:
        def map = restClient.get(
                path: '/quizzes/' + quizDto.getId() + '/export',
                requestContentType: "application/json"
        )

        then: 'the response status is OK'
        assert map['response'].status == 200
        assert map['reader'] != null

    }

    def cleanup(){
        userRepository.deleteById(user.getId())
        courseExecutionRepository.deleteById(externalCourseExecution.getId())
        courseRepository.deleteById(externalCourse.getId())
    }
}