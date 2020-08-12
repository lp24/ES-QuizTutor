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
import pt.ulisboa.tecnico.socialsoftware.tutor.user.dto.ExternalUserDto
import spock.lang.Shared
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

@DataJpaTest
class CreateExternalUser extends SpockTest {

    ExternalUserDto externalUserDto

    @Autowired
    Mailer mailerMock

    def setup() {
        course = new Course(COURSE_1_NAME, Course.Type.EXTERNAL)
        courseRepository.save(course)
        courseExecution = new CourseExecution(course, COURSE_1_ACRONYM, COURSE_1_ACADEMIC_TERM, Course.Type.EXTERNAL)
        courseExecutionRepository.save(courseExecution)
    }

    def "the course execution exists, the external user is enrolled in it and tries to enroll again"() {
        given: "a external course execution"
        def executionId = courseExecution.getId()
        and: "a external user dto"
        externalUserDto = new ExternalUserDto()
        externalUserDto.setEmail(USER_1_EMAIL)
        externalUserDto.setRole(User.Role.STUDENT)

        when:
        userService.createExternalUserApplicational(executionId, externalUserDto)
        userService.createExternalUserApplicational(executionId, externalUserDto)

        then: "an exception is thrown"
        def error = thrown(TutorException)
        error.getErrorMessage() == ErrorMessage.DUPLICATE_USER
    }

    def "the course execution exists, the username does not exist, create the user and associate the user with the course execution"() {
        given: "a external course execution"
        def executionId = courseExecution.getId()
        and: "a external user dto"
        externalUserDto = new ExternalUserDto()
        externalUserDto.setEmail(USER_1_EMAIL)
        externalUserDto.setRole(User.Role.STUDENT)

        when:
        def result = userService.createExternalUserApplicational(executionId, externalUserDto)

        then:"the user is saved in the database"
        userRepository.findAll().size() == 4
        and: "checks if user data is correct"
        result.getUsername() == USER_1_EMAIL
        result.getEmail() == USER_1_EMAIL
        result.getState() == User.State.INACTIVE
        and:"checks if the user and the course execution are associated"
        result.getConfirmationToken() != ""
        courseExecution.getUsers().size() == 1
        courseExecution.getUsers().toList().get(0).getId() == result.getId()
        and: "a mail is sent"
        1 * mailerMock.sendSimpleMail(mailerUsername, USER_1_EMAIL, User.PASSWORD_CONFIRMATION_MAIL_SUBJECT, _)
    }

    def "the course execution exists, the user exists but he's not enrolled and enroll him in the course execution"() {
        given: "a user"
        def user = new User("", USER_1_EMAIL, User.Role.STUDENT)
        user.setEmail(USER_1_EMAIL)
        userRepository.save(user)
        and: "a external course execution"
        def executionId = courseExecution.getId()
        and: "a external user dto"
        externalUserDto = new ExternalUserDto()
        externalUserDto.setEmail(USER_1_EMAIL)
        externalUserDto.setRole(User.Role.STUDENT)

        when:
        def result = userService.createExternalUserApplicational(executionId, externalUserDto)

        then:"the user is saved in the database"
        userRepository.count() == 4
        and: "checks if user data is correct"
        result.getUsername() == USER_1_EMAIL
        result.getEmail() == USER_1_EMAIL
        and:"checks if the user and the course execution are associated"
        result.getConfirmationToken() != ""
        courseExecution.getUsers().size() == 1
        courseExecution.getUsers().toList().get(0).getId() == result.getId()

        and: "a mail is sent"
        1 * mailerMock.sendSimpleMail(mailerUsername, USER_1_EMAIL, User.PASSWORD_CONFIRMATION_MAIL_SUBJECT,_)
    }

    @Unroll
    def "invalid arguments: executionId=#executionId | executionType=#executionType | email=#email | role=#role"() {
        given: "a course execution id"
        courseExecution.setType(executionType)
        def executionId = courseExecution.getId()

        and: "a external user dto"
        externalUserDto = new ExternalUserDto()
        externalUserDto.setEmail(email)
        externalUserDto.setRole(role)

        when:
        userService.createExternalUserApplicational(executionId, externalUserDto)

        then:
        def error = thrown(TutorException)
        error.getErrorMessage() == errorMessage
        and: "no mail is sent"
        0 * mailerMock.sendSimpleMail(_,_,_,_)
        and: "no user was created"
        userRepository.count() == 3

        where:
        executionType         | email             | role                     || errorMessage
        Course.Type.TECNICO   | USER_1_EMAIL      | User.Role.STUDENT        || ErrorMessage.COURSE_EXECUTION_NOT_EXTERNAL
        Course.Type.EXTERNAL  | null              | User.Role.STUDENT        || ErrorMessage.INVALID_EMAIL
        Course.Type.EXTERNAL  | ""                | User.Role.STUDENT        || ErrorMessage.INVALID_EMAIL
        Course.Type.EXTERNAL  | "test.mail.com"   | User.Role.STUDENT        || ErrorMessage.INVALID_EMAIL
        Course.Type.EXTERNAL  | "test@"           | User.Role.STUDENT        || ErrorMessage.INVALID_EMAIL
        Course.Type.EXTERNAL  | USER_1_EMAIL      | null                     || ErrorMessage.INVALID_ROLE
        Course.Type.EXTERNAL  | USER_1_EMAIL      | User.Role.ADMIN          || ErrorMessage.INVALID_ROLE
        Course.Type.EXTERNAL  | USER_1_EMAIL      | User.Role.DEMO_ADMIN     || ErrorMessage.INVALID_ROLE

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