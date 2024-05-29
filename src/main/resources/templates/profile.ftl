<#import "_layout.ftl" as layout /> <!-- Importing the layout template -->
<#assign userSession = userSession in layout> <!-- Assigning the user session variable from the layout -->

<@layout.base> <!-- Including the base layout -->

    <h1>
        <span class="material-symbols-outlined">account_box</span>
        Welcome, ${username}! <!-- Displaying the username -->
    </h1>

    <section class="content" style="flex-direction: column">
        <!-- Form for updating username -->
        <div class="user-data">
            <form onsubmit="updateUsername(event)">
                <label for="new-username">New Username:</label>
                <input type="text" id="new-username" name="newUsername" required>
                <input type="submit" value="Update Username">
            </form>
        </div>

        <!-- Form for updating email -->
        <div class="user-data">
            <form onsubmit="updateEmail(event)">
                <label for="new-email">New Email:</label>
                <input type="email" id="new-email" name="newEmail" required>
                <input type="submit" value="Update Email">
            </form>
        </div>

        <!-- Form for updating password -->
        <div class="user-data">
            <form onsubmit="updatePassword(event)">
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
            <form class="form" id="delete-account-form" onsubmit="return confirmDelete(event)">
                <input class="button" style="margin-left: 0;" type="submit" value="Delete Account">
            </form>
        </div>
    </section>

    <!-- JavaScript section -->
    <script>
        function updateUsername(event) {
            event.preventDefault(); // Prevent the default form submission behavior
            const form = event.target; // Get the form element that triggered the event
            const formData = new FormData(form); // Create a FormData object from the form
            const newUsername = formData.get("newUsername"); // Extract the new username from the FormData

            fetch('/user/username', { // Send a request to the '/user/username' endpoint
                method: 'PUT', // Use the PUT method to update the resource
                body: new URLSearchParams({ newUsername: newUsername }) // Set the body of the request to the new username
            }).then(response => {
                if (response.ok) { // Check if the response status is OK (200-299)
                    window.location.href = '/profile'; // Redirect to the profile page on success
                } else {
                    return response.text().then(text => { throw new Error(text) }); // Throw an error if the response is not OK
                }
            }).catch(error => {
                console.error('Error:', error); // Log any errors to the console
                alert('Failed to update username: ' + error.message); // Show an alert with the error message
            });
        }


        function updateEmail(event) {
            event.preventDefault();
            const form = event.target;
            const formData = new FormData(form);
            const newEmail = formData.get("newEmail");

            fetch('/user/email', {
                method: 'PUT',
                body: new URLSearchParams({ newEmail: newEmail })
            }).then(response => {
                if (response.ok) {
                    window.location.href = '/profile';
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            }).catch(error => {
                console.error('Error:', error);
                alert('Failed to update email: ' + error.message);
            });
        }

        document.addEventListener('DOMContentLoaded', function() {
            var uploadButton = document.getElementById('upload-button'); // Get the upload button element
            var removePictureButton = document.getElementById('remove-picture'); // Get the remove picture button element
            var profilePic = document.getElementById('profile-pic'); // Get the profile picture element

            // Event listener for upload button
            uploadButton.addEventListener('click', function() {
                var fileInput = document.createElement('input');
                fileInput.type = 'file';
                fileInput.style.display = 'none';
                document.body.appendChild(fileInput);
                fileInput.click();

                fileInput.addEventListener('change', function(event) {
                    var file = event.target.files[0];

                    // Check if file size is greater than 20 MB
                    if (file.size > 20 * 1024 * 1024) {
                        alert('The selected picture is too big. Maximum file size is 20 MB.');
                        return; // Stop further execution
                    }

                    var reader = new FileReader();

                    reader.onload = function(event) {
                        var imageDataUrl = event.target.result;
                        profilePic.src = imageDataUrl;

                        var formData = new FormData();
                        formData.append('profilePicture', file);

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
                            console.log(data);
                        }).catch(error => {
                            console.error(error);
                        });
                    };

                    reader.readAsDataURL(file);
                    document.body.removeChild(fileInput);
                });
            });

            // Event listener for remove picture button
            removePictureButton.addEventListener('click', function() {
                profilePic.src = "https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg";

                fetch('/remove-profile-picture', {
                    method: 'DELETE'
                }).then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to remove profile picture');
                    }
                }).then(data => {
                    console.log(data);
                }).catch(error => {
                    console.error(error);
                });
            });

            // Function to confirm deletion and send DELETE request
            function confirmDelete(event) {
                event.preventDefault();
                var confirmDelete = confirm('Are you sure you want to delete your account?');
                if (confirmDelete) {
                    fetch('/delete-account', {
                        method: 'DELETE',
                    }).then(response => {
                        if (response.ok) {
                            window.location.href = '/logout';
                        } else {
                            return response.text().then(text => { throw new Error(text) });
                        }
                    }).catch(error => {
                        console.error('Error:', error);
                        alert('Failed to delete account: ' + error.message);
                    });
                }
            }

            var deleteAccountForm = document.getElementById('delete-account-form');
            deleteAccountForm.addEventListener('submit', confirmDelete);
        });
    </script>

</@layout.base>