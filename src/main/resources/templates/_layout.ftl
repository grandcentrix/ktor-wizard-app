<#assign userSession = "null">
<#assign gravatar = "null">

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
                    <img class="profile-picture" id="profile-pic" src="${gravatar}" alt="Profile Picture">
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
            <input src="" name="search" placeholder="Search something..." type="text">
        </label>
        <div class="bg-effect"></div>
    </header>

    <section class="container">
        <#nested>
    </section>

    <!-- JavaScript for Dropdown Functionality -->
    <script>
        // This event listener waits for the DOMContentLoaded event, which starts when the initial HTML document has been completely loaded and parsed.
        document.addEventListener('DOMContentLoaded', function() {
            // This line selects the dropdown content element using its class name.
            var dropdownContent = document.querySelector('.dropdown-content');

            // This line adds a click event listener to the profile picture element.
            document.getElementById('profile-pic').addEventListener('click', function(event) {
                // This line prevents the click event from propagating to the document, which avoids closing the dropdown when clicking inside it.
                event.stopPropagation();
                // This line toggles the display style of the dropdown content between 'block' and 'none' when the profile picture is clicked.
                dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
            });

            // This line adds a click event listener to the profile dropdown element.
            document.getElementById('profile-dropdown').addEventListener('click', function(event) {
                // This line prevents the click event from propagating to the document, which avoids closing the dropdown when clicking inside it.
                event.stopPropagation();
            });

            // This comment indicates the purpose of the following code block.
            // Close dropdown when clicking outside
            // This line adds a click event listener to the entire document.
            document.addEventListener('click', function(event) {
                // This line checks if the clicked element is not within the profile picture element.
                if (!document.getElementById('profile-pic').contains(event.target)) {
                    // This line hides the dropdown content if the clicked element is not within the profile picture element.
                    dropdownContent.style.display = 'none';
                }
            });
        });

    </script>
    </body>
    </html>
</#macro>