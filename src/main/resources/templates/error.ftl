<#import "_layout.ftl" as layout />


<@layout.base>
    <h2>${errorMessage}</h2>

    <section class="content">
        <a class="button" href="${redirectLink}">Back</a>
    </section>
</@layout.base>