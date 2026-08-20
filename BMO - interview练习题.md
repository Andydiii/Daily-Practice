# API / HTTP

## What is a REST API?
A REST API is an architectural style for designing APIs. This api is built aorund on resources. resource is represented by the URL after the HTTP methods. It uses HTTP methods such as GET, POST, PUT, DELETE to perform operations on resources. e.g. GET /users/123 means we want the info about the user with ID 123. 
![](image.png)
## How is HTTP realted to REST?
HTTP provides the communication protocol, while REST defines how we organize and interact with resources through that protocol.

## What is the difference between GET and POST?
GET is used to retrieve data from the server, but POST is commonly used to create new resource on the server.

## What is the difference between PUT and POST?
PUT is used to update existing resource on the server, but POST is used to create a new resource on the server.


## What does "idempotent" mean in HTTP?
idempotent means: Making the same request multiple times has the same intended effect on the server as making it once.

``` 
GET /users/123

PUT /users/123
{
    "name":"Andy"
}
```
GET/PUT/DELETE => Idempotent
POST => non Idempotent since if we make request 3 times, multiple resources may get created:
```
POST /orders
Order 101
Order 102
Order 103
```

## PATCH vs PUT:
PUT is generally used to replace or fully update an existing resource, but PATCH is used to partially update an resource. For example, if I only want to update an user's name, I can use PATCH and send only the name field.
```
PUT /users/123
{
  "name": "Andy Liu",
  "email": "andy@example.com",
  "age": 23
}
```

```
PATCH /users/123
{
  "name": "Andy Liu",
  "email": "andy@example.com",
  "age": 23
}
```

## API endpoint
An API endpoint is an specific URL where a client can access an resource. e.g. https://api.example.com/users/123 is an endpoint for accessing user 123.

## What is the difference between a path parameter and a query parameter?

`/users/123` is path parameter, but `/users?id=123`  is query parameter used to filter/sort/search/pagination...


`GET /users/123` Get user 123 \
`GET /users?country=Canada` Get users whose country is Canada. 
`GET /users?sort=name` get users and sort by name.

## What is the difference between an HTTP request and an HTTP response?
HTTP request is sent by client to a server to perform an operation on a resource. It usually contains an HTTP method, URL, headers, and sometimes a request body. An HTTP response is sent back by a server after processing the request. It usually contains the status code, headers, and sometimes a response body.

## What is an HTTP header? Can you give me one example?
```
common header examples(suppose we have following headers in HTTP requests):
Content-Type: application/json → body 是 JSON 格式
Authorization: Bearer <token> → 带认证 token
Accept: application/json → client 希望 server 返回 JSON
```

HTTP headers contain additional information about the HTTP request or response. For exmaple, `Content-Type: application/json` tells that the request/response body is in JSON format.
