# Popular-Movies
This App:
- Present the user with a grid arrangement of movie posters upon launch.
- Allow your user to change sort order via a setting:
- - The sort order can be by most popular or by highest-rated.
- Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
1. Original title
2. Movie poster image thumbnail
3. A plot synopsis (called overview in the api)
4. User rating (called vote_average in the api)
5. Release date

- Allow users to view and play trailers ( either in the youtube app or a web browser).
- Allow users to read reviews of a selected movie.
- Allow users to mark a movie as a favorite in the details view by tapping a button(star).
- Have a database to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).

This project contains implementation of the following

- Retrofit(To fetch data from the API)
- Stetho(To analyze database)
- Picaso(To display images)
- RecyclerView (To display the list of movies)
- GSON(To store JSON data fetched from API)
- ButterKnife(For view binding)
- Room(For local storage)
- Timber(For Logging)
