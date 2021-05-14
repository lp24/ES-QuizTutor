describe('Answer Quiz with Open Answer Questions Walk-through', () => {
    function validateQuestion(title, content, answer) {
        cy.get('[data-cy="showQuestionDialog"]')
            .should('be.visible')
            .within(($ls) => {
                cy.get('.headline').should('contain', title);
                cy.get('span > p').should('contain', content);
            });
    }

    function validateQuestionFull(title, content, answer) {
        cy.get('[data-cy="questionTitleGrid"]').first().click();
        validateQuestion(title, content, answer);
        cy.get('button').contains('close').click();
    }

    function createQuestion(questionTitle){
        cy.get('button').contains('New Question').click();
    
        cy.get('[data-cy="createOrEditQuestionDialog"]')
          .parent()
          .should('be.visible');
    
        cy.get('span.headline').should('contain', 'New Question');

        cy.get('[data-cy="questionTypeInput"]')
            .type('open answer', { force: true })
            .click({ force: true });
    
        cy.get('[data-cy="questionTitleTextArea"]')
            .type(questionTitle, { force: true });
        cy.get('[data-cy="questionQuestionTextArea"]')
            .type('PRA Content Example', { force: true });
        cy.get('[data-cy="Answer"]')
            .type('PRA Answer Example', { force: true });
    
        cy.get('button').contains('Save').click();
       
        cy.get('[data-cy="questionTitleGrid"]')
          .first()
          .should('contain', questionTitle);
    
        validateQuestionFull(
            questionTitle,
            'PRA Content Example',
            'PRA Answer Example'
        );
    }
    
    function createQuiz() {
        cy.demoTeacherLogin();
        cy.get('[data-cy="managementMenuButton"]').click();
        cy.get('[data-cy="questionsTeacherMenuButton"]').click();

        createQuestion("PRA Title Example");
        createQuestion("PRA Title Example 2");
        cy.createQuizzWith2Questions("QUIZ_TITLE","PRA Title Example","PRA Title Example 2");
        cy.logout();
    }
         
    before(() => {

    });
    after(() => {
        //FIXME: clean database
    });

    beforeEach(() => {
    });

    afterEach(() => {
    });

    it('Student Answers to Quiz', function () {
        createQuiz();
        cy.demoStudentLogin();
        //Go to Quiz
        cy.get('[data-cy="quizzesStudentMenuButton"]').click();
        cy.get('[data-cy="StudentAvailableQuizzes"]').click();
        cy.get('[data-cy="availableQuizzesList"]').children('.list-row').first().click();

        //AnswerQuestion1        
        cy.get('[data-cy="studentAnswer"]').type('PRA Answer Example', { force: true });
        cy.get('[data-cy="submitAnswer"]').click();
        cy.get('[data-cy="nextQuestionButton"]').click();
        
        //Answer Question2
        cy.get('[data-cy="studentAnswer"]').clear({ force: true }).type('NOT PRA Answer Example', { force: true });
        cy.get('[data-cy="submitAnswer"]').click();
        cy.get('[data-cy="endQuizButton"]').click();
        cy.get('[data-cy="confirmationButton"]').click();

        //Check Results
        cy.get('[data-cy="studentAnswerResult"]').contains('PRA Answer Example');
        cy.get('[data-cy="nextQuestionButton"]').click();
        cy.get('[data-cy="studentAnswerResult"]').contains('NOT PRA Answer Example');
        cy.logout();
    });

    //FIXME: more tests

});
