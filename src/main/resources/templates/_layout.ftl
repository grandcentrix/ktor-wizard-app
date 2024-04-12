<#macro base>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>Wizard</title>
        <link href="/static/style.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    </head>
    <body>
    <header>
        <h1 class="logo">
            <a href="/">Wizard</a>
        </h1>
        <menu class="main-menu">
            <li><a href="">Home</a></li>
            <li style="padding: 0 10px;">//</li>
            <li><a href="">My account</a></li>
        </menu>
        <label class="search-bar">
            <span class="material-symbols-outlined">search</span>
            <input src="" name="search" placeholder="Search something..." type="text">
        </label>
        <div class="bg-effect"></div>
    </header>

    <section class="container">
        <#nested>
    </section>
    </body>
    </html>
</#macro>