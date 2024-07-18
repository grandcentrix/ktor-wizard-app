<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">flare</span>
        Spell Details
    </h1>
    <section class="content">
        <h2>Spell Information</h2>
        <ul class="content-block">
            <li><span class="spell-detail">Spell Name:</span> <span class="larger-text">${spell.name!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Category:</span> <span class="larger-text">${spell.category!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Creator:</span> <span class="larger-text">${spell.creator!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Effect:</span> <span class="larger-text">${spell.effect!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Hand:</span> <span class="larger-text">${spell.hand!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Incantation:</span> <span class="larger-text">${spell.incantation!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Light:</span> <span class="larger-text">${spell.light!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Hand:</span> <span class="larger-text">${spell.hand!'Unknown'}</span></li>
            <br>
            <li><span class="spell-detail">Wiki:</span> <a href="${spell.wiki!}"><span class="wiki-text">${spell.wiki!}</span></a></li>
        </ul>
    </section>
</@layout.base>