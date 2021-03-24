package pt.ulisboa.tecnico.socialsoftware.tutor.administration.service;

import pt.ulisboa.tecnico.socialsoftware.tutor.administration.AdministrationService
import spock.lang.Specification

class CreateAdHocCourseExecutionServiceSpockTest extends Specification {
    def adminService

    def setup() {
        adminService = new AdministrationService();
    }

    def "the course exists and create execution course"() {
        //the course execution is created
        expect: false
    }

    def "the course does not exist and create both, course and execution course"() {
        //course and course execution are created
        expect: false
    }

    def "the course and course execution exist"() {
        //an exception is thrown
        expect: false
    }

    def "course name is empty"() {
        //an exception is thrown
        expect: false
    }

    def "course name is blank"() {
        //an exception is thrown
        expect: false
    }

    def "execution course acronym is empty"() {
        //an exception is thrown
        expect: false
    }

    def "execution course acronym is blank"() {
        //an exception is thrown
        expect: false
    }

    def "execution course academic term is empty"() {
        //an exception is thrown
        expect: false
    }
}