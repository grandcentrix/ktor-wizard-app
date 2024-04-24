<#import "_layout.ftl" as layout />
<@layout.base>

    <h1>
        <span class="material-symbols-outlined">logout</span>
        Logout
    </h1>

    <form class="form" action="/logout" method="POST">
        <input class="button" type="submit" value="Logout">
    </form>

    <a class="links" href="/login">
        <span class="material-symbols-outlined">north_east</span>
    </a>

</@layout.base>
