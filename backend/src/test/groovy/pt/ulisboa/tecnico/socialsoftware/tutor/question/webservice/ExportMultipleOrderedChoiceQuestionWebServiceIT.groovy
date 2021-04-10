package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservice

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExportMultipleOrderedChoiceQuestion extends SpockTest{
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
                path: "/courses/" + courseExecutionDto.getCourseExecutionId() +"/questions/export",
                requestContentType: "application/zip"
        )

        then: "the response status is OK"
        assert map['response'].status == 200
    }
}
