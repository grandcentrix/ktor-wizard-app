<#import "_layout.ftl" as layout />

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">person</span>
        Characters
    </h1>
    <section class="content">
        <ul>
            <#list characters as character>
                <li>${character.name}</li>
            </#list>
        </ul>
    </section>

</@layout.base>