<#import "_layout.ftl" as layout /> <!-- Importing the "_layout.ftl" template file -->
<#assign userSession = userSession in layout>

<@layout.base> <!-- Calling the "base" macro defined in the imported "_layout.ftl" template -->

    <h1>
        <span class="material-symbols-outlined">account_box</span>
        Welcome, ${username}!
    </h1>

    <section class="content" style="flex-direction: column">
        <!-- Form for updating username -->
        <div class="user-data">
            <form action="/update-username" method="POST">
                <label for="new-username">New Username:</label>
                <input type="text" id="new-username" name="newUsername" required>
                <input type="submit" value="Update Username">
            </form>
        </div>

        <!-- Form for updating email -->
        <div class="user-data">
            <form action="/update-email" method="POST">
                <label for="new-email">New Email:</label>
                <input type="email" id="new-email" name="newEmail" required>
                <input type="submit" value="Update Email">
            </form>
        </div>

        <div class="user-data">
            <form action="/update-password" method="POST">
                <label for="new-password">New Password:</label>
                <input type="password" id="new-password" name="newPassword" required>
                <input type="submit" value="Update Password">
            </form>
        </div>
    </section>

    <section class="content" style="flex-direction: column">
        <!-- Upload profile picture -->
        <div class="user-data">
            <p>Profile Picture:</p>
            <button id="upload-button">Upload Picture</button>
            <button id="remove-picture">Remove Picture</button>
        </div>

        <!-- Delete Account button -->
        <div class="delete-button">
            <form class="form" action="/delete-account" method="POST" onsubmit="return confirmDelete()">
                <input class="button" style="margin-left: 0;" type="submit" value="Delete Account">
            </form>
        </div>
    </section>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var uploadButton = document.getElementById('upload-button');
            var removePictureButton = document.getElementById('remove-picture');
            var profilePic = document.getElementById('profile-pic');

            uploadButton.addEventListener('click', function() {
                var fileInput = document.createElement('input');
                fileInput.type = 'file';
                fileInput.style.display = 'none';
                document.body.appendChild(fileInput);
                fileInput.click();

                fileInput.addEventListener('change', function(event) {
                    var file = event.target.files[0];
                    var reader = new FileReader();

                    reader.onload = function(event) {
                        var imageDataUrl = event.target.result;
                        profilePic.src = imageDataUrl;
                    };

                    reader.readAsDataURL(file);
                    document.body.removeChild(fileInput);
                });
            });

            removePictureButton.addEventListener('click', function() {
                // Set profile picture to the standard one
                profilePic.src = "https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg";
                // You may need to clear any uploaded picture data stored on the server here
            });
        });

        function confirmDelete() {
            return confirm("Are you sure you want to delete your account? This action cannot be undone!");
        }
    </script>

</@layout.base> <!-- Closing the "base" macro -->
