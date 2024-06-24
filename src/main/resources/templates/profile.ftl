<#import "_layout.ftl" as layout />
<#assign userSession = userSession in layout>

<@layout.base>

<h1>
    <span class="material-symbols-outlined">account_box</span>
    Welcome, ${username}!
</h1>



<section class="content" style="flex-direction: column;">
    <div class="user-data">
        <form hx-put="/user/username" hx-trigger="submit" hx-target=".content">
            <label for="new-username">New Username:</label>
            <input type="text" id="new-username" name="newUsername" required>
            <input type="submit" value="Update Username">
        </form>
    </div>

    <div class="user-data">
        <form hx-put="/user/email" hx-trigger="submit" hx-target=".content">
            <label for="new-email">New Email:</label>
            <input type="email" id="new-email" name="newEmail" required>
            <input type="submit" value="Update Email">
        </form>
    </div>

    <div class="user-data">
        <form hx-put="/user/password" hx-trigger="submit" hx-target=".content">
            <label for="new-password">New Password:</label>
            <input type="password" id="new-password" name="newPassword" required>
            <input type="submit" value="Update Password">
        </form>
    </div>

    <div class="user-data">
        <p>Profile Picture:</p>
        <form id="upload-form" enctype="multipart/form-data">
            <input type="file" id="picture-upload" name="profilePicture" style="display: none;">
            <button type="button" id="upload-button">Upload Picture</button>
        </form>
        <button hx-delete="/user/profilepicture" hx-target=".content">Remove Picture</button>
    </div>

    <div class="delete-button">
        <form hx-delete="/user/account" hx-trigger="submit" hx-target=".content" onsubmit="return confirm('Are you sure you want to delete your account?');">
            <input class="button" style="margin-left: 0;" type="submit" value="Delete Account">
        </form>
    </div>

    <div class="user-data" hx-get="/hogwarts-house" hx-swap="outerHTML">
        <#if house??>
            <div class="user-data">
                <img
                        src="/static/img/${house} house symbole.png"
                        alt="${house} Symbol"
                        style="width: 200px; height: 200px;"
                />
                <div style="color: #dab6bd;">Hogwarts House: ${house}</div>
            </div>
        <#else>
            <p>No Hogwarts house assigned.</p>
        </#if>
    </div>

    <script>



        document.addEventListener('DOMContentLoaded', function() {
            var uploadButton = document.getElementById('upload-button');

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
                        var profilePic = document.getElementById('profile-pic');
                        profilePic.src = imageDataUrl;

                        var formData = new FormData();
                        formData.append('profilePicture', file);

                        fetch('/user/profilepicture', {
                            method: 'PUT',
                            body: formData
                        }).then(response => {
                            return response.text().then(text => {
                                if (response.ok) {
                                    document.getElementById('message-container-picture').textContent = text;
                                } else {
                                    throw new Error(text);
                                }
                            });
                        }).catch(error => {
                            console.error('Error:', error);
                            document.getElementById('message-container-picture').textContent = 'Failed to upload profile picture';
                        });
                    };

                    reader.readAsDataURL(file);
                    document.body.removeChild(fileInput);
                });
            });
        });
    </script>

    </@layout.base>
