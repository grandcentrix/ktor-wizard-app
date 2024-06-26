<#global userSession = "null">
<#global gravatar = "null">

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
        <#if userSession == "null">
            <nav class="user-menu">
                <!-- Profile Picture Dropdown Section -->
                <div class="dropdown" id="profile-dropdown">
                    <img class="profile-picture" id="profile-pic" src="/static/img/no_profile_picture.png" alt="Profile Picture">
                    <div class="dropdown-content">
                        <a href="/signup">Signup</a>
                        <a href="/login">Login</a>
                    </div>
                    <!-- End of Profile Picture Dropdown Section -->
                </div>
            </nav>
        <#elseif userSession != "null" && (gravatar == "null")>
            <nav class="user-menu">
                <!-- Profile Picture Dropdown Section -->
                <div class="dropdown tooltip" id="profile-dropdown">
                    <img class="profile-picture" id="profile-pic" src="/static/img/no_profile_picture.png" alt="Profile Picture">
                    <div class="tooltip">
                        <div class="tooltiptext">
                            <#if username??>
                                <p>Username: ${username}
                                    <#if house??>
                                        <img src="/static/img/${house} house symbole.png" alt="House Symbol" style="width: 20px; height: 20px; vertical-align: middle;">
                                    </#if>
                                </p>
                            </#if>
                        </div>
                    </div>
                    <div class="dropdown-content">
                        <a href="/profile#favourites">Favourites</a>
                        <a href="/logout">Logout</a>
                    </div>
                    <!-- End of Profile Picture Dropdown Section -->
                </div>
            </nav>
        <#else>
            <nav class="user-menu">
                <!-- Profile Picture Dropdown Section -->
                <div class="dropdow tooltip" id="profile-dropdown">
                    <img class="profile-picture" id="profile-pic" src="${gravatar}" alt="Profile Picture">
                    <div class="tooltip">
                        <div class="tooltiptext">
                            <#if username??>
                                <p>Username: ${username}
                                    <#if house??>
                                        <img src="/static/img/${house} house symbole.png" alt="House Symbol" style="width: 20px; height: 20px; vertical-align: middle;">
                                    </#if>
                                </p>
                            </#if>
                        </div>
                    </div>
                    <div class="dropdown-content">
                        <a href="/profile#favourites">Favourites</a>
                        <a href="/logout">Logout</a>
                    </div>
                    <!-- End of Profile Picture Dropdown Section -->
                </div>
            </nav>
        </#if>

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