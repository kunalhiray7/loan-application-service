# ADR - Design Proposal for the Search API

## Status

Planned

## Context & Story

As a loan manager, I want to search the customers based on the attributes like their loan status, the range of the amount etc. 
so that I analyze the trends in the loan applications and the customers.

## API Definition
### Request
* HTTP Method - `GET`
* Root Path - `/search`
* Query Parameters - Loan and customer attributes like `minAmount`, `maxAmount`, `status` etc.

### Response
* Success Response status - `OK 200`
* Success Response Body - 
```json
[
  {
    "id": 11,
    "userId": 1,
    "firstName": "John",
    "lastName": "Smith",
    "email": "johnsmith@example.com",
    "phone": "+49 123 456 78 910"
  },
  {
    "id": 12,
    "userId": 2,
    "firstName": "Mehmed",
    "lastName": "Demir",
    "email": "mehmeddemir@example.com",
    "phone": "+49 109 876 54 321"
  }
]
```
* The API will return an empty list if no matching resource found.

## Architecture Decisions

* The query attributes of the search may belong to `Loan Application` or `Customer` domains. Thus, we are required to search across two microservices viz. `customer-service` and `loan-application-service`.
It makes sense to have another microservice which will do the job of searching. Let's call it `search-service`.
* The API returns the `customers` entities matching with the search criteria. However, we might need the another natural search APIs to return
different entities like `loans` in future. It's a foreseeable situation, hence we need to change the root path of the URL to `GET /search/customers` 
and `GET /search/loanapplications` etc. This may become an overhead as the entities in the ecosystem grows. We can mitigate this by having a `Path Parameter`
in the URL like `GET /search/{entity}`. We can load the search handlers or the response writers dynamically as per the entity type.

## Implementation Notes

* We can use the `elasticsearch` from ELK stack to achieve the natural search across multiple domains.
* We can keep pushing searchable attributes to the elastic search and having appropriate index whenever any WRITE happens in the `customer` or
`loan-application` service. This can be achieved by `Event Sourcing`. The `search-microservice` will listen to the WRITE events from the `Event Source`
and update the indexes in the elastic search.
* We do not know the volume of the data and if it becomes overhead to use `elasticsearch`, we can use distributed caches like [GemFire](https://gemfire.docs.pivotal.io/910/geode/basic_config/the_cache/intro_cache_management.html) to achieve cross domain search.
* We should implement the QueryDSL in the Spring Boot microservices to have the generic search API within its domain. 
Therefore even if elsaticseach goes down for any reason, the `search-service` will call these APIs and marshal the response as a fall back mechanism.
* Caching - Caching at the `search-service` will play a key role here. The searching feature should work in isolation i.e. even if `customer`
or `loan-applications` services are down, it should work. We should come up with the optimum time to refresh the cache based on the real traffic in production.
* The API should be paginated with `next` and `previous` links derived using Spring HATEOAS.
