package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExportItemCombinationQuestionWS extends SpockTest {
    @LocalServerPort
    private int port

    def course
    def courseExecution
    def user
    def questionId
    def response

    def setup() {
        restClient = new RESTClient("http://localhost:" + port)

        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
        courseExecutionRepository.save(courseExecution)

        createExternalCourseAndExecution()

        def questionDto = new QuestionDto()
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new ItemCombinationQuestionDto())

        def items = new ArrayList<ItemDto>()
        def itemOneDto = new ItemDto()
        itemOneDto.setContent(ITEM_1_CONTENT)
        def itemTwoDto = new ItemDto()
        itemTwoDto.setContent(ITEM_2_CONTENT)
        itemOneDto.addConnection(itemTwoDto.getId())
        itemTwoDto.addConnection(itemOneDto.getId())
        items.add(itemOneDto)
        items.add(itemTwoDto)
        questionDto.getQuestionDetailsDto().setItems(items)

        questionId = questionService.createQuestion(externalCourse.getId(), questionDto).getId()
    }

    def "export item combination questions"() {
        given: "a teacher"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        and: "prepare a request response"
        restClient.handler.failure = { resp, reader -> [response:resp, reader:reader] }
        restClient.handler.success = { resp, reader -> [response:resp, reader:reader] }

        when:
        def map = restClient.get(path: "/courses/" + course.getId()+"/questions/export", requestContentType: "application/json")

        then: "response status was OK"
        assert map['response'].status == 200
        assert map['reader'] != null
    }

    def "export questions by a student"() {
        given: "a student"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.STUDENT, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        when:
        response = restClient.get(path: "/courses/" + course.getId()+"/questions/export", requestContentType: "application/json")

        then: "an exception is thrown"
        def exception = thrown(HttpResponseException)
        exception.response.status== HttpStatus.SC_FORBIDDEN
    }
    
    // Missing more access control tests (such as teacher without permission)

    def cleanup() {
        userRepository.deleteById(user.getId())
        courseExecutionRepository.deleteById(courseExecution.getId())
        courseRepository.deleteById(course.getId())
    }
}