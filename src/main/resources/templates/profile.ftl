<#import "_layout.ftl" as layout />

<@layout.base>

    <h1>
        <span class="material-symbols-outlined">account_box</span>
        Welcome, ${username}!
    </h1>

    <section class="content profile-content">
        <div class="user-house" hx-get="/hogwarts-house" hx-swap="outerHTML">
            <#if house??>
                <img
                        class="user-house-symbol"
                        src="/static/img/${house}_symbol.png"
                        alt="${house} Symbol"
                />
            </#if>
        </div>

        <div class="profile-picture-data">
            <div class="picture-buttons">
                <#if profilePictureData??>
                        <div class="profile-picture-display">
                            <img id="profile-pic" class="profile-picture" src="${profilePictureData}" alt="Profile Picture">
                        </div>
                </#if>
                <div class="profile-picture-buttons">
                    <form
                            id="upload-form"
                            enctype="multipart/form-data"
                            hx-post="/user/profilepicture"
                            hx-trigger="change from:#upload"
                            hx-target="#profile-pic"
                            hx-select-oob="#profile-pic"
                            hx-swap="innerHTML"
                    >
                        <input
                                type="file"
                                id="upload"
                                name="file"
                                hidden
                        />
                        <label class="links button-main" for="upload">Choose file</label>
                    </form>
                    <button
                            class="links"
                            hx-delete="/user/profilepicture"
                            hx-target="#profile-pic"
                            hx-select="#profile-pic"
                            style="margin: 15px 0 0 0;"
                    >
                        Remove Picture
                    </button>
                </div>
            </div>
            <span style="color:#f5c8ae;">Maximum size for picture: 5MB</span>
        </div>

        <div class="user-data-form">
            <div class="user-data">
                <form class="profile-form" hx-put="/user/username" hx-trigger="submit" hx-target=".content">
                    <label for="new-username">
                        <input type="text" id="new-username" name="newUsername" placeholder="New username" required>
                    </label>
                    <input class="button" type="submit" value="Save">
                </form>
            </div>

            <div class="user-data">
                <form class="profile-form" hx-put="/user/email" hx-trigger="submit" hx-target=".content">
                    <label for="new-email">
                        <input type="email" id="new-email" name="newEmail" placeholder="Update e-mail" required>
                    </label>
                    <input class="button" type="submit" value="Save">
                </form>
            </div>

            <div class="user-data">
                <form class="profile-form" hx-put="/user/password" hx-trigger="submit" hx-target=".content">
                    <label for="new-password">
                        <input type="password" id="new-password" name="newPassword" placeholder="New password" required>
                    </label>
                    <input class="button" type="submit" value="Save">
                </form>
            </div>
        </div>

        <div class="user-data " style="justify-content: flex-end;">
            <form hx-delete="/user/account" hx-trigger="submit" hx-target=".content"
                  onsubmit="return confirm('Are you sure you want to delete your account?');">
                <input class="button delete-button" type="submit" value="Delete Account">
            </form>
        </div>

    </section>

<#--    <script>-->
<#--        document.addEventListener('DOMContentLoaded', function() {-->
<#--            var uploadButton = document.getElementById('upload-button');-->

<#--            uploadButton.addEventListener('click', function() {-->
<#--                var fileInput = document.createElement('input');-->
<#--                fileInput.type = 'file';-->
<#--                fileInput.style.display = 'none';-->
<#--                document.body.appendChild(fileInput);-->
<#--                fileInput.click();-->

<#--                fileInput.addEventListener('change', function(event) {-->
<#--                    var file = event.target.files[0];-->

<#--                    if (file.size > 20 * 1024 * 1024) {-->
<#--                        alert('The selected picture is too big. Maximum file size is 20 MB.');-->
<#--                        return;-->
<#--                    }-->

<#--                    var reader = new FileReader();-->

<#--                    reader.onload = function(event) {-->
<#--                        var imageDataUrl = event.target.result;-->
<#--                        var profilePic = document.getElementById('profile-pic');-->
<#--                        profilePic.src = imageDataUrl;-->

<#--                        var formData = new FormData();-->
<#--                        formData.append('profilePicture', file);-->

<#--                        fetch('/user/profilepicture', {-->
<#--                            method: 'PUT',-->
<#--                            body: formData-->
<#--                        }).then(response => {-->
<#--                            return response.text().then(text => {-->
<#--                                if (response.ok) {-->
<#--                                    document.getElementById('message-container-picture').textContent = text;-->
<#--                                } else {-->
<#--                                    throw new Error(text);-->
<#--                                }-->
<#--                            });-->
<#--                        }).catch(error => {-->
<#--                            console.error('Error:', error);-->
<#--                            document.getElementById('message-container-picture').textContent = 'Failed to upload profile picture';-->
<#--                        });-->
<#--                    };-->

<#--                    reader.readAsDataURL(file);-->
<#--                    document.body.removeChild(fileInput);-->
<#--                });-->
<#--            });-->
<#--        });-->
<#--    </script>-->

</@layout.base>
