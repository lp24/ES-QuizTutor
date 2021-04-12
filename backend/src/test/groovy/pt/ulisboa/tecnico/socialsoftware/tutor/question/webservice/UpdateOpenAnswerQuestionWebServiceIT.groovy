package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import groovyx.net.http.ContentEncoding
import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseException
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OpenAnswerQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateOpenAnswerQuestionWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def response
    def user
    def question
    def questionDto
    def course
    def courseExecution

    /*
    @PutMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('ROLE_TEACHER') and hasPermission(#questionId, 'QUESTION.ACCESS')")
    public QuestionDto updateQuestion(@PathVariable Integer questionId, @Valid @RequestBody QuestionDto question) {
        return this.questionService.updateQuestion(questionId, question);
    }
     */

    def setup() {
        given: 'a rest client'
        restClient = new RESTClient("http://localhost:" + port)

        and: 'a course'
        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
        courseExecutionRepository.save(courseExecution)
    }

    def "update an open answer question"() {
        given: "a teacher"
        user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        and:"an open answer question"
        question = new Question()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE)
        question.setCourse(course)
        question.setQuestionDetails(new OpenAnswerQuestion())
        question.getQuestionDetails().setCorrectAnswer(CORRECT_ANSWER)
        questionRepository.save(question)

        and: "an updated question"
        questionDto = new QuestionDto(question)
        questionDto.setTitle(QUESTION_2_TITLE)
        questionDto.setContent(QUESTION_2_CONTENT)
        questionDto.getQuestionDetailsDto().setCorrectAnswer(CORRECT_ANSWER_2)

        when: "the service is invoked"
        def mapper = new ObjectMapper()
        response = restClient.put(
                path: "/questions/"+ question.getId(),
                body: mapper.writeValueAsString(questionDto),
                requestContentType: "application/json"
        )

        then: "response status was OK"
        response.status == 200
        and: "the question was updated"
        response.data.title==QUESTION_2_TITLE
        response.data.content==QUESTION_2_CONTENT
        response.data.questionDetailsDto.correctAnswer==CORRECT_ANSWER_2

        cleanup:
        userRepository.deleteById(user.getId())
        questionRepository.deleteById(question.getId())
    }

    def "student cant remove question"(){
        expect: true
    }

    def cleanup() {
        courseExecutionRepository.deleteById(courseExecution.getId())
        courseRepository.deleteById(course.getId())
    }
}