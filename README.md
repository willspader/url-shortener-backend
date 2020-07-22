# url-shortener-backend

### What is it

This application saves a URL with a random string of 8 characters.
After saving the URL, a short link is provide which
can be used for redirecting

### Docker

For now, you can clone this repository and run the command bellow to create a mongodb server

- docker-compose up

After that, just import the application in your favorite IDE and run it.

The properties of mongo database are in application.properties file inside resources folder

### Usage

#### Save URL

You can send a HTTP POST request to the /api/v1/url-shortener/save-url endpoint to create the short link

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
 
 To be able to get the redirect link, you can send a HTTP GET request to http://localhost:8080/api/v1/url-shortener/{shortURL} endpoint
 
 ```
 http://localhost:8080/api/v1/url-shortener/teste
 ```