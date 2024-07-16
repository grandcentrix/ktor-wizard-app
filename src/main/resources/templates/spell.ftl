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
        <span class="material-symbols-outlined">flare</span>
        Spell Details

    </h1>
    <section class="content">
        <h2>Spell Information</h2>
        <ul class="content-block">
            <li>Spell Name: ${spell.name!'Unknown'}</li>
            <li>Category: ${spell.category!'Unknown'}</li>
            <li>Creator: ${spell.creator!'Unknown'}</li>
            <li>Effect: ${spell.effect!'Unknown'}</li>
            <li>Hand: ${spell.hand!'Unknown'}</li>
            <li>Incantation: ${spell.incantation!'Unknown'}</li>
            <li>Light: ${spell.light!'Unknown'}</li>
            <li>Hand: ${spell.hand!'Unknown'}</li>
            <li>Wiki: <a href="${spell.wiki!}">${spell.wiki!}</a></li>

        </ul>
    </section>
</@layout.base>