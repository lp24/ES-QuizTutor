package pt.ulisboa.tecnico.socialsoftware.tutor.question.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.*
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.TopicDto
import pt.ulisboa.tecnico.socialsoftware.tutor.questionsubmission.domain.QuestionSubmission
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.QuizQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User

@DataJpaTest
class RemovePEMQuestionTest extends SpockTest {
    def question
    def optionOK
    def optionKO
    def teacher

    /*def setup() {

    }*/

    def "remove a question"() {
       expect: false
    }

    def "remove a question used in a quiz"() {
       expect: false
    }

    def "remove a question that has topics"() {
        expect: false
    }

    def "remove a question that was submitted"() {
        expect: false
    }
    def "remove a question that has options with relevance"() {
        expect: false
    }
    def "remove a question that has image and options with relevance"() {
        expect: false
    }



    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
