package pt.ulisboa.tecnico.socialsoftware.tutor.impexp.webservice

import groovy.json.JsonOutput
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.*
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.ImpExpService
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ImageDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.MultipleOrderedChoiceQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionWithRelevanceDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser

import java.net.http.HttpResponse

// PEM1.4 - Export Questions to XML - Test Web Service #107
// PEM1.5: Import Questions from XML - Test Web Service #108
// PEM1.6: Export Questions to LaTex - Test Web Service #109  <<----

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImportExportWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def student
    def admin
    def response

    def setup(){
        given: "a rest client"
        restClient = new RESTClient("http://localhost:" + port)
    }
    //TESTE DE CONTROLO DE ACESSO: testar que um aluno não pode fazer import ou export
    def "demo student is not allowed to import/export"(){
        given: "a demo student"
        demoStudentLogin()
        and: "a import service"
        def impExpService = Stub(ImpExpService.class)
        def exportFile = impExpService.exportAll()
        when: "the service is invoked"
        response = restClient.post(
                path: '/student/export',
                body: exportFile,
                requestContentType: 'application/json'
        )
        then: "the request returns 404"
        def error = thrown(HttpResponseException)
        error.response.status == HttpStatus.SC_NOT_FOUND
        /*Deveria ser,
          error.response.status == HttpStatus.SC_FORBIDDEN
          porque quero um erro de acesso. //TODO
        */

    }
    //TESTE DE SUCESSO: um admin consegues fazer import ou export
    //TESTE DE INSUCESSO: o quê que pode constituir um insucesso no import ou export de ficheiros??
}

