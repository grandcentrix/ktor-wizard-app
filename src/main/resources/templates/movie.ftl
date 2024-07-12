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
        <span class="material-symbols-outlined">Movie</span>
        Movie Details
    </h1>
    <section class="content">
        <h2>Movie Information</h2>
        <ul class="content-block">
            <li>Summary: ${movie.summary!}</li>
            <li>BoxOffice: ${movie.boxOffice!}</li>
            <li>Budget: ${movie.budget!}</li>
            <li>Distributors: ${movie.distributors?join(', ')}</li>
            <li>rating: ${movie.rating!}</li>
            <li>Release Date: ${movie.releaseDate!}</li>
            <li>Duration: ${movie.duration!}</li>
            <li>Cinematographers: ${movie.cinematographers?join(', ')}</li>
            <li>Directors: ${movie.directors?join(', ')}</li>
            <li>Screenwriters: ${movie.screenwriters?join(', ')}</li>
            <li>Producer: ${movie.producers?join(', ')}</li>
            <li>Editors: ${movie.editors?join(', ')}</li>
            <li>Music Composers: ${movie.music_composers?join(', ')}</li>
            <li>Trailer: <a href="${movie.trailer!}">${movie.trailer!}</a></li>
            <li>Wiki: <a href="${movie.wiki!}">${movie.wiki!}</a></li>
        </ul>
    </section>
</@layout.base>