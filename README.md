# pivete :cyclone:
app to navigate website, find user-provided term, list URLs

#### what?
> user-provided term must have a minimum of 4 and a maximum of 32 characters.  
> search is case insensitive  
> `base url` of the website where the analyzes are performed is determined by an environment variable.  
> search  follows links (absolute and relative) in anchor elements of visited pages only if they have the same base URL.  
> support the execution of multiple simultaneous searches. 


#### run:
```bash
docker build -t pivete .
docker run -e BASE_URL=http://base.url -p 4567:4567 --rm pivete
```

#### :japanese_goblin: endpoints:
> ```
> POST /crawl HTTP/1.1
> Host: localhost:4567
> Content-Type: application/json
> Body: {"keyword": "security"}
> ```
>
> ```
> 200 OK
> Content-Type: application/json
> Body: {"id": "30vbllyb"}
> ```


> ```
> GET /crawl/30vbllyb HTTP/1.1
> Host: localhost:4567
> ```
>
> ```
> 200 OK
> Content-Type: application/json
> Body: {
>   "id": "30vbllyb",
>   "status": "active",
>   "urls": [
>     "http://base.url/page.html",
>     "http://base.url/context/page.html"
>   ]
> }
> ```

