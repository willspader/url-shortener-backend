# url-shortener-backend

### What is it

This application saves a URL with a random string of 8 characters. After saving the URL, a short link is provided which can be used for redirecting
to the original URL.

### Docker

#### Dev environment

You can clone this repository and run the command below to create a MongoDB server

- docker-compose -f docker-compose-dev.yml up

After that, just import the application in your favorite IDE. You need to provide
a properties file to run the application, if you are using IntelliJ you can do the following:

- Run -> Edit Configurations... -> VM Options: -Dspring.config.name=application-dev

#### Uat environment

This environment is for testing the Dockerfile.

Do the following:

- gradlew build
- docker build --tag url-shortener-api:1.0.0 .
- docker-compose -f docker-compose-uat.yml up

### Usage

#### Save URL Endpoint

You can send an HTTP POST request to the /api/v1/url-shortener/save-url endpoint to create the short link

```
{
    "originalURL": "www.google.com",
    "customURL": "teste"
}
```
or 
```
{
    "originalURL": "www.google.com"
}
```

#### Get URL Endpoint
 
 To be able to get the redirect link, you can send an HTTP GET request to http://localhost:8080/api/v1/url-shortener/{shortURL} endpoint
 
 ```
 http://localhost:8080/api/v1/url-shortener/teste
 ```
