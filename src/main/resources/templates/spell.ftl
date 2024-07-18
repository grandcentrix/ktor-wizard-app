<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">flare</span>
        ${spell.name}
    </h1>
    <section class="content row">
        
        <div class="item-detail row">
            <ul class="item-content">
                <h2>Spell Information</h2>

                <li>
                    <h3>Category:</h3>
                    <p>${spell.category!'Unknown'}</p>
                </li>

                <li>
                    <h3>Creator:</h3>
                    <p>${spell.creator!'Unknown'}</p>
                </li>

                <li>
                    <h3>Effect:</h3>
                    <p>${spell.effect!'Unknown'}</p>
                </li>

                <li>
                    <h3>Hand:</h3>
                    <p>${spell.hand!'Unknown'}</p>
                </li>

                <li>
                    <h3>Incantation:</h3>
                    <p>${spell.incantation!'Unknown'}</p>
                </li>

                <li><h3>Light:</h3>
                    <p>${spell.light!'Unknown'}</p>
                </li>

                <li>
                    <h3>Hand:</h3>
                    <p>${spell.hand!'Unknown'}</p>
                </li>

                <li>
                    <p>
                        <a class="with-icon" href="${spell.wiki!}">
                            <span class="material-symbols-outlined">open_in_new</span>
                            Wiki
                        </a>
                    </p>
                </li>
            </ul>
        </div>
        
    </section>
</@layout.base>