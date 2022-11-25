# PTSD-Service
This is the Java Spring Opensearch Service Repository

# Technical Stack & External Dependencies
use  `mvn dependency:tree` to view more.
- Java Open JDK (17)
- spring-boot-starter-data-jpa (2.7.4)
- spring-boot-starter-web (2.7.4)
- spring-boot-starter-test (2.7.4)
- postgresql (42.5.0)
- log4j-api (2.19.0)
- log4j-core (2.19.0)
- slf4j-simple (2.0.3)
- opensearch-rest-high-level-client (2.3.0)
- opensearch-java (2.1.0)
- jakarta.json-api (2.1.1)

# Steps to run manually
| Step  | Instructions                                                                                                                                          | Description                                                                                               |
| ----- |:------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------- |
| 1     | `docker run -d -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" opensearchproject/opensearch:1.3`                                            | Spin up an OpenSearch container locally. Default UN: "admin" & PWD: "admin". |
| 2     | `docker cp custom-opensearch.yml 3d803eaaca2e:/usr/share/opensearch/config/opensearch.yml`                                                            | Create a custom OpenSearch config file disabling the following three lines only. We do this to disable security. Then run the given command with the container Id. Entries in the custom config file should be "network.host: 0.0.0.0", "node.max_local_storage_nodes: 3", "plugins.security.disabled: true". Finally restart the container |
| 3     | `docker run --name postgresql -e POSTGRES_USER=myusername -e POSTGRES_PASSWORD=mypassword -p 5432:5432 -v /data:/var/lib/postgresql/data -d postgres` | Spin up a postgres container.
| 4     | Modify application properties                                                                                                                         | Need to change the postgres and OpenSearch URIs.|
| 5     | Start the application. Access the URI: http://localhost:8081/accounts?accountNumber=10                                                                | Now you can consume the API. |

# Steps to run with docker compose
| Step  | Instructions                                                                           | Description                                                                        |
| ----- |:---------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| 1     | `sudo sysctl -w vm.max_map_count=262144`                                               | Run this command in the terminal.                                                  |
| 2     | `bash docker-compose-local-run.sh`                                                     | This shell script will start the containers and service will run with dev profile. |
| 3     | `http://localhost:5601`                                                                | To access the opensearch dashboard.                                                |
| 5     | Start the application. Access the URI: http://localhost:8081/accounts?accountNumber=10 | Now you can consume the API.                                                       |

# Dev guide
- make sure your java version is 17
- make sure you are following the declarative programming methodology (Functional Programming) in this module
