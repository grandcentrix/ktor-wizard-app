<#import "_layout.ftl" as layout />
<#assign userSession = userSession in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">stockpot</span>
        Potions
    </h1>
    <section class="content">
        <ul>
            <#list potions as potion>
                <li>${potion.name}</li>
            </#list>
        </ul>
    </section>

</@layout.base>