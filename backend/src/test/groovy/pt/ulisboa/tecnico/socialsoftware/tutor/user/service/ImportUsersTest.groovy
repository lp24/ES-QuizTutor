package pt.ulisboa.tecnico.socialsoftware.tutor.user.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.course.Course
import pt.ulisboa.tecnico.socialsoftware.tutor.course.CourseExecution
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.tutor.mailer.Mailer
import pt.ulisboa.tecnico.socialsoftware.tutor.user.User
import spock.mock.DetachedMockFactory

@DataJpaTest
class ImportUsersTest extends SpockTest {

    @Autowired
    Mailer mailerMock

    Course course
    CourseExecution courseExecution
    def setup(){
        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL)
        courseExecutionRepository.save(courseExecution)
    }

    def 'Import users from csv file' () {
        given: "number of users in dataBase"
        def usersInDataBase = userRepository.count()
        and: "inputStream"
        InputStream csvFile = new FileInputStream(CSVFILE);
        when:
        userService.importListOfUsers(csvFile, courseExecution.getId())
        then:
        userRepository.findAll().size() == usersInDataBase + NUMBER_OF_USERS_IN_FILE;

        and: "a mail was sent for each user"
        NUMBER_OF_USERS_IN_FILE * mailerMock.sendSimpleMail(mailerUsername,_, User.PASSWORD_CONFIRMATION_MAIL_SUBJECT,_)


    }

    def 'Csv file has wrong format on some lines, no users are added' () {
        given: "number of users in dataBase"
        def usersInDataBase = userRepository.count()
        and: "wrong formatted InputStream"
        InputStream csvBadFormatFile = new FileInputStream(CSVBADFORMATFILE);
        when:
        userService.importListOfUsers(csvBadFormatFile, courseExecution.getId())
        then:
        def error = thrown(TutorException)
        error.getErrorMessage() == ErrorMessage.WRONG_FORMAT_ON_CSV_LINE
        and:
        userRepository.findAll().size() == usersInDataBase

        and: "no mail was sent"
        0 * mailerMock.sendSimpleMail(_,_,_,_)
    }

    def 'The course execution does not exist' () {
        given: "number of users in dataBase"
        def usersInDataBase = userRepository.count()
        and: "a invalid course execution id"
        def executionId = -1
        and: "inputStream"
        InputStream csvFile = new FileInputStream(CSVFILE);
        when:
        userService.importListOfUsers(csvFile, executionId)
        then:
        def error = thrown(TutorException)
        error.getErrorMessage() == ErrorMessage.COURSE_EXECUTION_NOT_FOUND
        and:
        userRepository.findAll().size() == usersInDataBase

        and: "no mail was sent"
        0 * mailerMock.sendSimpleMail(_,_,_,_)
    }

    def 'Csv file has wrong format for roles' () {
        given: "number of users in dataBase"
        def usersInDataBase = userRepository.count()
        and: "wrong role formatted InputStream"
        InputStream csvImportUsersBadRoleFormat = new FileInputStream(CSVIMPORTUSERSBADROLEFORMAT);
        when:
        userService.importListOfUsers(csvImportUsersBadRoleFormat, courseExecution.getId())
        then:
        def error = thrown(TutorException)
        error.getErrorMessage() == ErrorMessage.WRONG_FORMAT_ON_CSV_LINE
        and:
        userRepository.findAll().size() == usersInDataBase

        and: "no mail was sent"
        0 * mailerMock.sendSimpleMail(_,_,_,_)
    }


    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {
        def mockFactory = new DetachedMockFactory()

        @Bean
        Mailer mailer(){
            return mockFactory.Mock(Mailer)
        }
    }
}