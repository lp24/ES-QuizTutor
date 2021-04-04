package pt.ulisboa.tecnico.socialsoftware.tutor.question.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.ItemCombinationQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User

@DataJpaTest
class UpdateItemCombinationTest extends SpockTest{
    def question
    def user
    def items
    def itemOneDto
    def itemTwoDto

    def setup() {
        createExternalCourseAndExecution()

        user = new User(USER_1_NAME, USER_1_USERNAME, USER_1_EMAIL, User.Role.STUDENT, false, AuthUser.Type.TECNICO)
        user.addCourse(externalCourseExecution)
        userRepository.save(user)

        question = new Question()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setStatus(Question.Status.AVAILABLE)
        question.setCourse(externalCourse)
        def questionDetails = new ItemCombinationQuestion()
        question.setQuestionDetails(questionDetails)
        questionDetailsRepository.save(questionDetails)
        questionRepository.save(question)

        items = new ArrayList<ItemDto>()
        itemOneDto = new ItemDto()
        itemOneDto.setContent(ITEM_1_CONTENT)
        itemTwoDto = new ItemDto()
        itemTwoDto.setContent(ITEM_2_CONTENT)
        items.add(itemOneDto)
        items.add(itemTwoDto)
        question.getQuestionDetails().setItems(items)
    }

    def "update an item combination question"() {
        given: "an updated question"
        question.setTitle(QUESTION_2_TITLE)
        question.setContent(QUESTION_2_CONTENT)
        question.setQuestionDetailsDto(new ItemCombinationQuestionDto())

        and: 'the first item and second item are changed'
        itemOneDto.setContent("CONTENT3")
        itemTwoDto.setContent("CONTENT4")
        items.add(itemOneDto)
        items.add(itemTwoDto)
        question.getQuestionDetails().setItems(items)

        when:
        questionService.updateQuestion(question.getId(), question.getQuestionDto())

        then: "the question is changed"
        questionRepository.count() == 2L // Why 2? You only saved one question in the repository
        def result = questionRepository.findAll().get(1)
        result.getId() == question.getId()
        result.getTitle() == QUESTION_2_TITLE
        result.getContent() == QUESTION_2_CONTENT

        and: 'are not changed'
        result.getStatus() == Question.Status.AVAILABLE
        result.getNumberOfAnswers() == 1
        result.getNumberOfCorrect() == 1

        def item1 = result.getQuestionDetails().getItems().get(0)
        def item2 = result.getQuestionDetails().getItems().get(1)
        item1.getConnections() == "CONTENT3"
        item2.getConnections() == "CONTENT4"
    }

    // Missing unsuccessful tests

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
