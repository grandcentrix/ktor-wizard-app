<#import "_layout.ftl" as layout /> <!-- Importing the "_layout.ftl" template file -->
<#assign userSession = userSession in layout>
<#assign avatar = avatar in layout>

<@layout.base> <!-- Calling the "base" macro defined in the imported "_layout.ftl" template -->

        <h1>
            <span class="material-symbols-outlined">account_box</span>
            Welcome, ${username}!
        </h1>


    <section class="content" style="flex-direction: column">
        <div class="user-data">
            <p>Upload profile picture</p> <!-- Adding text next to the button -->
            <#if uploadButton> <!-- Checking if the 'uploadButton' variable is true -->
                <button id="upload-button">Upload Picture</button> <!-- Creating a button for uploading a picture -->
            </#if>
        </div>

        <div class="delete-button">
            <form class="form" action="/delete-account" method="POST" onsubmit="return confirmDelete()">
                <input class="button" style="margin-left: 0;" type="submit" value="Delete Account">
            </form>
        </div>

    </section>

    <script>
        document.addEventListener('DOMContentLoaded', function() { <!-- Adding an event listener when the DOM content is loaded -->
            var uploadButton = document.getElementById('upload-button'); <!-- Finding the upload button element -->

            uploadButton.addEventListener('click', function() { <!-- Adding an event listener to the upload button -->
                var fileInput = document.createElement('input'); <!-- Creating a file input element -->
                fileInput.type = 'file'; <!-- Setting the type of the input to file -->
                fileInput.style.display = 'none'; <!-- Making the input hidden -->
                document.body.appendChild(fileInput); <!-- Adding the input to the document body -->
                fileInput.click(); <!-- Triggering a click event on the file input -->

                fileInput.addEventListener('change', function(event) { <!-- Adding an event listener to the file input for changes -->
                    var file = event.target.files[0]; <!-- Getting the selected file -->
                    var reader = new FileReader(); <!-- Creating a FileReader object -->

                    reader.onload = function(event) { <!-- Adding an event listener when the file is loaded -->
                        var imageDataUrl = event.target.result; <!-- Getting the data URL of the uploaded image -->
                        var profilePic = document.getElementById('profile-pic'); <!-- Finding the profile picture element -->
                        profilePic.src = imageDataUrl; <!-- Setting the source of the profile picture to the uploaded image -->
                    };

                    reader.readAsDataURL(file); <!-- Reading the file as a data URL -->

                    // Remove the file input element from the DOM
                    document.body.removeChild(fileInput); <!-- Removing the file input element from the document body -->
                });
            });
        });
    </script>

    <script>
        function confirmDelete() {
            return confirm("Are you sure you want to delete your account? This action cannot be undone!");
        }
    </script>

</@layout.base> <!-- Closing the "base" macro -->
