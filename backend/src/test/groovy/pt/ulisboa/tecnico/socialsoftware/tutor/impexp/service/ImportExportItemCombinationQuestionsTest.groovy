package pt.ulisboa.tecnico.socialsoftware.tutor.impexp.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto

@DataJpaTest
class ImportExportItemCombinationQuestionsTest extends SpockTest{
    def questionId

    def setup() {
        createExternalCourseAndExecution()

        def questionDto = new QuestionDto()
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new ItemCombinationQuestionDto())
        // Missing items
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
        // Must save the question int the repository
        questionId = questionService.createQuestion(externalCourse.getId(), questionDto).getId()
    }

    def "import and export item combination questions to xml"() {
        given: 'a xml with questions'
        def questionsXml = questionService.exportQuestionsToXml()
        print questionsXml
        and: 'a clean database'
        questionService.removeQuestion(questionId)

        when:
        questionService.importQuestionsFromXml(questionsXml)

        then:
        questionRepository.findQuestions(externalCourse.getId()).size() == 1
        def questionResult = questionService.findQuestions(externalCourse.getId()).get(0)
        questionResult.getKey() == null
        questionResult.getTitle() == QUESTION_1_TITLE
        questionResult.getContent() == QUESTION_1_CONTENT
        questionResult.getStatus() == Question.Status.AVAILABLE.name()
        // Missing item validations
    }

    def "export item combination questions to latex"() {
        when:
        def questionsLatex = questionService.exportQuestionsToLatex()
        print questionsLatex

        then:
        questionsLatex != null
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}