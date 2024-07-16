<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <style>
        a {
            color: #4682B4; /* Lighter shade of blue */
        }

        .character-detail {
            font-size: 1.4em; /* make the text a bit bigger */
            text-decoration: underline;
        }

        .larger-text {
            font-size: 1.2em; /* make the text even bigger */
        }
    </style>
    <h1>
        <span class="material-symbols-outlined">person</span>
        Character Details
    </h1>
    <section class="content">
        <h2>Character Information</h2>
        <ul class="content-block">
            <li><span class="character-detail">Name:</span> <span class="larger-text">${character.name!}</span></li>
            <br>
            <li><span class="character-detail">Alias:</span>
                <#if character.aliasNames??>
                    <#if character.aliasNames?size gt 0>
                        <span class="larger-text">${character.aliasNames?join(', ')}</span>
                    <#else>
                        <span class="larger-text">Unknown</span>
                    </#if>
                <#else>
                    <span class="larger-text">Unknown</span>
                </#if>
            </li>
            <br>
            <li><span class="character-detail">Animagus:</span> <span class="larger-text">${(character.animagus)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Boggart:</span> <span class="larger-text">${(character.boggart)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Birth:</span> <span class="larger-text">${(character.birth)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Death:</span> <span class="larger-text">${(character.death)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Family Members:</span>
                <#if character.familyMembers??>
                    <#if character.familyMembers?size gt 0>
                        <span class="larger-text">${character.familyMembers?join(', ')}</span>
                    <#else>
                        <span class="larger-text">Unknown</span>
                    </#if>
                <#else>
                    <span class="larger-text">Unknown</span>
                </#if>
            </li>
            <br>
            <li><span class="character-detail">House:</span> <span class="larger-text">${(character.house)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Nationality:</span> <span class="larger-text">${(character.nationality)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Gender:</span> <span class="larger-text">${(character.gender)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Blood Status:</span> <span class="larger-text">${(character.blood_status)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Species:</span> <span class="larger-text">${(character.species)!'Unknown'}</span></li>
            <br>
            <li><span class="character-detail">Jobs:</span>
                <#if character.jobs??>
                    <#if character.jobs?size gt 0>
                        <span class="larger-text">${character.jobs?join(', ')}</span>
                    <#else>
                        <span class="larger-text">Unknown</span>
                    </#if>
                <#else>
                    <span class="larger-text">Unknown</span>
                </#if>
            </li>
            <br>
            <li><span class="character-detail">Titles:</span>
                <#if character.titles??>
                    <#if character.titles?size gt 0>
                        <span class="larger-text">${character.titles?join(', ')}</span>
                    <#else>
                        <span class="larger-text">Unknown</span>
                    </#if>
                <#else>
                    <span class="larger-text">Unknown</span>
                </#if>
            </li>
            <br>
            <li><span class="character-detail">Wands:</span>
                <#if character.wands??>
                    <#if character.wands?size gt 0>
                        <span class="larger-text">${character.wands?join(', ')}</span>
                    <#else>
                        <span class="larger-text">Unknown</span>
                    </#if>
                <#else>
                    <span class="larger-text">Unknown</span>
                </#if>
            </li>
            <br>
            <li><span class="character-detail">Wiki:</span> <a href="${character.wiki!}"><span class="larger-text">${character.wiki!}</span></a></li>
        </ul>
    </section>
</@layout.base>