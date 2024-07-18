<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">movie</span>
        ${movie.title}
    </h1>

    <section class="content row">

        <div class="item-detail row">
            <div class="column" style="gap: 2%;">
            <img alt="" class="item-img" src="${movie.posterUrl}" />
            <div class="row" style="gap: 2%;">
                <a class="with-icon" href="${movie.trailer!}">
                    <span class="material-symbols-outlined">open_in_new</span> Trailer
                </a>
                <a class="with-icon" href="${movie.wiki!}">
                    <span class="material-symbols-outlined">open_in_new</span> Wiki
                </a>
            </div>
        </div>
            <ul class="item-content">

                <h2>Movie Information</h2>

                <li>
                    <h3>Box Office:</h3> 
                    <p> ${movie.boxOffice!}</p>
                </li>

                <li>
                    <h3>Budget:</h3> 
                    <p> ${movie.budget!}</p>
                </li>

                <li>
                    <h3>Distributor(s):</h3>
                    <p> ${movie.distributors?join(', ')}</p>
                </li>

                <li>
                    <h3>Rating:</h3> 
                    <p> ${movie.rating!}</p>
                </li>

                <li>
                    <h3>Release Date:</h3> 
                    <p> ${movie.releaseDate!}</p>
                </li>

                <li>
                    <h3>Duration:</h3> 
                    <p> ${movie.duration!}</p>
                </li>

                <li>
                    <h3>Cinematographer(s):</h3>
                    <p> ${movie.cinematographers?join(', ')}</p>
                </li>

                <li>
                    <h3>Director(s):</h3>
                    <p> ${movie.directors?join(', ')}</p>
                </li>

                <li>
                    <h3>Screenwriter(s):</h3>
                    <p> ${movie.screenwriters?join(', ')}</p>
                </li>

                <li>
                    <h3>Producer(s):</h3>
                    <p>${movie.producers?join(', ')}</p>
                </li>

                <li>
                    <h3>Editor(s):</h3>
                    <p> ${movie.editors?join(', ')}</p>
                </li>

                <li>
                    <h3>Music Composer(s):</h3>
                    <p>${movie.music_composers?join(', ')}</p>
                </li>

                <li style="display: block">
                    <h3>Summary:</h3>
                    <p>${movie.summary!}</p>
                </li>
            </ul>
        </div>

    </section>
</@layout.base>