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
            <li>Author: ${book.author!}</li>
            <li>Summary: ${book.summary!}</li>
            <li>Pages: ${book.pages!}</li>
            <li>Dedication: ${book.dedication!}</li>
            <li>Wiki: <a href="${book.wiki!}">${book.wiki!}</a></li>
        </ul>
    </section>
</@layout.base>