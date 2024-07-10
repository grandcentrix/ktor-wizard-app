<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Books
    </h1>
    <section class="content">
        <ul id="books-list" class="content-list">
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
                                <#assign isFavourite = "false">
                                <#list userFavourites>
                                    <#items as favouriteItem>
                                        <#if favouriteItem == book["id"]>
                                            <#assign isFavourite = "true">
                                        </#if>
                                    </#items>
                                </#list>

                                <#if isFavourite == "true">
                                    <div class="liked-button">
                                        <button
                                                id="favourite-items-button"
                                                hx-delete="/books/${book.id}/favourite"
                                                hx-target="#books-list"
                                                hx-select="#books-list"
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
                                                hx-post="/books/${book.id}/favourite"
                                                hx-target="#books-list"
                                                hx-select="#books-list"
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
        <footer class="pagination">
            <ul>
                <#if (pagination.first)??>
                    <li>${pagination.first}</li>
                </#if>

                <#if (pagination.previous)??>
                <li>${pagination.previous}</li>
                </#if>

                <#if (pagination.current)??>
                    <li>${pagination.current}</li>
                </#if>

                <#if (pagination.next)??>
                    <li>${pagination.next}</li>
                </#if>

                <#if (pagination.last)??>
                    <li>${pagination.last}</li>
                </#if>

<#--                <li>${pagination.records}</li>-->
            </ul>
        </footer>
    </section>

</@layout.base>