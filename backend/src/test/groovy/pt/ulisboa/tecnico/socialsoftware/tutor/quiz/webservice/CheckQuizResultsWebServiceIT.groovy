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
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.dto.QuizDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CheckQuizResultsWebServiceIT extends SpockTest{
    @LocalServerPort
    private int port

    def course
    def courseExecution
    def questionDto
    def quizDto
    def response
    def question
    def user

    def setup(){
        restClient = new RESTClient("http://localhost:" + port)

        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
        courseExecutionRepository.save(courseExecution)

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


        question = new Question()
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

        def questions = new ArrayList()
        questions.add(questionDto)
        quizDto.setQuestions(questions)

        quizDto = quizService.createQuiz(externalCourseExecution.getId(), quizDto)
        quizService.populateWithQuizAnswers(quizDto.getId())

    }

    def "teacher sees quiz results"(){
        expect:true

        /*given: "a teacher"
        demoTeacherLogin()
        *//*user = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        user.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        user.addCourse(courseExecution)
        courseExecution.addUser(user)
        userRepository.save(user)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)
        *//*

        when:
        response = restClient.get(path: "/quizzes/" + quizDto.getId() + "/answers", requestContentType: "application/json")

        then: "the request returns OK"
        response.status == 200*/
    }

    def cleanup(){
        userRepository.deleteById(user.getId())
        questionRepository.deleteById(question.getId())
        courseExecutionRepository.deleteById(courseExecution.getId())
        courseRepository.deleteById(course.getId())
    }
}
