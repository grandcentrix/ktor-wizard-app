<#assign userSession = "null">

<#macro base>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>Wizard</title>
        <link href="/static/style.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <script src="https://unpkg.com/htmx.org@1.9.12"></script>
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
                        <img class="profile-picture" id="profile-pic" src="https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg" alt="Profile Picture">
                    </a>
                    <div class="tooltip" hx-get="/hogwarts-house" hx-swap="outerHTML">
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
            <input name="search" placeholder="Search something..." type="text">
        </label>
        <div class="bg-effect"></div>
    </header>

    <section class="container">
        <#nested>
    </section>

    <!-- JavaScript for Fetching Profile Picture (if userSession is not null) -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var profilePic = document.getElementById('profile-pic');
            var tooltipText = document.querySelector('.tooltiptext');
            var defaultProfilePic = "https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg";

            <#if userSession!= "null">
            fetch('/profile-picture', {
                method: 'GET'
            }).then(response => {
                if (response.ok) {
                    return response.blob();
                } else {
                    throw new Error('Failed to fetch profile picture');
                }
            }).then(blob => {
                if (blob.size > 0) {
                    const imageUrl = URL.createObjectURL(blob);
                    profilePic.src = imageUrl;
                } else {
                    profilePic.src = defaultProfilePic;
                }
            }).catch(error => {
                console.error(error);
                profilePic.src = defaultProfilePic;
            });

            if (tooltipText) {
                // Add CSS styles to center the tooltip text under the profile picture
                tooltipText.style.position = 'absolute';
                tooltipText.style.top = '100%'; // Position it below the parent element
                tooltipText.style.left = '50%'; // Center it horizontally
                tooltipText.style.transform = 'translateX(-50%)'; // Center it exactly under the profile picture
                tooltipText.style.textAlign = 'center'; // Align the text to the center
            }
            </#if>
        });
    </script>
    </body>
    </html>
</#macro>