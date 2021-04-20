package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.bind.annotation.DeleteMapping
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RemoveMultipleOrderedChoiceQuestionWebServiceIT extends SpockTest{
    @LocalServerPort
    private int port

    def courseExecution
    def response
    def question
    def course
    def user

    def setup() {
        given: 'a rest client'
        restClient = new RESTClient("http://localhost:" + port)

        and: 'a course'
        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
        courseExecutionRepository.save(courseExecution)

        and: 'a question'
        question = new Question()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE)
        question.setCourse(course)
        def questionDetails = new MultipleOrderedChoiceQuestion()
        question.setQuestionDetails(questionDetails)
        questionRepository.save(question)
    }

    def "teacher removes multiple ordered choice question"() {
        given: 'a teacher login'
        //demoTeacherLogin()
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL, User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        when: 'the web service is invoked'
        response = restClient.delete(
                path: "/questions/"+ question.getId(),
                requestContentType: 'application/json'
        )

        then: "the request returns OK"
        response.status == 200
        and: "the question was removed from the database"
        questionRepository.findById(question.getId()).isEmpty()

        cleanup:
        userRepository.deleteById(user.getId())

    }

    def "student removes multiple ordered choice question"() {
        given: 'a demo student'
        demoStudentLogin()
        when: 'the web service is invoked'
        response = restClient.delete(
                path: "/questions/"+ question.getId(),
                requestContentType: 'application/json'
        )
        then: "the request returns 403"
        def error = thrown(HttpResponseException)
        error.response.status == HttpStatus.SC_FORBIDDEN
    }
    
    // Missing more access control tests (such as teacher without permission)

     def cleanup(){
         courseExecutionRepository.deleteById(courseExecution.getId())
         courseRepository.deleteById(course.getId())
         questionRepository.deleteAll()
     }



}