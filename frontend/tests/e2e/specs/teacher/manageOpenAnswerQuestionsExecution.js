describe('Manage Open Answer Questions Walk-through', () => {
    function validateQuestion(title, content, answer) {
        cy.get('[data-cy="showQuestionDialog"]')
            .should('be.visible')
            .within(($ls) => {
                cy.get('.headline').should('contain', title);
                cy.get('span > p').should('contain', content);
                cy.get('p').should('contain',answer);
            });
    }

    function validateQuestionFull(title, content, answer) {
        cy.get('[data-cy="questionTitleGrid"]').first().click();

        validateQuestion(title, content, answer);

        cy.get('button').contains('close').click();
    }

    before(() => {
    });
    after(() => {
        cy.demoTeacherLogin();
        cy.server();
        cy.route('GET', '/courses/*/questions').as('getQuestions');
        cy.route('GET', '/courses/*/topics').as('getTopics');
        cy.get('[data-cy="managementMenuButton"]').click();
        cy.get('[data-cy="questionsTeacherMenuButton"]').click();
        cy.wait('@getQuestions').its('status').should('eq', 200);
        cy.wait('@getTopics').its('status').should('eq', 200);
        cy.deleteLastQuestion();
        cy.logout();
    });

    beforeEach(() => {
        cy.demoTeacherLogin();
        cy.server();
        cy.route('GET', '/courses/*/questions').as('getQuestions');
        cy.route('GET', '/courses/*/topics').as('getTopics');
        cy.get('[data-cy="managementMenuButton"]').click();
        cy.get('[data-cy="questionsTeacherMenuButton"]').click();
        cy.wait('@getQuestions').its('status').should('eq', 200);
        cy.wait('@getTopics').its('status').should('eq', 200);
    });

    afterEach(() => {
        cy.logout();
    });

    it('Creates a new open answer question', function () {
        cy.get('button').contains('New Question').click();
    
        cy.get('[data-cy="createOrEditQuestionDialog"]')
          .parent()
          .should('be.visible');
    
        cy.get('span.headline').should('contain', 'New Question');

        cy.get('[data-cy="questionTypeInput"]')
            .type('open answer', { force: true })
            .click({ force: true });
    
        cy.get('[data-cy="questionTitleTextArea"]')
            .type('PRA Title Example', { force: true });
        cy.get('[data-cy="questionQuestionTextArea"]')
            .type('PRA Content Example', { force: true });
        cy.get('[data-cy="Answer"]')
            .type('PRA Answer Example', { force: true });
    
        cy.route('POST', '/courses/*/questions/').as('postQuestion');

        cy.get('button').contains('Save').click();
    
        cy.wait('@postQuestion').its('status').should('eq', 200);
    
        cy.get('[data-cy="questionTitleGrid"]')
          .first()
          .should('contain', 'PRA Title Example');
    
        validateQuestionFull(
          'PRA Title Example',
          'PRA Content Example',
          'PRA Answer Example'
        );
    });

    it('Can view question (with button)', function () {
        cy.get('tbody tr')
            .first()
            .within(($list) => {
                cy.get('button').contains('visibility').click();
            });
        validateQuestion(
            'PRA Title Example',
            'PRA Content Example',
            'PRA Answer Example'
        );
        cy.get('button').contains('close').click();
    });

    it('Can view question (with click)', function () {
        cy.get('[data-cy="questionTitleGrid"]').first().click();

        validateQuestion(
            'PRA Title Example',
            'PRA Content Example',
            'PRA Answer Example'
        );

        cy.get('button').contains('close').click();
    });

    it('Can update title (with right-click)', function () {
        cy.route('PUT', '/questions/*').as('updateQuestion');

        cy.get('[data-cy="questionTitleGrid"]').first().rightclick();

        cy.get('[data-cy="createOrEditQuestionDialog"]')
            .parent()
            .should('be.visible')
            .within(($list) => {
                cy.get('span.headline').should('contain', 'Edit Question');

                cy.get('[data-cy="questionTitleTextArea"]')
                    .clear({ force: true })
                    .type('Edited PRA Title Example', { force: true });

                cy.get('button').contains('Save').click();
            });

        cy.wait('@updateQuestion').its('status').should('eq', 200);

        cy.get('[data-cy="questionTitleGrid"]')
            .first()
            .should('contain', 'Edited PRA Title Example');

        validateQuestionFull(
            'Edited PRA Title Example',
            'PRA Content Example',
            'PRA Answer Example'
        );
    });

    it('Can update content (with button)', function () {
        cy.route('PUT', '/questions/*').as('updateQuestion');

        cy.get('tbody tr')
            .first()
            .within(($list) => {
                cy.get('button').contains('edit').click();
            });

        cy.get('[data-cy="createOrEditQuestionDialog"]')
            .parent()
            .should('be.visible')
            .within(($list) => {
                cy.get('span.headline').should('contain', 'Edit Question');

                cy.get('[data-cy="questionQuestionTextArea"]')
                    .clear({ force: true })
                    .type('Edited PRA Content Example', { force: true });
                cy.get('[data-cy="Answer"]')
                    .clear({ force: true })
                    .type('Edited PRA Answer Example', { force: true });

                cy.get('button').contains('Save').click();
            });

        cy.wait('@updateQuestion').its('status').should('eq', 200);

        validateQuestionFull(
            'Edited PRA Title Example',
            'Edited PRA Content Example',
            'Edited PRA Answer Example'
        );
    });

    it('Can duplicate question', function () {
        cy.get('tbody tr')
          .first()
          .within(($list) => {
            cy.get('button').contains('cached').click();
          });
    
        cy.get('[data-cy="createOrEditQuestionDialog"]')
          .parent()
          .should('be.visible');
    
        cy.get('span.headline').should('contain', 'New Question');
    
        cy.get('[data-cy="questionTitleTextArea"]')
          .should('have.value', 'Edited PRA Title Example')
          .type('{end} - DUP', { force: true });
        cy.get('[data-cy="questionQuestionTextArea"]').should(
          'have.value',
          'Edited PRA Content Example'
        );
        cy.get('[data-cy="Answer"]').should('have.value','Edited PRA Answer Example');
    
        cy.route('POST', '/courses/*/questions/').as('postQuestion');    
        cy.get('button').contains('Save').click();
    
        cy.wait('@postQuestion').its('status').should('eq', 200);
    
        cy.get('[data-cy="questionTitleGrid"]')
          .first()
          .should('contain', 'Edited PRA Title Example - DUP');
    
        validateQuestionFull(
            'Edited PRA Title Example - DUP',
            'Edited PRA Content Example',
            'Edited PRA Answer Example'
        );
    });

    it('Can delete created question', function () {
        cy.deleteLastQuestion();      

        validateQuestionFull(
            'Edited PRA Title Example',
            'Edited PRA Content Example',
            'Edited PRA Answer Example'
        );
    });
});
