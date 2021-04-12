package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseException
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RemoveOpenAnswerQuestionWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def response
    def user
    def question
    def course
    def courseExecution

    def setup() {
        given: 'a rest client'
        restClient = new RESTClient("http://localhost:" + port)

        and: 'a course'
        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
        courseExecutionRepository.save(courseExecution)

        and:"an open answer question"
        question = new Question()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE)
        question.setCourse(course)
        def questionDetails = new OpenAnswerQuestion()
        question.setQuestionDetails(questionDetails)

    }

    def "remove an open answer question"() {
        given: "a database with an open answer question"
        questionRepository.save(question)

        and: "a teacher"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        when: "the service is invoked"
        response = restClient.delete(
                path: "/questions/"+ question.getId(),
                requestContentType: "application/json"
        )

        then: "response status was OK"
        response.status == 200
        and: "the question was removed from the database"
        questionRepository.findById(question.getId()).isEmpty()

        cleanup:
        userRepository.deleteById(user.getId())
    }

    def "student cant remove question"(){
        given: "a database with an open answer question"
        questionRepository.save(question)

        and: "a student"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.STUDENT, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        when:
        response = restClient.delete(
                path: "/questions/"+ question.getId(),
                requestContentType: "application/json"
        )

        then: "an exception is thrown"
        def exception = thrown(HttpResponseException)
        exception.response.status== HttpStatus.SC_FORBIDDEN

        and: "the question was not removed from the database"
        questionRepository.findAll().size()==1

        cleanup:
        questionRepository.deleteById(question.getId())
        userRepository.deleteById(user.getId())
    }

    def cleanup() {
        courseExecutionRepository.deleteById(courseExecution.getId())
        courseRepository.deleteById(course.getId())
    }
}