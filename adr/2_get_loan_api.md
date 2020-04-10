# Mandatory customerId in The response and structure of API - GET /api/loanapplications?customerId={customerId} 

## Status

Accepted

## Context

The API Gateway expected that the customerId should be passed as request parameter in the GET Loan for customer API. By REST specification,
the request parameter can be optional. If the client has just the endpoint URL, there is no way to figure out whether the request parameter 
is optional or not. In this context, the loan entity cannot exist without customer information. Therefore we have to make the `customerId`
parameter as mandatory. Imagine the `customerId` is not mandatory and user calls this API as `GET /api/loanapplications`, it should return the
list of all the applications in the database. However, the response signature in the contract is not a list, its an object.

## Decision

The `customerId` request parameter in the API `GET /api/loanapplications?customerId={customerId}` is made mandatory. If the customerId
is invalid i.e. if it does not exist in the ecosystem, return NOT_FOUND with appropriate message. If customerId is valid but there is
no loan application for that customer in the system, return NO_CONTENT.

## Consequences

The API is compromised with RESTful specification. Imagine we want to implement get all loan applications API, it will have the same URL i.e.
`GET /api/loanapplications`. This will not be possible with the current design as it will return BAD_REQUEST if `customerId` is not passed.
We could change the contract slightly i.e. returning list instead of single object to make it non-mandatory. In that case, the system would
have returned all the loan applications if `customerId` is not passed in the request.

In this microservice `loanapplications` are first class citizens as it addresses `Loan Applications` domain. In future, we can change the
API signature for this API to avoid this dilemma. The problem can be solved by making `customerId` as `Path Parameter` instead of
request parameter. We can have an API something like - `GET /api/loanapplications/customers/{customerId}`.
