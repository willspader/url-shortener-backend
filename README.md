# url-shortener-backend

### What is it

This application saves a URL with a random string of 8 characters. After saving the URL, a short link is provided which can be used for redirecting

### Docker

For now, you can clone this repository and run the command below to create a MongoDB server

- docker-compose up

After that, just import the application in your favorite IDE and run it.

The properties of the mongo database are in the application.properties file inside the resources folder

### Usage

#### Save URL

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

#### Get URL
 
 To be able to get the redirect link, you can send an HTTP GET request to http://localhost:8080/api/v1/url-shortener/{shortURL} endpoint
 
 ```
 http://localhost:8080/api/v1/url-shortener/teste
 ```
