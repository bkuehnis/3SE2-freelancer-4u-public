import config from "../../src/auth.config";

Cypress.Commands.add("login", (user_type) => {
  const domain = config.domain;
  const options = {
    method: "POST",
    url: `https://${domain}/oauth/token`,
    body: {
      grant_type: "password",
      username: Cypress.env()[user_type].email,
      password: Cypress.env()[user_type].password,
      audience: `https://${domain}/api/v2/`,
      scope: "openid profile email",
      client_id: config.clientId,
      client_secret: Cypress.env("auth0_client_secret"),
    },
  };
  cy.request(options).then((resp) => {
    const { id_token, access_token } = resp.body;
    sessionStorage.setItem("jwt_token", id_token);
    cy.request({
      method: "GET",
      url: `${domain}/oauth/userinfo`,
      headers: {
        Authorization: "Bearer " + access_token
      }
    }).then((resp) => {
      const userInfo = resp.body;
      sessionStorage.setItem("user", JSON.stringify(userInfo));
    });
  });
});