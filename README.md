# autocomp
Auto-complete with Spring Boot

## Usage

### Auto-complete
Get all words by prefix:
`http://localhost:8080/api/v1/autocomp/?prefix=<prefix>`

Limit with `max`:
`http://localhost:8080/api/v1/autocomp/?prefix=hello&max=<max>`

### More
Get all words from the H2 db:
`http://localhost:8080/api/v1/word`