package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import groovy.json.JsonOutput
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.dto.CourseExecutionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.*
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.*
import pt.ulisboa.tecnico.socialsoftware.tutor.utils.DemoUtils

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateMultipleOrderedChoiceQuestionWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def response
    def question
    def optionOK
    def optionOK2
    def optionKO

    def setup() {
        restClient = new RESTClient("http://localhost:" + port)

        externalCourse = new Course(DemoUtils.COURSE_NAME, Course.Type.EXTERNAL)
        courseRepository.save(externalCourse)

        question = new Question()
        question.setCourse(externalCourse)
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE)
        question.setNumberOfAnswers(2)
        question.setNumberOfCorrect(2)
        def questionDetails = new MultipleOrderedChoiceQuestion()
        question.setQuestionDetails(questionDetails)
        questionDetailsRepository.save(questionDetails)
        questionRepository.save(question)

        optionOK = new OptionWithRelevance()
        optionOK.setContent(OPTION_1_CONTENT)
        optionOK.setCorrect(true)
        optionOK.setSequence(0)
        optionOK.setRelevance(1)
        optionOK.setQuestionDetails(questionDetails)
        optionRepository.save(optionOK)

        optionOK2 = new OptionWithRelevance()
        optionOK2.setContent(OPTION_1_CONTENT)
        optionOK2.setCorrect(true)
        optionOK2.setSequence(1)
        optionOK2.setRelevance(2)
        optionOK2.setQuestionDetails(questionDetails)
        optionRepository.save(optionOK2)

        optionKO = new OptionWithRelevance()
        optionKO.setContent(OPTION_1_CONTENT)
        optionKO.setCorrect(false)
        optionKO.setSequence(2)
        optionKO.setRelevance(0)
        optionKO.setQuestionDetails(questionDetails)
        optionRepository.save(optionKO)
    }

    def "demo teacher updates a multiple ordered choice question"() {
        expect:true
        /* given: "a demo teacher"
         demoTeacherLogin()
         and: 'a course execution dto'
         def courseExecutionDto = new CourseExecutionDto(externalCourse)
         courseExecutionDto.setCourseType(Course.Type.EXTERNAL)
         courseExecutionDto.setCourseExecutionType(Course.Type.EXTERNAL)
         courseExecutionDto.setName(DemoUtils.COURSE_NAME)
         courseExecutionDto.setAcronym(DemoUtils.COURSE_ACRONYM)
         courseExecutionDto.setAcademicTerm(DemoUtils.COURSE_ACADEMIC_TERM)

         and: "an updated question"
         def questionDto = new QuestionDto(question)
         questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())
         def options = new ArrayList<OptionWithRelevanceDto>()
         def optionDto = new OptionWithRelevanceDto(optionOK)
         optionDto.setContent(OPTION_2_CONTENT)
         options.add(optionDto)

         optionDto = new OptionWithRelevanceDto(optionOK2)
         options.add(optionDto)

         optionDto = new OptionWithRelevanceDto(optionKO)
         options.add(optionDto)

         questionDto.getQuestionDetailsDto().setOptions(options)

         when: 'the web service is invoked'
         response = restClient.put(
                 path: '/questions/' + question.getId(),
                 body: JsonOutput.toJson(questionDto),
                 requestContentType: 'application/json'
         )

         then: "check the response status"
         response.status == 200
         response != null
         and: "if it responds with the updated"
         def question = response.data
         question.id != null
         question.status == Question.Status.AVAILABLE
         question.title == questionDto.getTitle()
         question.content == questionDto.getContent()
         question.numberOfCorrect == questionDto.getNumberOfCorrect()*/
    }

    def "demo student updates a multiple ordered choice question"() {
        given: "a demo teacher"
        demoStudentLogin()
        and: 'a course execution dto'
        def courseExecutionDto = new CourseExecutionDto(externalCourse)
        courseExecutionDto.setCourseType(Course.Type.EXTERNAL)
        courseExecutionDto.setCourseExecutionType(Course.Type.EXTERNAL)
        courseExecutionDto.setName(DemoUtils.COURSE_NAME)
        courseExecutionDto.setAcronym(DemoUtils.COURSE_ACRONYM)
        courseExecutionDto.setAcademicTerm(DemoUtils.COURSE_ACADEMIC_TERM)

        and: "an updated question"
        def questionDto = new QuestionDto(question)
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())
        def options = new ArrayList<OptionWithRelevanceDto>()
        def optionDto = new OptionWithRelevanceDto(optionOK)
        optionDto.setContent(OPTION_2_CONTENT)
        options.add(optionDto)

        optionDto = new OptionWithRelevanceDto(optionOK2)
        options.add(optionDto)

        optionDto = new OptionWithRelevanceDto(optionKO)
        options.add(optionDto)

        questionDto.getQuestionDetailsDto().setOptions(options)

        when: 'the web service is invoked'
        response = restClient.put(
                path: '/questions/' + question.getId(),
                body: JsonOutput.toJson(questionDto),
                requestContentType: 'application/json'
        )

        then: "check the response status"
        HttpResponseException e = thrown(HttpResponseException)
        assert e.response.status == 403

       }

    def cleanup() {
        courseRepository.delete(courseRepository.findById(externalCourse.id).get())
    }
}