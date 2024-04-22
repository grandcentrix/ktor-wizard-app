<#import "_layout.ftl" as layout />

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">house</span>
        Houses
    </h1>
    <section class="content">
        <ul>
            <#list houses as house>
                <li>${house.name}</li>
            </#list>
        </ul>
    </section>

</@layout.base>