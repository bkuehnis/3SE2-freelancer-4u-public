/// <reference types="Cypress"/>
describe('Main Page', () => {
  it('Title contains "Welcome"', () => {
    cy.visit('http://localhost:8080')
    cy.get('h1').should('contain', 'Welcome')
  })
})