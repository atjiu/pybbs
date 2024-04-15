> Instructions: Please mark in a conspicuous place `powered by pybbs`

## Document

[Document](https://atjiu.github.io/pybbs/)

The documentation is written using the open source tool [docsify](https://docsify.js.org/#/quickstart)

## Technology

- Spring-Boot
- Shiro
- MyBatis-Plus
- Bootstrap
- MySQL
- Freemarker
- Redis
- ElasticSearch
- WebSocket
- I18N

## Feature

- This project integrates many third-party services, Such as redis, elasticsearch, websocket, etc.
- you can use it to build your own website, or you can use it as a project to learn related technologies.
- you can configure different third-party services on the backend.
- You can start a website service by using maven, docker, or downloading the zip package in the release.
- Integrated flyway makes it easy to iterate on database operations
- I18n support, so that language is not a barrier to communication
- The document is very detailed

## Getting Started

[Getting Started Document](https://atjiu.github.io/pybbs/#%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B)

**Special thanks to github user [@zzzzbw](https://github.com/zzzzbw) for helping to develop dockerfile**

## Manual package

```bash
mvn clean package
```

After the package is complete, a `pybbs.jar` file will be generated in the target directory under the project root directory, run `java -jar pybbs.jar` to start the forum service.

In addition, the tar.gz file generated after manual packaging is the latest release package in the release on github. After downloading, the extracted content is the same.

## Test

Project test cases have not been written yet!

## Feedback

- [issues](https://github.com/atjiu/pybbs/issues)

*Please clearly describe the problem recurring steps when asking questions*

## Contribution

Welcome everyone to submit issues and pr

## License

GNU AGPLv3
