<#macro base>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>Wizard</title>
        <link href="/static/style.css" rel="stylesheet" />
        <link rel="stylesheet"
              href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
        />
        <script src="https://unpkg.com/htmx.org@2.0.0"
                integrity="sha384-wS5l5IKJBvK6sPTKa2WZ1js3d947pvWXbPJ1OmWfEuxLgeHcEbjUUA5i9V5ZkpCw"
                crossorigin="anonymous">
        </script>
    </head>
    <body>
        <header>
            <nav class="user-menu">
                <!-- Profile Picture Dropdown Section -->
                <div class="dropdown" id="profile-dropdown">
                    <a href="#">
                        <#if profilePictureData??>
                            <img class="profile-picture" id="profile-pic" src="${profilePictureData}" alt="Profile Picture">
                        </#if>
                    </a>
                    <#if !userSession??>
                        <div class="dropdown-content">
                            <a href="/signup">Signup</a>
                            <a href="/login">Login</a>
                        </div>
                        <!-- End of Profile Picture Dropdown Section -->
                    <#elseif userSession??>
                        <#if house??>
                            <img class="user-house-icon" src="/static/img/${house}_logo.webp" alt="${house} Symbol">
                        </#if>
                        <div class="dropdown-content">
                            <p>Hello, ${userSession.username}!</p>
                            <a href="/profile#favourites">Favourites</a>
                            <a href="/logout">Logout</a>
                        </div>
                    </#if>
                </div>
            </nav>

        <h1 class="logo">
            <a href="/">Wizard</a>
        </h1>
        <menu class="main-menu">
            <li><a href="/">Home</a></li>
            <li style="padding: 0 10px;">//</li>
            <li><a href="/profile">My account</a></li>
        </menu>
        <label class="search-bar">
            <span class="material-symbols-outlined">search</span>
            <input name="search" placeholder="Search something..." type="text">
        </label>
    <div class="bg-effect"></div>
    </header>

    <section class="container">
        <#nested>
    </section>
</#macro>