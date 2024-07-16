<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <style>
        a {
            color: #4682B4; /* Lighter shade of blue */
        }


        .movie_details{
            font-size: 1.4em; /* make the text a bit bigger */
            text-decoration: underline;
        }

        .larger-text {
            font-size: 1.2em; /* make the text even bigger */
        }
    </style>

    <h1>
        <span class="material-symbols-outlined">movie</span>
        Movie Details
    </h1>

    <section class="content">
        <h2>Movie Information</h2>

        <ul class="content-block">
            <li><span class="movie_details">Summary:</span> <span class="larger-text"> ${movie.summary!}</span></li>
            <br>
            <li><span class="movie_details">Box Office:</span> <span class="larger-text"> ${movie.boxOffice!}</span></li>
            <br>
            <li><span class="movie_details">Budget:</span> <span class="larger-text"> ${movie.budget!}</span></li>
            <br>
            <li><span class="movie_details">Distributors:</span> <span class="larger-text"> ${movie.distributors?join(', ')}</span></li>
            <br>
            <li><span class="movie_details">Rating:</span> <span class="larger-text"> ${movie.rating!}</span></li>
            <br>
            <li><span class="movie_details">Release Date:</span> <span class="larger-text"> ${movie.releaseDate!}</span></li>
            <br>
            <li><span class="movie_details">Duration:</span> <span class="larger-text"> ${movie.duration!}</span></li>
            <br>
            <li><span class="movie_details">Cinematographers:</span> <span class="larger-text"> ${movie.cinematographers?join(', ')}</span></li>
            <br>
            <li><span class="movie_details">Directors:</span> <span class="larger-text"> ${movie.directors?join(', ')}</span></li>
            <br>
            <li><span class="movie_details">Screenwriters:</span> <span class="larger-text"> ${movie.screenwriters?join(', ')}</span></li>
            <br>
            <li><span class="movie_details">Producer:</span> <span class="larger-text">${movie.producers?join(', ')}</span></li>
            <br>
            <li><span class="movie_details">Editors:</span> <span class="larger-text"> ${movie.editors?join(', ')}</span></li>
            <br>
            <li><span class="movie_details">Music Composers:</span> <span class="larger-text">${movie.music_composers?join(', ')}</span></li>
            <br>
            <li><span class="movie_details">Trailer:</span> <span class="larger-text"> <a href="${movie.trailer!}">${movie.trailer!}</a></span></li>
            <br>
            <li><span class="movie_details">Wiki:</span> <span class="larger-text"> <a href="${movie.wiki!}">${movie.wiki!}</a></span></li>
        </ul>
    </section>
</@layout.base>