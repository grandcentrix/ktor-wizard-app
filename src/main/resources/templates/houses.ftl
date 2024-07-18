<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">house</span>
        Houses
    </h1>
    <section class="content">
        <ul id="houses-list" class="content-list">
            <#list houses as house>
                <li class="houses">
                    <div class="list-item">

                        <div class="list-item-name">
                            <a href="/houses/${house.id}">
                                ${house.name}
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
                                        <#if favouriteItem == house["id"]>
                                            <#assign isFavourite = "true">
                                        </#if>
                                    </#items>
                                </#list>

                                <#if isFavourite == "true">
                                    <div class="liked-button">
                                        <button
                                                id="favourite-items-button"
                                                hx-delete="/houses/${house.id}/favourite"
                                                hx-target="#houses-list"
                                                hx-select="#houses-list"
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
                                                hx-post="/houses/${house.id}/favourite"
                                                hx-target="#houses-list"
                                                hx-select="#houses-list"
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