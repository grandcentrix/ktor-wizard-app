<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Books
    </h1>
    <section class="content">
        <ul class="content-list">
            <#list books as book>
                <li>
                    <img alt="" class="content-img" src="${book.coverUrl}" />
                    <div class="list-item">

                        <div class="list-item-name">
                            <a href="/books/${book.slug}">
                                ${book.title}
                            </a>
                        </div>


                        <div class="favourite-items">
                            <#if session == "null">
                                <a href="/login" class="favourite-button">
                                    <span class="material-symbols-outlined favourite-button-icon-white" style="font-size: 30px;">favorite</span>
                                </a>

                            <#else>
                                    <button
                                            id="favourite-items-button"
                                            hx-post="/books/${book.id}/favourite"
                                            class="favourite-button"
                                            type="submit"
                                    >
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
                                            <span class="material-symbols-outlined favourite-button-icon-white liked"  style="font-size: 30px;">favorite</span>
                                        </#if>
                                    </button>
                            </#if>
                        </div>
                    </div>
                </li>
            </#list>
        </ul>
    </section>

    <!-- Invisible iframe to handle form submissions without page reload -->
<#--    <iframe id="invisible" name="invisible" style="display: none;"></iframe>-->

<script>
    document.addEventListener('htmx:afterRequest', function(event) {
        if (event.detail.elt.id === 'favourite-items-button') {
            document.getElementById('favourite-items-button').classList.toggle('unsaved');
        }
    });
</script>
</@layout.base>