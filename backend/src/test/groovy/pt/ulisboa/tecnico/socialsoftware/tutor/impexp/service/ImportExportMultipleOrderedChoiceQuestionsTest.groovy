package pt.ulisboa.tecnico.socialsoftware.tutor.impexp.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest

@DataJpaTest
class ImportExportMultipleOrderedChoiceQuestionsTest extends SpockTest {


    def setup() {
    }

    def 'export and import questions to xml'() {
        expect:false
    }

    def 'export to latex'() {
        expect:false
    }

}
