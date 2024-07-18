<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
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
            <li><span class="book-detail">Wiki:</span> <a href="${book.wiki!}"><span class="wiki-text">${book.wiki!}</span></a></li>
            <br>
            <ul>
                <h2>Chapters</h2>
                <#list chapters as chapter>
                    <li>
                        <input type="checkbox" id="${chapter.title?replace(" ", "_")}-checkbox">
                        <span class="book-chapter"><label for="${chapter.title?replace(" ", "_")}-checkbox">${chapter.title}</span></label>
                        <div class="chapter-summary"><span class="larger-text">${chapter.summary!}</span></div>
                    </li>
                </#list>
            </ul>

        </ul>
    </section>
</@layout.base>