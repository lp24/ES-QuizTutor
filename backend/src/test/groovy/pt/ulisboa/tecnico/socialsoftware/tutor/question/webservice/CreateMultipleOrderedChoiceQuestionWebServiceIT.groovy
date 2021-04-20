package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Option
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateMultipleOrderedChoiceQuestionWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def courseExecutionDto
    def questionDto
    def response
    def question

    def setup() {
        given: 'a rest client'
        restClient = new RESTClient("http://localhost:" + port)

        and: 'the demo course execution'
        courseExecutionDto = courseService.getDemoCourse()

        questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())

        and: 'two options with relevance DTO'
        def optionsWithRelevance = new ArrayList<OptionWithRelevanceDto>()
        def optionWithRelevanceDto1 = new OptionWithRelevanceDto()
        optionWithRelevanceDto1.setRelevance(OPTION_1_RELEVANCE)
        optionWithRelevanceDto1.setSequence(1)
        optionWithRelevanceDto1.setCorrect(true)
        optionWithRelevanceDto1.setContent(OPTION_1_CONTENT)

        def optionWithRelevanceDto2 = new OptionWithRelevanceDto()
        optionWithRelevanceDto2.setRelevance(OPTION_2_RELEVANCE)
        optionWithRelevanceDto2.setSequence(2)
        optionWithRelevanceDto2.setCorrect(true)
        optionWithRelevanceDto2.setContent(OPTION_2_CONTENT)
        optionsWithRelevance.add(optionWithRelevanceDto1)
        optionsWithRelevance.add(optionWithRelevanceDto2)
        questionDto.getQuestionDetailsDto().setOptions(optionsWithRelevance)
    }

    def "teacher creates multiple ordered choice question"(){
        given: 'a demo teacher'
        demoTeacherLogin()

        when: 'the web service is invoked'
        def mapper = new ObjectMapper()
        def jsonStr = mapper.writeValueAsString(questionDto)
       // def object = mapper.readValue(jsonStr, QuestionDto.class)
        response = restClient.post(
                path: "/courses/" + courseExecutionDto.getCourseExecutionId() + "/questions",
                body: jsonStr,
                requestContentType: 'application/json'
        )

        then: "the request returns OK"
        response.status == 200

        and: "if it responds with the correct questionDto"
        def question = response.data
        question.id != null
        question.title == questionDto.getTitle()
        question.content == questionDto.getContent()
        question.status == Question.Status.AVAILABLE.name()
        // Must validate the PEM specific attributes (options with relevance)
        
        // Missing database validation (questions = 1, at least)

    }

    def "student creates multiple ordered choice question"(){
        given: 'a demo student'
        demoStudentLogin()
        when: 'the web service is invoked'
        def mapper = new ObjectMapper()
        def jsonStr = mapper.writeValueAsString(questionDto)
       // def object = mapper.readValue(jsonStr, QuestionDto.class)
        response = restClient.post(
                path: "/courses/" + courseExecutionDto.getCourseExecutionId() + "/questions",
                body: jsonStr,
                requestContentType: 'application/json'
        )

        then: "the request returns 403"
        def error = thrown(HttpResponseException)
        error.response.status == HttpStatus.SC_FORBIDDEN

    }
    
    // Missing more access control tests (such as teacher without permission)

    def cleanup(){
        // Nothing to Clean manually
        // What about the question you just created?
    }

}