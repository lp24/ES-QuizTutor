package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import org.apache.http.HttpStatus
import groovyx.net.http.HttpResponseException
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OpenAnswerQuestionDto

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateOpenAnswerQuestionWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def response
    def user
    def question
    def questionDto
    def course
    def courseExecution

    def setup() {
        given: "a rest client"
        restClient = new RESTClient("http://localhost:" + port)

        and: "a course"
        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
        courseExecutionRepository.save(courseExecution)
    }

    def "create an open answer question by a teacher"() {
        given: "a teacher"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                        User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        and: "a questionDto"
        question = new QuestionDto()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new OpenAnswerQuestionDto())
        question.setCourse(course)
        question.getQuestionDetails().setCorrectAnswer(CORRECT_ANSWER)
        questionRepository.save(question)

        when: "the service is invoked"
        def mapper = new ObjectMapper()
        response = restClient.post(path: "/courses/" + course.getId() + "/questions",
                body: mapper.writeValueAsString(questionDto), requestContentType: "application/json")

        then: "check the response status"
        response != null
        response.status == 200
    }

    def "create an open answer question by a student"() {
        given: "a student"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.STUDENT, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        and: "a questionDto"
        question = new QuestionDto()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new OpenAnswerQuestionDto())
        question.setCourse(course)
        question.getQuestionDetails().setCorrectAnswer(CORRECT_ANSWER)
        questionRepository.save(question)

        when: "creation is requested"
        response = restClient.post(path: "/courses/" + course.getId() + "/questions",
                body: "error", requestContentType: "application/json")

        then: "an exception is thrown"
        def exception = thrown(HttpResponseException)
        exception.response.status== HttpStatus.SC_FORBIDDEN
    }

    def cleanup() {
        userRepository.deleteById(user.getId())
        courseExecutionRepository.deleteById(courseExecution.getId())
        courseRepository.deleteById(course.getId())
    }
}
