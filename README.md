# gym-api

Gym app api


## Frontend

https://github.com/filip-stepien/gym-frontend

### Plan

- Authorization with [OAuth 2.0](https://datatracker.ietf.org/doc/html/rfc6749) and [keycloak](https://www.keycloak.org/).
- [Spring Boot](https://spring.io)

### Database Model

![model](./docs/model.svg)

### Endpoints

openAPI spec is located in [docs/api-spec](docs/api-spec) directory.
Documetation can also be acceseed from running instance.

- WEBui `/swagger`
- openAPI `/api-docs`

## Contributing

- commit convention https://www.conventionalcommits.org
- versioning https://semver.org/

### Development setup

Prerequisites:

-   docker-compose
-   https://direnv.net/ or you are on your own with tools.

### Coding

#### using task

`task dev`

#### Manually
- run `docker-compose -f src/docker/docker-compose.yaml up`
- ???
- commit

### Manually generating documetation

`task docs`


# FAQ

## Q: Why not `@Data`

https://youtu.be/Gd6AQsthXNY?t=2722

## Q: Why it's version 1.x.x > not 0.x.x

sematnic-release does not follow semver guidelines: https://semantic-release.gitbook.io/semantic-release/support/faq#can-i-set-the-initial-release-version-of-my-package-to-0.0.1