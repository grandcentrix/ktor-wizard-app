<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <style>
        a {
            color: #4682B4; /* Lighter shade of blue */
        }
    </style>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Book Details
    </h1>
    <section class="content">
        <h2>Book Information</h2>
        <ul class="content-block">
            <li>name: ${character.name!}</li>
        </ul>
    </section>
</@layout.base>