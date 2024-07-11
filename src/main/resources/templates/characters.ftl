<#import "_layout.ftl" as layout />
<#import "pagination.ftl" as pages>

<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>
<#assign route = "/characters" in pages>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">person</span>
        Characters
    </h1>
    <section class="content">
        <ul id="characters-list" class="content-list">
            <#list characters as character>
                <li>
<#--                    <img alt="" class="content-img" src="${character.imageUrl}" />-->
                    <div class="list-item">

                        <div class="list-item-name">
                            <a href="/characters/${character.slug}">
                                ${character.name}
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
                                        <#if favouriteItem == character["id"]>
                                            <#assign isFavourite = "true">
                                        </#if>
                                    </#items>
                                </#list>

                                <#if isFavourite == "true">
                                    <div class="liked-button">
                                        <button
                                                id="favourite-items-button"
                                                hx-delete="/characters/${character.id}/favourite"
                                                hx-target="#characters-list"
                                                hx-select="#characters-list"
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
                                                hx-post="/characters/${character.id}/favourite"
                                                hx-target="#characters-list"
                                                hx-select="#characters-list"
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
        <@pages.base></@pages.base>
    </section>

</@layout.base>