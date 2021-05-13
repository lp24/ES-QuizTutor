package pt.ulisboa.tecnico.socialsoftware.tutor.quiz.webservice

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto
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

        Question question = new Question()
        question.setKey(1)
        question.setCourse(externalCourse)
        question.setTitle(QUESTION_1_TITLE)
        def questionDetails = new MultipleOrderedChoiceQuestion()
        question.setQuestionDetails(questionDetails)
        questionDetailsRepository.save(questionDetails)
        questionRepository.save(question)

        questionDto = new QuestionDto(question)
        questionDto.setKey(1)
        questionDto.setSequence(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())

        def optionDto = new OptionWithRelevanceDto()
        optionDto.setSequence(1)
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        optionDto.setRelevance(1)

        def options = new ArrayList<OptionWithRelevanceDto>()
        options.add(optionDto)

        optionDto = new OptionWithRelevanceDto()
        optionDto.setSequence(2)
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        optionDto.setRelevance(2)
        options.add(optionDto)

        optionDto = new OptionWithRelevanceDto()
        optionDto.setSequence(3)
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(false)
        optionDto.setRelevance(0)
        options.add(optionDto)

        questionDto.getQuestionDetailsDto().setOptions(options)
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

