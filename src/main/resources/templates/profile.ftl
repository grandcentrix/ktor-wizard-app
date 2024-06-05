<#import "_layout.ftl" as layout />
<#assign userSession = userSession in layout>

<@layout.base>

    <h1>
        <span class="material-symbols-outlined">account_box</span>
        Welcome, ${username}!
    </h1>

    <section class="content" style="flex-direction: column">
        <div class="user-data">
            <form hx-put="/user/username" hx-trigger="submit" hx-target="#message-container-username">
                <label for="new-username">New Username:</label>
                <input type="text" id="new-username" name="newUsername" required>
                <input type="submit" value="Update Username">
                <p id="message-container-username" style="color: #dab6bd; margin-left:15px;"></p>
            </form>
        </div>

        <div class="user-data">
            <form hx-put="/user/email" hx-trigger="submit" hx-target="#message-container-email">
                <label for="new-email">New Email:</label>
                <input type="email" id="new-email" name="newEmail" required>
                <input type="submit" value="Update Email">
                <p id="message-container-email" style="color: #dab6bd; margin-left:15px;"></p>
            </form>
        </div>

        <div class="user-data">
            <form hx-put="/user/password" hx-trigger="submit" hx-target="#message-container-password">
                <label for="new-password">New Password:</label>
                <input type="password" id="new-password" name="newPassword" required>
                <input type="submit" value="Update Password">
                <p id="message-container-password" style="color: #dab6bd; margin-left:15px;"></p>
            </form>
        </div>
    </section>

    <section class="content" style="flex-direction: column">
        <div class="user-data">
            <p>Profile Picture:</p>
            <form id="upload-form" enctype="multipart/form-data" action="/update-profile-picture" method="POST">
                <input type="file" id="picture-upload" name="profilePicture" style="display: none;">
                <button type="button" id="upload-button">Upload Picture</button>
            </form>
            <button hx-delete="/remove-profile-picture" hx-target="#message-container-picture">Remove Picture</button>
            <p id="message-container-picture" style="color: #dab6bd; margin-left:15px;"></p>
        </div>
        <div class="delete-button">
            <form hx-delete="/delete-account" hx-trigger="submit" hx-target="#delete-message-container" onsubmit="return confirm('Are you sure you want to delete your account?');">
                <input class="button" style="margin-left: 0;" type="submit" value="Delete Account">
            </form>
            <p id="delete-message-container" style="color: #dab6bd; margin-left:15px;"></p>
        </div>
    </section>

    <script>
        document.addEventListener('htmx:afterOnLoad', function(event) {
            var targetId = event.detail.target.id;
            var targetElement = document.getElementById(targetId);

            if (targetElement && event.detail.xhr.status >= 200 && event.detail.xhr.status < 300) {
                targetElement.innerText = event.detail.xhr.responseText;
                if (event.detail.successful) {
                    setTimeout(function() {
                        location.reload();
                    }, 1000);
                }
            } else if (targetElement) {
                targetElement.innerText = event.detail.xhr.responseText;
            }
        });

        document.addEventListener('htmx:afterOnLoad', function(event) {
            if (event.detail.target.id.startsWith('delete-message-container') && event.detail.successful) {
                setTimeout(function() {
                    location.replace("/logout");
                }, 1000);
            }
        });

        document.addEventListener('DOMContentLoaded', function() {
            var uploadButton = document.getElementById('upload-button');
            var profilePic = document.getElementById('profile-pic');

            uploadButton.addEventListener('click', function() {
                var fileInput = document.createElement('input');
                fileInput.type = 'file';
                fileInput.style.display = 'none';
                document.body.appendChild(fileInput);
                fileInput.click();

                fileInput.addEventListener('change', function(event) {
                    var file = event.target.files[0];

                    if (file.size > 20 * 1024 * 1024) {
                        alert('The selected picture is too big. Maximum file size is 20 MB.');
                        return;
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
        });
    </script>
</@layout.base>
