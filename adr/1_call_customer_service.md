# Calling customer service to check if the customer exists for the Customer Id

## Status

Accepted

## Context

The request for creating a loan application has a field - customerId. It is a reference of a customer from the customer service.
In a monolith application, it throws CustomerNotFoundException(404) when there is no customer for the given customerId. As we break it into
microservices, we will have to keep the business requirement intact. We need to call the customer service to check if
the customer exists before creating the loan application. If we had some gateway microservice, we could have achieved it with Saga.
Even the front end application could have managed it with Saga. However, considering the loan-application-service in complete isolation,
I have decided to call the customer-service(/api/customers) to check the existence of the customer. Moreover, this call is synchronous using Feign
clients as we need the response from customer-service for further processing.  

## Decision

Calling customer-service(/api/customers/{customerId}) to check the if customer exists for given userId so that the loan application referring to it
can be saved.

## Consequences

It might introduce latency in the whole ecosystem. There is another scenario where customer-service goes down. This will lead to Bad Gateway
at the loan-application-service even if it is running fine. However, this can be mitigated by extensive caching at the loan-application-service as
the customer data is very unlikely to change. We can also have circuit breakers like Hystrix in place. 
