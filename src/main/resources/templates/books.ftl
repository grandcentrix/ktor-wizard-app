<#import "_layout.ftl" as layout />
<#assign userSession = userSession in layout>
<#assign gravatar = avatar in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Books
    </h1>
    <section class="content">
        <ul class="content-list">
            <#list books as book>
                <li><a href="/books/${book.slug}">${book.title}</a></li>
            </#list>
        </ul>
    </section>

</@layout.base>