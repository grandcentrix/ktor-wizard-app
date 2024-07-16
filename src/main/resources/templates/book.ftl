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
            <li>Title: ${book.title!}</li>
            <br>
            <li>Author: ${book.author!}</li>
            <br>
            <li>Summary: ${book.summary!}</li>
            <br>
            <li>Pages: ${book.pages!}</li>
            <br>
            <li>Dedication: ${book.dedication!}</li>
            <br>
            <li>Wiki: <a href="${book.wiki!}">${book.wiki!}</a></li>
        </ul>
    </section>
</@layout.base>