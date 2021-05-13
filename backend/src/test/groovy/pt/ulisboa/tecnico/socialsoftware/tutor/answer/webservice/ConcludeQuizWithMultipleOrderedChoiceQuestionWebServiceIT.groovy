package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuizAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleChoiceStatementAnswerDetailsDto
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleOrderedChoiceStatementAnswerDetailsDto
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDto
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuizDto
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Option
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.repository.OptionWithRelevanceRepository
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.dto.QuizDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.utils.DateHandler

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConcludeQuizWithMultipleOrderedChoiceQuestionWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def course
    def courseExecution
    def student
    def teacher

    def questionDto
    def response
    def question

    def quizDto
    def quizAnswer
    def options
    def quizDto2

    def setup() {
        given: 'a rest client'
        restClient = new RESTClient("http://localhost:" + port)

        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
        courseExecutionRepository.save(courseExecution)

        teacher = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        //teacher.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        teacher.addCourse(courseExecution)
        courseExecution.addUser(teacher)
        userRepository.save(teacher)

        questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())

        and: 'two options with relevance DTO'
        def optionDto1 = new OptionWithRelevanceDto()
        optionDto1.setContent(OPTION_1_CONTENT)
        optionDto1.setCorrect(true)
        optionDto1.setSequence(0)
        optionDto1.setRelevance(OPTION_1_RELEVANCE)
        //optionDto1.setQuestionDetails(questionDetails)

        def optionDto2 = new OptionWithRelevanceDto()
        optionDto2.setContent(OPTION_2_CONTENT)
        optionDto2.setCorrect(true)
        optionDto2.setSequence(1)
        optionDto2.setRelevance(OPTION_2_RELEVANCE)
        //optionDto2.setQuestionDetails(questionDetails)

        def optionKO = new OptionWithRelevanceDto()
        optionKO.setContent("Option Content")
        optionKO.setCorrect(false)
        optionKO.setSequence(2)
        // optionKO.setQuestionDetails(questionDetails)

        options = new ArrayList<OptionWithRelevanceDto>()
        options.add(optionDto1)
        options.add(optionDto2)



        questionDto.getQuestionDetailsDto().setOptions(options)

        quizDto = new QuizDto()
        quizDto.setKey(1)
        quizDto.setTitle("Quiz Title")
        quizDto.setType(Quiz.QuizType.PROPOSED.toString())
        //quizDto.setCourseExecution(externalCourseExecution)
        quizDto.setAvailableDate(DateHandler.toISOString(DateHandler.now()))

        quizDto2 = quizService.createQuiz(courseExecution.getId(),quizDto)

        student = new User(USER_2_NAME, USER_2_EMAIL, USER_2_EMAIL,
                User.Role.STUDENT, false, AuthUser.Type.TECNICO)
        student.authUser.setPassword(passwordEncoder.encode(USER_2_PASSWORD))
        student.addCourse(courseExecution)
        courseExecution.addUser(student)
        userRepository.save(student)
        createdUserLogin(USER_2_EMAIL, USER_2_PASSWORD)
    }

    def "student answer a quiz with multiple ordered quiz questions"(){
        given: 'a quiz'

        def statementQuizDto = new StatementQuizDto()
        statementQuizDto.id = quizDto2.getId()
        //statementQuizDto.quizAnswerId = quizAnswer.getId()
        def statementAnswerDto = new StatementAnswerDto()
        def multipleOrderedChoiceAnswerDto = new MultipleOrderedChoiceStatementAnswerDetailsDto()
        multipleOrderedChoiceAnswerDto.setOptionId(options.get(0).getId())
        statementAnswerDto.setAnswerDetails(multipleOrderedChoiceAnswerDto)
        statementAnswerDto.setSequence(0)
        statementAnswerDto.setTimeTaken(100)
        //statementAnswerDto.setQuestionAnswerId(quizAnswer.getQuestionAnswers().get(0).getId())
        statementQuizDto.getAnswers().add(statementAnswerDto)


        when: 'the web service is invoked'
        def mapper = new ObjectMapper()
        def jsonStr = mapper.writeValueAsString(statementQuizDto)
       // def object = mapper.readValue(jsonStr, QuestionDto.class)

        response = restClient.post(
                path: '/quizzes/'+quizDto2.getId() + '/conclude',
                body: jsonStr,
                requestContentType: 'application/json'
        )

        then: "the request returns OK"
        response.status == 200

        /*and: "if it responds with the correct questionDto"
        def question = response.data
        question.id != null
        question.title == questionDto.getTitle()
        question.content == questionDto.getContent()
        question.status == Question.Status.AVAILABLE.name()*/

    }

    def cleanup(){
        userRepository.deleteById(teacher.getId())
        userRepository.deleteById(student.getId())
        courseExecutionRepository.deleteById(courseExecution.getId())
        courseRepository.deleteById(course.getId())
    }

}