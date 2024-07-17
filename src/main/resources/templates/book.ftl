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

        .book-chapter {
            font-size: 1.4em; /* make the text a bit bigger */
            color: #4682B4; /* same color as links */
        }

        .larger-text {
            font-size: 1.2em; /* make the text even bigger */
        }

        .chapter-summary {
            display: none;
        }

        input[type="checkbox"] {
            display: none;
        }

        input[type="checkbox"]:checked ~.chapter-summary {
            display: block;
        }

        label {
            color: #4682B4; /* same color as links */
            cursor: pointer; /* make the label look like a clickable link */
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
            <br>
            <ul>
                <h2>Chapters</h2>
                <#list chapters as chapter>
                    <li>
                        <input type="checkbox" id="${chapter.title?replace(" ", "_")}-checkbox">
                        <span class="book-chapter"><label for="${chapter.title?replace(" ", "_")}-checkbox">${chapter.title}</span></label>
                        <div class="chapter-summary">${chapter.summary!}</div>
                    </li>
                </#list>
            </ul>

        </ul>
    </section>
</@layout.base>