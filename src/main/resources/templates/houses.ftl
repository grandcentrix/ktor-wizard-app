<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">house</span>
        Houses
    </h1>
    <section class="content column">
        <ul id="houses-list" class="content-list">
            <#list houses as house>
                <li class="houses">
                    <div class="list-item">
                        <#if house.name == "Gryffindor">
                            <img src="/static/img/Gryffindor_symbol.png" alt="Gryffindor Symbol" style="width: 100%; height: 100px; object-fit: contain; margin-bottom: 10px;">
                        <#elseif house.name == "Slytherin">
                            <img src="/static/img/Slytherin_symbol.png" alt="Slytherin Symbol" style="width: 100%; height: 100px; object-fit: contain; margin-bottom: 10px;">
                        <#elseif house.name == "Ravenclaw">
                            <img src="/static/img/Ravenclaw_symbol.png" alt="Ravenclaw Symbol" style="width: 100%; height: 100px; object-fit: contain; margin-bottom: 10px;">
                        <#elseif house.name == "Hufflepuff">
                            <img src="/static/img/Hufflepuff_symbol.png" alt="Hufflepuff Symbol" style="width: 100%; height: 100px; object-fit: contain; margin-bottom: 10px;">
                        </#if>

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