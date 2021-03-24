package pt.ulisboa.tecnico.socialsoftware.tutor.impexp.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemCombinationQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User

@DataJpaTest
class ImportExportItemCombinationQuestionsTest extends SpockTest{
    def "export item combination questions to xml"() {
        expect: false;
    }

    def "import item combination questions to xml"() {
        expect: false;
    }

    def "export item combination questions to latex"() {
        expect: false;
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}