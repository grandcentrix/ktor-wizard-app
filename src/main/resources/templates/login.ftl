<#import "_layout.ftl" as layout />
<@layout.base>

<h1>Login</h1>
<section class="content">
    <form action="/login" method="POST">
        <label>
            <input placeholder="Username" type="text" name="username">
        </label>

        <label>
            <input placeholder="Password" type="password" name="password">
        </label>

        <input class="button" type="submit">
        <p>${loginStatus}</p>
    </form>
    <a href="/signup">Create account</a>
</section>

</@layout.base>