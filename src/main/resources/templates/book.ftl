<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <style>
        a {
            color: #4682B4; /* Lighter shade of blue */
        }

        .book-detail {
            font-size: 1.4em; /* make the text a bit bigger */
            text-decoration: underline;
        }

        .larger-text {
            font-size: 1.2em; /* make the text even bigger */
        }

    </style>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Book Details
    </h1>
    <section class="content">
        <h2>Book Information</h2>
        <ul class="content-block">
            <li><span class="book-detail">Title:</span> <span class="larger-text">${book.title!}</span></li>
            <br>
            <li><span class="book-detail">Author:</span> <span class="larger-text">${book.author!}</span></li>
            <br>
            <li><span class="book-detail">Summary:</span> <span class="larger-text">${book.summary!}</span></li>
            <br>
            <li><span class="book-detail">Pages:</span> <span class="larger-text">${book.pages!}</span></li>
            <br>
            <li><span class="book-detail">Dedication:</span> <span class="larger-text">${book.dedication!}</span></li>
            <br>
            <li><span class="book-detail">Wiki:</span> <a href="${book.wiki!}"><span class="larger-text">${book.wiki!}</span></a></li>
        </ul>
    </section>
</@layout.base>