<#global userSession = "null">
<#global profilePicture = "/static/img/no_profile_picture.png">

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
                <div class="dropdown tooltip" id="profile-dropdown">
                    <a href="#">
                        <img class="profile-picture" id="profile-pic" src="${profilePicture}" alt="Profile Picture">
                    </a>
                    <#if userSession == "null">
                        <div class="dropdown-content">
                            <a href="/signup">Signup</a>
                            <a href="/login">Login</a>
                        </div>
                        <!-- End of Profile Picture Dropdown Section -->
                    <#elseif userSession != "null">
                        <div class="tooltip">
                            <div class="tooltiptext">
                                <#if username??>
                                    <p>Username: ${username}
                                        <#if house??>
                                            <img src="/static/img/${house}_symbol.png" alt="${house} Symbol" style="width: 20px; height: 20px; vertical-align: middle;">
                                        </#if>
                                    </p>
                                </#if>
                            </div>
                        </div>
                        <div class="dropdown-content">
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
                <form id="search-form" hx-post="/search-redirect" hx-target="#search-results"  hx-select="#search-results">
                    <input name="search" placeholder="Search something..." type="text" id="search-input" list="search-options"
                           hx-get="/search-suggestions" hx-trigger="input" hx-target="#search-options">
                    <datalist id="search-options"></datalist>
                </form>
            </label>
            <div class="bg-effect"></div>
        </header>


        <section class="container" id="search-results">
            <#nested>
        </section>

</#macro>