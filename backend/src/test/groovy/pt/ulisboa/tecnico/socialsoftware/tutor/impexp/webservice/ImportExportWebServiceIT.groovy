package pt.ulisboa.tecnico.socialsoftware.tutor.impexp.webservice

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.ImpExpService
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.user.repository.UserRepository
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImportExportWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port

    def user
    def admin
    def course
    def courseExecution
    def response

    def setup(){
        given: "a rest client"
        restClient = new RESTClient("http://localhost:" + port)
        and: 'the demo course execution'
        courseExecutionDto = courseService.getDemoCourse()
    }

    def "demo student is not allowed to export"(){
        given: "a demo student"
        demoStudentLogin()
        and: "an import/export service"
        def impExpService = Stub(ImpExpService.class)
        def exportFile = impExpService.exportAll()

        when: "the web service is invoked"
        response = restClient.post(
                path: '/student/export',
                body: exportFile,
                requestContentType: 'application/json'
        )
        then: "the request returns 404"
        def error = thrown(HttpResponseException)
        error.response.status == HttpStatus.SC_NOT_FOUND

    }

    def "demo admin is not allowed to export"() {
        given: "a demo admin and a file"
        demoAdminLogin()
        and: "an export service"
        def impExpService = Stub(ImpExpService.class)
        def exportFile = impExpService.exportAll()

        when: "the web service is invoked"
        response = restClient.post(
                path: '/admin/export',
                body: exportFile,
                requestContentType: 'application/json'
        )

        then: "the request returns 405"
        def error = thrown(HttpResponseException)
        error.response.status == HttpStatus.SC_METHOD_NOT_ALLOWED
    }

    //TESTE DE SUCESSO
    def "admin is allowed to export"() {
        given: "an admin"
        //TODO
        /* course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
         courseRepository.save(course)
         courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL, LOCAL_DATE_TOMORROW)
         courseExecutionRepository.save(courseExecution)
         user = new User(USER_1_NAME, USER_1_NAME, USER_1_EMAIL, User.Role.ADMIN, true, AuthUser.Type.EXTERNAL)
         user.setConfirmationToken(USER_1_TOKEN)
         user.setTokenGenerationDate(LOCAL_DATE_TODAY)
         admin = new AuthExternalUser(user, USER_1_NAME, USER_1_EMAIL)
         admin.setActive(true)
         admin.setConfirmationToken(USER_1_TOKEN)
         admin.setTokenGenerationDate(LOCAL_DATE_TODAY)
         courseExecution.addUser(admin)
         userRepository.save(admin)*/

        when: "the web service is invoked"
        response = restClient.get(  //TODO
                path: '/admin/export',
                requestContentType: 'application/json'
        )
        then: "check response status"
        response.status == 200
        response.data == null
        response.data.role == "ADMIN"
        /*response.data.username == USER_2_NAME

        courseExecution.getUsers().remove(userRepository.findByKey(response.data.key).get())
        authUserRepository.delete(userRepository.findByKey(response.data.key).get().getAuthUser())
        userRepository.delete(userRepository.findByKey(response.data.key).get())
*/
    }
}

