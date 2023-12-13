# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

My app allows users to input a movie title into a search bar that is then sent to the Open Movie Database API. My app than receives the release date of the movie, which it than sends to the NASA Astronomy Picture of the Day API. My app then receives the photo associated with the release date and displays the photo and release date of the movie for the user to see.

## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### API 1

```
https://www.omdbapi.com/?apikey=3498e0d5&t=Barbie
```

> This API required an API key which is shown in the link.

### API 2

```
https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=2023-07-21
```



## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

This project was the first time I truly felt like I understood JSON and GSON.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I would either create more features or choose different APIs. The project I have now is rather barren in features that aren't just loading and using the APIs. I also would have liked to have a better link between my two APIs beyond just circumstantial dates. Both of these were a product of me waiting to the last minute to begin on my project.
