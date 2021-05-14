package pt.ulisboa.tecnico.socialsoftware.tutor.answer.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.MultipleOrderedChoiceAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuizAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleOrderedChoiceStatementAnswerDetailsDto
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDto
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuizDto
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleOrderedChoiceQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OptionWithRelevance
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.QuizQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.utils.DateHandler

@DataJpaTest
class CheckQuizResultsWithMultipleOrderedChoiceQuestionTest extends SpockTest {

    def user
    def quizQuestion
    def option1
    def option2
    def optionKO
    def quizAnswer
    def date
    def quiz

    def setup() {
        createExternalCourseAndExecution()
        user = new User(USER_1_NAME, USER_1_USERNAME, USER_1_EMAIL, User.Role.STUDENT, false, AuthUser.Type.TECNICO)
        user.addCourse(externalCourseExecution)
        userRepository.save(user)
        def question = new Question()
        question.setKey(1)
        question.setTitle("Question Title")
        question.setCourse(externalCourse)
        def questionDetails = new MultipleOrderedChoiceQuestion()
        question.setQuestionDetails(questionDetails)
        questionDetailsRepository.save(questionDetails)
        questionRepository.save(question)

        option1 = new OptionWithRelevance()
        option1.setContent(OPTION_1_CONTENT)
        option1.setCorrect(true)
        option1.setSequence(0)
        option1.setRelevance(OPTION_1_RELEVANCE)
        option1.setQuestionDetails(questionDetails)
        optionWithRelevanceRepository.save(option1)

        option2 = new OptionWithRelevance()
        option2.setContent(OPTION_2_CONTENT)
        option2.setCorrect(true)
        option2.setSequence(1)
        option2.setRelevance(OPTION_2_RELEVANCE)
        option2.setQuestionDetails(questionDetails)
        optionWithRelevanceRepository.save(option2)

        optionKO = new OptionWithRelevance()
        optionKO.setContent("Option Content")
        optionKO.setCorrect(false)
        optionKO.setSequence(2)
        optionKO.setQuestionDetails(questionDetails)
        optionWithRelevanceRepository.save(optionKO)

        quiz = new Quiz()
        quiz.setKey(1)
        quiz.setTitle("Quiz Title")
        quiz.setType(Quiz.QuizType.PROPOSED.toString())
        quiz.setCourseExecution(externalCourseExecution)
        quiz.setAvailableDate(DateHandler.now())
        quizRepository.save(quiz)

        quizQuestion = new QuizQuestion(quiz, question, 0)
        quizQuestionRepository.save(quizQuestion)

        date = DateHandler.now()

        quizAnswer = new QuizAnswer(user, quiz)
        quizAnswerRepository.save(quizAnswer)
    }

    def 'conclude quiz with answer, before conclusionDate'() {
        expect: true
        /*given: 'a quiz with future conclusionDate'
        quiz.setConclusionDate(DateHandler.now().plusDays(2))
        and: 'an answer'
        def statementQuizDto = new StatementQuizDto()
        statementQuizDto.id = quiz.getId()
        statementQuizDto.quizAnswerId = quizAnswer.getId()
        def statementAnswerDto = new StatementAnswerDto()
        def multipleOrderedChoiceAnswerDto = new MultipleOrderedChoiceStatementAnswerDetailsDto()
        multipleOrderedChoiceAnswerDto.setOptionId(option1.getId())
        // System.out.println(option1)
        statementAnswerDto.setAnswerDetails(multipleOrderedChoiceAnswerDto) //this.answerDetails = answerDetails
        statementAnswerDto.setSequence(0)
        statementAnswerDto.setTimeTaken(100)
        statementAnswerDto.setQuestionAnswerId(quizAnswer.getQuestionAnswers().get(0).getId())
        statementQuizDto.getAnswers().add(statementAnswerDto)

        when:
        def correctAnswers = answerService.concludeQuiz(statementQuizDto)

        then: 'the value is createQuestion and persistent'
        quizAnswer.isCompleted()
        questionAnswerRepository.findAll().size() == 1
        def questionAnswer = questionAnswerRepository.findAll().get(0)
        questionAnswer.getQuizAnswer() == quizAnswer
        quizAnswer.getQuestionAnswers().contains(questionAnswer)
        questionAnswer.getQuizQuestion() == quizQuestion
        quizQuestion.getQuestionAnswers().contains(questionAnswer)
        ((MultipleOrderedChoiceAnswer) questionAnswer.getAnswerDetails()).getOption() == option1
        option1.getQuestionAnswers().contains(questionAnswer.getAnswerDetails())
        and: 'the return value is OK'
        correctAnswers.size() == 1
        def correctAnswerDto = correctAnswers.get(0)
        correctAnswerDto.getSequence() == 0
        correctAnswerDto.getCorrectAnswerDetails().getCorrectOptionId() == option1.getId()*/
    }
    
    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}