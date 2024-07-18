<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">book</span>
        ${book.title}
    </h1>
    <section class="content column">

        <div class="item-detail row">
            <img alt="" class="item-img" src="${book.coverUrl}" />

            <ul class="item-content">

                <h2>Book Information</h2>

                <li>
                    <h3>Author:</h3>
                    <p>${book.author!}</p>
                </li>

                <li>
                    <h3>Pages:</h3>
                    <p>${book.pages!}</p>
                </li>

                <li>
                    <h3>Dedication:</h3>
                    <p>${book.dedication!}</p>
                </li>

                <li style="display: block">
                    <h3>Summary:</h3>
                    <p>${book.summary!}</p>
                </li>

                <li>
                    <p>
                        <a class="with-icon" href="${book.wiki!}">
                            <span class="material-symbols-outlined">open_in_new</span> Wiki
                        </a>
                    </p>
                </li>
            </ul>
        </div>

        <h2 style="margin-top: 30px;">Chapters</h2>
        <ul>
            <#list chapters as chapter>
                <li class="column">
                    <input type="checkbox" id="${chapter.title?replace(" ", "_")}-checkbox">
                    <span class="book-chapter">
                        <label class="chapter-title text" for="${chapter.title?replace(" ", "_")}-checkbox">
                            ${chapter.title}
                        </label>
                    </span>
                    <p class="chapter-summary text">${chapter.summary!}</p>
                </li>
            </#list>
        </ul>
    </section>
</@layout.base>