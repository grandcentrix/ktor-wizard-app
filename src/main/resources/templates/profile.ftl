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
            <div id="update-username" class="user-data">
                <form 
                  class="profile-form" 
                  hx-put="/user/username" 
                  hx-trigger="submit" 
                  hx-target="#update-username"
                  hx-select="#update-username"
                  hx-swap="outerHTML"
                >
                    <label for="new-username">
                        <input type="text" id="new-username" name="newUsername" placeholder="New username" required>
                    </label>
                    <input class="button" type="submit" value="Save">
                    <#if statusMessage??>
                      <span>${statusMessage}</span>
                    </#if>
                </form>
            </div>

            <div id="update-email" class="user-data">
                <form 
                  class="profile-form" 
                  hx-put="/user/email" 
                  hx-trigger="submit" 
                  hx-target="#update-email"
                  hx-select="#update-email"
                  hx-swap="outerHTML"
                >
                    <label for="new-email">
                        <input type="email" id="new-email" name="newEmail" placeholder="Update e-mail" required>
                    </label>
                    <input class="button" type="submit" value="Save">
                    <#if statusMessage??>
                      <span>${statusMessage}</span>
                    </#if>
                </form>
            </div>

            <div id="update-password" class="user-data">
                <form 
                  class="profile-form" 
                  hx-put="/user/password" 
                  hx-trigger="submit" 
                  hx-target="#update-password"
                  hx-select="#update-password"
                  hx-swap="outerHTML"
                >
                    <label for="new-password">
                        <input type="password" id="new-password" name="newPassword" placeholder="New password" required>
                    </label>
                    <input class="button" type="submit" value="Save">
                    <#if statusMessage??>
                      <span>${statusMessage}</span>
                    </#if>
                </form>
            </div>
        </div>

        <div id="delete-account" class="user-data " style="justify-content: flex-end;">
            <form 
              hx-delete="/user/account" 
              hx-trigger="submit" 
              hx-target="#delete-account"
              hx-select="#delete-account"
              onsubmit="return confirm('Are you sure you want to delete your account?');"
            >
                <input class="button delete-button" type="submit" value="Delete Account">
            </form>
        </div>

    </section>

</@layout.base>