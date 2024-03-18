# aggregate-seller-info

This is a simple graphql api for an application which shows aggregated seller information for different companies.

### Tech-stack:
* Java 17+
* Gradle 7.5+
* Docker
* Spring boot
* Postgres
* GraphQL

### Format code

```shell
$ ./gradlew spotlessApply
```

### Run tests

```shell
$ ./gradlew clean build
```

### Run locally

```shell
$ docker-compose -f docker/docker-compose.yml up -d
$ ./gradlew bootRun -Plocal
```

### Using Testcontainers at Development Time
You can run `TestApplication.java` from your IDE directly.
You can also run the application using Gradle as follows:

```shell
$ ./gradlew bootTestRun
```

### Useful Links
* Endpoint: http://localhost:8080/graphql

### Example Usage
```
Query:
    query sellerDetails($filter: SellerFilter, $page: PageInput!, $sortBy: SellerSortBy) {
      sellers(filter: $filter, page: $page, sortBy: $sortBy) {
        data {
          sellerName
          externalId
          marketplaceId
          producerSellerStates {
            producerId
            producerName
            sellerState
            sellerId
          }
        }
        meta {
            pageSize
            currentPage
        }
      }
    }

Variable:
    {
      "filter": {
        "searchByName": "John",
        "producerIds": ["0f025173-5671-400a-bb04-d84d9d8e09e5"],
        "marketplaceIds": ["0451c311-7d6a-4ed6-86fb-8cf4b862044e", "b2f238f7-ee58-43a5-953a-ea1cc359c1db"]
      },
      "page": {
        "page": 0,
        "size": 10
      },
      "sortBy": "NAME_DESC"
    }
```

### Note
- Used projector generator to speed up the development.
https://www.npmjs.com/package/generator-springboot
