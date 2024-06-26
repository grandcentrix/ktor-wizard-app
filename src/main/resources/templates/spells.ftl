<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">flare</span>
        Spells
    </h1>
    <section class="content">
        <ul>
            <#list spells as spell>
                <li>${spell.name}</li>
            </#list>
        </ul>
    </section>

</@layout.base>