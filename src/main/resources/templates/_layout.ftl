<#macro base>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>Wizard</title>
        <link href="/static/style.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
        <style>
            /* Dropdown Menu Styles */
            .dropdown {
                position: relative;
                display: inline-block;
            }
            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }
            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }
            .dropdown-content a:hover {
                background-color: #f1f1f1;
            }
            /* Ensure the dropdown remains open when hovering over it */
            .dropdown:hover .dropdown-content {
                display: block;
            }
            /* Hide the dropdown content when the mouse leaves the dropdown area */
            .dropdown-content {
                position: absolute;
                z-index: 1;
            }
            .dropdown:hover .dropdown-content {
                display: block;
            }
            /* Allow user to interact with dropdown content */
            .dropdown-content:hover {
                display: block;
            }
            /* Hide dropdown content when mouse leaves the dropdown area */
            .dropdown:hover .dropdown-content:not(:hover) {
                display: none;
            }
        </style>
    </head>
    <body>
    <header>
        <h1 class="logo">
            <a href="/">Wizard</a>
        </h1>
        <menu class="main-menu">
            <li><a href="/">Home</a></li>
            <li style="padding: 0 10px;">//</li>
            <li><a href="/profile">My account</a></li>
            <li style="padding: 0 10px;">//</li>
            <li><a href="/logout">Logout</a></li>
        </menu>
        <label class="search-bar">
            <span class="material-symbols-outlined">search</span>
            <input src="" name="search" placeholder="Search something..." type="text">
        </label>
        <div class="bg-effect"></div>
    </header>

    <section class="container">
        <!-- Profile Picture Dropdown Section -->
        <div class="dropdown" id="profile-dropdown">
            <img class="profile-picture" id="profile-pic" src="https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg" alt="Profile Picture">
            <div class="dropdown-content">
                <a href="/profile">Profile</a>
                <a href="/logout">Logout</a>
                <!-- Add more options here -->
            </div>
        </div>
        <!-- End of Profile Picture Dropdown Section -->
        <#nested>
    </section>

    <!-- JavaScript for Dropdown Functionality -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var dropdownContent = document.querySelector('.dropdown-content');
            document.getElementById('profile-pic').addEventListener('click', function(event) {
                event.stopPropagation(); // Prevents the click event from propagating to the document
                dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
            });
            document.getElementById('profile-dropdown').addEventListener('click', function(event) {
                event.stopPropagation(); // Prevents the click event from propagating to the document
            });

            // Close dropdown when clicking outside
            document.addEventListener('click', function(event) {
                if (!document.getElementById('profile-pic').contains(event.target)) {
                    dropdownContent.style.display = 'none';
                }
            });
        });
    </script>
    </body>
    </html>
</#macro>