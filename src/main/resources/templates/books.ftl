<#import "_layout.ftl" as layout />

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Books
    </h1>
    <section class="content">
        <ul>
            <#list books as book>
                <li>${book.title}</li>
            </#list>
        </ul>
    </section>

</@layout.base>