<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">movie</span>
        Movies
    </h1>
    <section class="content">
        <ul id="movies-list" class="content-list">
            <#list movies as movie>
                <li>
                    <img alt="" class="content-img" src="${movie.posterUrl}" />
                    <div class="list-item">

                        <div class="list-item-name">
                            <a href="/movies/${movie.slug}">
                                ${movie.title}
                            </a>
                        </div>

                        <div class="favourite-items">
                            <#if session == "null">
                                <a href="/login" class="favourite-button">
                                    <span class="material-symbols-outlined favourite-button-icon-white" style="font-size: 30px;">favorite</span>
                                </a>
                            <#else>
                                <#assign isFavourite = "false">
                                <#list userFavourites>
                                    <#items as favouriteItem>
                                        <#if favouriteItem == movie["id"]>
                                            <#assign isFavourite = "true">
                                        </#if>
                                    </#items>
                                </#list>

                                <#if isFavourite == "true">
                                    <div class="liked-button">
                                        <button
                                                id="favourite-items-button"
                                                hx-delete="/movies/${movie.id}/favourite"
                                                hx-target="#movies-list"
                                                hx-select="#movies-list"
                                                class="favourite-button"
                                                type="submit"
                                        >
                                            <span class="material-symbols-outlined favourite-button-icon-red" style="font-size: 30px;">favorite</span>
                                        </button>
                                    </div>
                                <#else>
                                    <div class="unliked-button">
                                        <button
                                                id="favourite-items-button-2"
                                                hx-post="/movies/${movie.id}/favourite"
                                                hx-target="#movies-list"
                                                hx-select="#movies-list"
                                                class="favourite-button unliked"
                                                type="submit"
                                        >
                                            <span class="material-symbols-outlined favourite-button-icon-white"  style="font-size: 30px;">favorite</span>
                                        </button>
                                    </div>
                                </#if>
                            </#if>
                        </div>
                    </div>
                </li>
            </#list>
        </ul>
    </section>

</@layout.base>