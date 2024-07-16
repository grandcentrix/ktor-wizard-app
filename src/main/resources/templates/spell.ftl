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
            <br>
            <li>Category: ${spell.category!'Unknown'}</li>
            <br>
            <li>Creator: ${spell.creator!'Unknown'}</li>
            <br>
            <li>Effect: ${spell.effect!'Unknown'}</li>
            <br>
            <li>Hand: ${spell.hand!'Unknown'}</li>
            <br>
            <li>Incantation: ${spell.incantation!'Unknown'}</li>
            <br>
            <li>Light: ${spell.light!'Unknown'}</li>
            <br>
            <li>Hand: ${spell.hand!'Unknown'}</li>
            <br>
            <li>Wiki: <a href="${spell.wiki!}">${spell.wiki!}</a></li>

        </ul>
    </section>
</@layout.base>