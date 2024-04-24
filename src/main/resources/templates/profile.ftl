<#import "_layout.ftl" as layout /> <!-- Importing the "_layout.ftl" template file -->

<@layout.base> <!-- Calling the "base" macro defined in the imported "_layout.ftl" template -->
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8"> <!-- Declaring the character encoding -->
        <title>Profile Picture</title> <!-- Setting the title of the HTML page -->
    </head>
    <body>
    <a>
        <h1>Welcome, ${username}!</h1>
    </a>

    <#if uploadButton> <!-- Checking if the 'uploadButton' variable is true -->
        <button id="upload-button">Upload Picture</button> <!-- Creating a button for uploading a picture -->
        <span>Upload profile picture</span> <!-- Adding text next to the button -->

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
    </#if>
    <div class="delete-button-container">
        <form class="form" action="/delete-account" method="POST" onsubmit="return confirmDelete()">
            <input class="button" type="submit" value="Delete Account">
        </form>
    </div>

    <script>
        function confirmDelete() {
            return confirm("Are you sure you want to delete your account? This action cannot be undone.");
        }
    </script>
    </body>

    </html>

</@layout.base> <!-- Closing the "base" macro -->
