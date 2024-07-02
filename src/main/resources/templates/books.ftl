<#import "_layout.ftl" as layout />
<#assign userSession = userSession in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Books
    </h1>
    <section class="content">
        <ul class="content-list">
            <#list books as book>
                <li>
                    <form method="POST" action="/books/${book.id}/favourite" class="favourite-items-form" target="invisible">
                        <button class="favourite-button" type="submit">
                            <#assign isFavourite = "false">

                            <#list userFavourites>
                                <#items as favouriteItem>
                                    <#if favouriteItem == book["id"]>
                                        <#assign isFavourite = "true">
                                    </#if>
                                </#items>
                            </#list>

                            <#if isFavourite == "true">
                                <span class="material-symbols-outlined favourite-button-icon-red" style="font-size: 30px;">favorite</span>
                            <#else>
                                <span class="material-symbols-outlined favourite-button-icon-white" style="font-size: 30px;">favorite</span>
                            </#if>
                        </button>
                    </form>
                    <img alt="" class="content-img" src="${book.coverUrl}" />
                    <a href="/books/${book.slug}">${book.title}</a>
                </li>
            </#list>
        </ul>
    </section>

    <!-- Invisible iframe to handle form submissions without page reload -->
    <iframe id="invisible" name="invisible" style="display: none;"></iframe>

</@layout.base>