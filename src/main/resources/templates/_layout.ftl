<#assign userSession = "null">
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
        <#if userSession == "null">
            <nav class="user-menu">
                <!-- Profile Picture Dropdown Section -->
                <div class="dropdown" id="profile-dropdown">
                    <img class="profile-picture" id="profile-pic" src="https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg" alt="Profile Picture">
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
                <div class="dropdown" id="profile-dropdown">
                    <img class="profile-picture" id="profile-pic" src="https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg" alt="Profile Picture">
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

    <!-- JavaScript for Dropdown Functionality -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var profilePic = document.getElementById('profile-pic');
            var defaultProfilePic = "https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg";

            <#if userSession != "null">
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
            </#if>

            var dropdownContent = document.querySelector('.dropdown-content');

            profilePic.addEventListener('click', function(event) {
                event.stopPropagation();
                dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
            });

            document.getElementById('profile-dropdown').addEventListener('click', function(event) {
                event.stopPropagation();
            });

            document.addEventListener('click', function(event) {
                if (!profilePic.contains(event.target)) {
                    dropdownContent.style.display = 'none';
                }
            });
        });
    </script>
    </body>
    </html>
</#macro>
