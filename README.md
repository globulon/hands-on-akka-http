# hands-on-akka-http

101 getting acquainted with Akka again

- [X] init
- [X] Create dto
- [X] create domain
- [X] create algebras
- [ ] Create in memory algebra
- [ ] Connect algebra
- [ ] Start with IO
- [ ] Use Ciris for config
- [ ] Try command / queries

## Usage 

Expected user creation
```$bash
curl -v -H "Content-Type: application/json" -X POST -d '{"name": "test"}'  http://localhost:8080/users
```

List all users
```$bash
curl -v  -X GET http://localhost:8080/users
```

List user __test__
```$bash
curl -v  -X GET http://localhost:8080/users/test
```
