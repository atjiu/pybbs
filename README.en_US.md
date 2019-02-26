> Instructions: Please mark in a conspicuous place `powered by pybbs`

## Document

[Document](https://tomoya92.github.io/pybbs/#/)

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

[Getting Started Document](https://tomoya92.github.io/pybbs/#/getting-started)

**Special thanks to github user [@zzzzbw](https://github.com/zzzzbw) for helping to develop dockerfile**

## Manual package

```bash
mvn clean assembly:assembly
```

After the package is complete, a `pybbs.tar.gz` file will be generated in the target directory under the project root directory, extract it and run `sh start.sh` to start the forum service.

In addition, the tar.gz file generated after manual packaging is the latest release package in the release on github. After downloading, the extracted content is the same.

## Test

Project test cases have not been written yet!

## Feedback

- [开发俱乐部](https://17dev.club/)
- [issues](https://github.com/tomoya92/pybbs/issues)
- QQ group：`419343003`

*Please clearly describe the problem recurring steps when asking questions*

## Contribution

Welcome everyone to submit issues and pr

## Donation

![image](https://cloud.githubusercontent.com/assets/6915570/18000010/9283d530-6bae-11e6-8c34-cd27060b9074.png)
![image](https://cloud.githubusercontent.com/assets/6915570/17999995/7c2a4db4-6bae-11e6-891c-4b6bc4f00f4b.png)

**If you feel that this project is helpful to you, welcome to donate!**

## License

GNU AGPLv3
