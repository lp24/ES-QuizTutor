package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.ACCESS_DENIED
import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.QUESTIONS_EXPORT_ERROR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExportMultipleOrderedChoiceQuestionWebServiceIT extends SpockTest{
    @LocalServerPort
    private int port
    def courseExecutionDto

    def setup(){
        restClient = new RESTClient("http://localhost:" + port)
        and: 'the demo course execution'
        courseExecutionDto = courseService.getDemoCourse()
    }

    def "a teacher exports questions"(){
        given: 'a demon teacher'
        demoTeacherLogin()
        and: 'prepare request response'
        restClient.handler.failure = { resp, reader ->
            [response:resp, reader:reader]
        }
        restClient.handler.success = { resp, reader ->
            [response:resp, reader:reader]
        }

        when: "the web service is invoked"
        def map = restClient.get(
                path: "/courses/" + courseExecutionDto.getCourseExecutionId() + "/questions/export",
                requestContentType: "application/zip"
        )

        then: "the response status is OK"
        assert map['response'].status == 200

    }

    def "a student exports questions"(){
        given: 'a demon teacher'
        demoStudentLogin()
        and: 'prepare request response'
        restClient.handler.failure = { resp, reader ->
            [response:resp, reader:reader]
        }
        restClient.handler.success = { resp, reader ->
            [response:resp, reader:reader]
        }

        when: "the web service is invoked"
        def response = restClient.get(
                path: "/courses/" + courseExecutionDto.getCourseExecutionId() + "/questions/export",
                requestContentType: "application/zip"
        )

        then: "the request returns nothing"
        response.data == null
    }
}
