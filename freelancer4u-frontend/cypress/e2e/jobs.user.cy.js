/// <reference types="Cypress"/>
describe('Manage jobs as admin', () => {
    before(() => {
        cy.visit('http://localhost:8080')

        // login as "user"
        cy.login("user").then(()=> {
            // Reload the page after login
            cy.visit('http://localhost:8080')
        })
    })

    it('visit jobs page', () => {
        cy.get('a[href="#/jobs"]').click()
        cy.location('hash').should('include', 'jobs')
    })

    it('jobs form is not displayed', () => {
        cy.get('#description').should('not.exist')
        cy.get('#type').should('not.exist')
        cy.get('#earnings').should('not.exist')
        cy.contains('Submit').should('not.exist')
    })

    it('job is assigned to me', () => {
        cy.get('tbody>tr:nth-child(2) button').click()
        cy.get('tbody>tr:nth-child(2) .badge').should('exist')
    })

    it('filter by type', () => {
        cy.get('#typefilter').select("IMPLEMENT")
        cy.contains('Apply').click()
        cy.get('tbody>tr').should('have.length', 2)
    })

    it('filter by earnings', () => {
        cy.get('#earningsfilter').type("110")
        cy.contains('Apply').click()
        cy.get('tbody>tr').should('have.length', 1)
    })

})