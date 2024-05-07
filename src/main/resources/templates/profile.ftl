<#import "_layout.ftl" as layout />
<#assign userSession = userSession in layout>

<@layout.base>

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

        <!-- Form for updating password -->
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
            <form id="upload-form" enctype="multipart/form-data" action="/update-profile-picture" method="POST">
                <input type="file" id="picture-upload" name="profilePicture" style="display: none;">
                <button type="button" id="upload-button">Upload Picture</button>
            </form>
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

            // Fetch profile picture URL from the server
            fetch('/profile-picture') // Change the endpoint to match your backend route
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to fetch profile picture URL');
                    }
                })
                .then(data => {
                    if (data.profilePictureUrl) {
                        profilePic.src = data.profilePictureUrl;
                    } else {
                        // Set a default profile picture if no URL is returned
                        profilePic.src = 'default-profile-picture.jpg';
                    }
                })
                .catch(error => {
                    console.error(error);
                    // Handle errors
                });

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

                        // Create a FormData object to send the file data
                        var formData = new FormData();
                        formData.append('profilePicture', file);

                        // Send the image data to the backend
                        fetch('/update-profile-picture', {
                            method: 'POST',
                            body: formData
                        }).then(response => {
                            if (response.ok) {
                                return response.json();
                            } else {
                                throw new Error('Failed to upload profile picture');
                            }
                        }).then(data => {
                            console.log(data); // Log response from the server
                            // Optionally handle response data
                        }).catch(error => {
                            console.error(error);
                            // Handle errors
                        });
                    };

                    reader.readAsDataURL(file);
                    document.body.removeChild(fileInput);
                });
            });

            removePictureButton.addEventListener('click', function() {
                profilePic.src = ""; // Set profile picture to blank
                // Send request to remove profile picture
                fetch('/remove-profile-picture', {
                    method: 'POST'
                }).then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to remove profile picture');
                    }
                }).then(data => {
                    console.log(data); // Log response from the server
                    // Optionally handle response data
                }).catch(error => {
                    console.error(error);
                    // Handle errors
                });
            });
        });
    </script>


</@layout.base>
