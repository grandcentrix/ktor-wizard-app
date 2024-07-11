<#import "_layout.ftl" as layout />
<#import "pagination.ftl" as pages>

<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>
<#assign route = "/potions" in pages>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">stockpot</span>
        Potions
    </h1>
    <section class="content">
        <ul id="potions-list" class="content-list">
            <#list potions as potion>
                <li>
<#--                    <img alt="" class="content-img" src="${potion.imageUrl}" />-->
                    <div class="list-item">

                        <div class="list-item-name">
                            <a href="/potions/${potion.slug}">
                                ${potion.name}
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
                                        <#if favouriteItem == potion["id"]>
                                            <#assign isFavourite = "true">
                                        </#if>
                                    </#items>
                                </#list>

                                <#if isFavourite == "true">
                                    <div class="liked-button">
                                        <button
                                                id="favourite-items-button"
                                                hx-delete="/potions/${potion.id}/favourite"
                                                hx-target="#potions-list"
                                                hx-select="#potions-list"
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
                                                hx-post="/potions/${spell.id}/favourite"
                                                hx-target="#potions-list"
                                                hx-select="#potions-list"
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