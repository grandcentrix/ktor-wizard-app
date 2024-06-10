<#import "_layout.ftl" as layout />
<#--<#assign redirectLink = "null">-->
<#--<#assign userSession = userSession in layout>-->


<@layout.base>
    <h2>${errorMessage}</h2>

    <section class="content">
        <a class="button" href="${redirectLink}">Back</a>
    </section>
</@layout.base>