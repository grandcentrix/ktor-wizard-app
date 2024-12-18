<#import "_layout.ftl" as layout />

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">house</span>
        Houses
    </h1>
    <section class="content">
        <ul class="content-list">
            <#list houses as house>
                <li><a href="/houses/${house.name}">${house.name}</a></li>
            </#list>
        </ul>
    </section>

</@layout.base>