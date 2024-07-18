<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">stockpot</span>
        ${potion.name!}
    </h1>
    <section class="content co">

        <div class="item-detail row">

            <#if potion["imageUrl"]??>
                <img alt="" class="item-img" src="${potion.imageUrl}" />
            <#else>
                <img alt="" class="item-img" src="/static/img/no_image.png" />
            </#if>

            <ul class="item-content">
                <h2>Potion Information</h2>

                <li>
                    <h3>Characteristics:</h3>
                    <p>${potion.characteristics!'Unknown'}</p>
                </li>

                <li>
                    <h3>Effect:</h3>
                    <p>${potion.effect!'Unknown'}</p>
                </li>

                <li>
                    <h3>Side Effects:</h3>
                    <p>${potion.sideEffects!'Unknown'}</p>
                </li>

                <li>
                    <h3>Ingredients:</h3>
                    <p>${potion.ingredients!'Unknown'}</p>
                </li>

                <li>
                    <h3>Time:</h3>
                    <p>${potion.time!'Unknown'}</p>
                </li>

                <li>
                    <h3>Difficulty:</h3>
                    <p>${potion.difficulty!'Unknown'}</p>
                </li>

                <li>
                    <h3>Manufacturers:</h3>
                    <p>${potion.manufacturers!'Unknown'}</p>
                </li>

                <li>
                    <h3>Inventors:</h3>
                    <p>${potion.inventors!'Unknown'}</p>
                </li>

                <li>
                    <p>
                        <a class="with-icon" href="${potion.wiki!}">
                                <span class="material-symbols-outlined">open_in_new</span> Wiki
                        </a>
                    </p>
                </li>
            </ul>
        </div>

    </section>
</@layout.base>