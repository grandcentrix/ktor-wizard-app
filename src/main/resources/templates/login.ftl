<#import "_layout.ftl" as layout />
<@layout.base>

<h1>
    <span class="material-symbols-outlined">login</span>
    Login
</h1>

    <form class="form" action="/login" method="POST">

        <label>
            <input placeholder="Username" type="text" name="username">
        </label>

        <label>
            <input placeholder="Password" type="password" name="password">
        </label>

        <input class="button" type="submit">
        <p style="color: #dab6bd; margin-left:15px;">${loginStatus}</p>
    </form>

    <a class="links" href="/signup">
        Create account
        <span class="material-symbols-outlined">north_east</span>
    </a>

</@layout.base>