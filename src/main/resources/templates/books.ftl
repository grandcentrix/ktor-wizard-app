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
                    <form method="POST" action="/favourite/book/${book.id}" class="favourite-items-form">
                        <button class="favourite-button" type="submit">
                            <span class="material-symbols-outlined" style="font-size: 30px">favorite</span>
                        </button>
                    </form>
                    <img alt="" class="content-img" src="${book.coverUrl}" />
                    <a href="/books/${book.slug}">${book.title}</a>
                </li>
            </#list>
        </ul>
    </section>

</@layout.base>