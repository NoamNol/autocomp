# autocomp
Auto-complete with Spring Boot

## Usage

### Auto-complete
Get all words by prefix (Ignore case):
</br>
`http://localhost:8080/api/v1/autocomp/?prefix=<prefix>`

Limit results with `max`:
</br>
`http://localhost:8080/api/v1/autocomp/?prefix=<prefix>&max=<max>`

Select `groupname` (Default to 'default'):
</br>
`http://localhost:8080/api/v1/autocomp/?prefix=<prefix>&groupname=<groupname>`

#### Example
Get countries that start with 'I', and limit to 4 results:
</br>
`http://localhost:8080/api/v1/autocomp/?prefix=I&groupname=country&max=4`

Result:
```json
[
    "Iraq",
    "Italy",
    "India",
    "Israel"
]
```

The search is case-insensitive, but the original prefix is used in the results:
</br>
`http://localhost:8080/api/v1/autocomp/?prefix=iSrA&groupname=country`

Result:
```json
[
    "iSrAel"
]
```

### Words
Get all known words:
</br>
`http://localhost:8080/api/v1/word`