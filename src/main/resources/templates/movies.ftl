<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">movie</span>
        Movies
    </h1>
    <section class="content">
        <ul>
            <#list movies as movie>
                <li>${movie.title}</li>
            </#list>
        </ul>
    </section>

</@layout.base>