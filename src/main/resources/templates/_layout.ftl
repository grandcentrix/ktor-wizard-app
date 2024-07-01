<#assign userSession = "null">

<#macro base>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>Wizard</title>
        <link href="/static/style.css" rel="stylesheet" />
        <script src="https://unpkg.com/htmx.org@2.0.0" integrity="sha384-wS5l5IKJBvK6sPTKa2WZ1js3d947pvWXbPJ1OmWfEuxLgeHcEbjUUA5i9V5ZkpCw" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    </head>
    <body>
    <header>
        <#if userSession == "null">
            <nav class="user-menu">
                <!-- Profile Picture Dropdown Section -->
                <div class="dropdown" id="profile-dropdown">
                    <a href="#">
                        <img class="profile-picture" id="profile-pic" src="https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg" alt="Profile Picture">
                    </a>
                    <div class="dropdown-content">
                        <a href="/signup">Signup</a>
                        <a href="/login">Login</a>
                        <!-- Add more options here -->
                    </div>
                    <!-- End of Profile Picture Dropdown Section -->
                </div>
            </nav>
        <#else>
            <nav class="user-menu">
                <!-- Profile Picture Dropdown Section -->
                <div class="dropdown tooltip" id="profile-dropdown">
                    <a href="#">
                        <img class="profile-picture" id="profile-pic" src="${"/profile-picture"}" alt="Profile Picture">
                    </a>
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
                        <!-- Add more options here -->
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
            <input name="search" placeholder="Search something..." type="text" id="search-input">
        </label>

        <script>
            const searchInput = document.getElementById('search-input');
            const routes = {
                "books": "/books",
                "houses": "/houses",
                "characters": "/characters",
                "movies": "/movies",
                "potions": "/potions",
                "spells": "/spells"
            };

            searchInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    const inputValue = searchInput.value.toLowerCase();
                    if (routes.hasOwnProperty(inputValue)) {
                        window.location.href = routes[inputValue];
                    }
                }
            });
        </script>
        <div class="bg-effect"></div>
    </header>

    <section class="container">
        <#nested>
    </section>
    </body>
    </html>
</#macro>