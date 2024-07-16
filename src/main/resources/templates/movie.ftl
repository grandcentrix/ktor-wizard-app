<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <style>
        a {
            color: #4682B4; /* Lighter shade of blue */
        }
    </style>
    <h1>
        <span class="material-symbols-outlined">movie</span>
        Movie Details
    </h1>
    <section class="content">
        <h2>Movie Information</h2>
        <ul class="content-block">
            <li>Summary: ${movie.summary!}</li>
            <br>
            <li>BoxOffice: ${movie.boxOffice!}</li>
            <br>
            <li>Budget: ${movie.budget!}</li>
            <br>
            <li>Distributors: ${movie.distributors?join(', ')}</li>
            <br>
            <li>rating: ${movie.rating!}</li>
            <br>
            <li>Release Date: ${movie.releaseDate!}</li>
            <br>
            <li>Duration: ${movie.duration!}</li>
            <br>
            <li>Cinematographers: ${movie.cinematographers?join(', ')}</li>
            <br>
            <li>Directors: ${movie.directors?join(', ')}</li>
            <br>
            <li>Screenwriters: ${movie.screenwriters?join(', ')}</li>
            <br>
            <li>Producer: ${movie.producers?join(', ')}</li>
            <br>
            <li>Editors: ${movie.editors?join(', ')}</li>
            <br>
            <li>Music Composers: ${movie.music_composers?join(', ')}</li>
            <br>
            <li>Trailer: <a href="${movie.trailer!}">${movie.trailer!}</a></li>
            <br>
            <li>Wiki: <a href="${movie.wiki!}">${movie.wiki!}</a></li>
        </ul>
    </section>
</@layout.base>