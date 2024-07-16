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
        <span class="material-symbols-outlined">person</span>
        Character Details
    </h1>
    <section class="content">
        <h2>Character Information</h2>
        <ul class="content-block">
            <li>Name: ${character.name!}</li>
            <br>
            <li>Alias:
                <#if character.aliasNames??>
                    <#if character.aliasNames?size gt 0>
                        ${character.aliasNames?join(', ')}
                    <#else>
                        Unknown
                    </#if>
                <#else>
                    Unknown
                </#if>
            </li>
            <br>
            <li>Animagus: ${(character.animagus)!'Unknown'}</li>
            <br>
            <li>Boggart: ${(character.boggart)!'Unknown'}</li>
            <br>
            <li>Birth: ${(character.birth)!'Unknown'}</li>
            <br>
            <li>Death: ${(character.death)!'Unknown'}</li>
            <br>
            <li>Family Members:
                <#if character.familyMembers??>
                    <#if character.familyMembers?size gt 0>
                        ${character.familyMembers?join(', ')}
                    <#else>
                        Unknown
                    </#if>
                <#else>
                    Unknown
                </#if>
                <br>
            <li>House: ${(character.house)!'Unknown'}</li>
            <br>
            <li>Nationality: ${(character.nationality)!'Unknown'}</li>
            <br>
            <li>Gender: ${(character.gender)!'Unknown'}</li>
            <br>
            <li>Blood Status: ${(character.blood_status)!'Unknown'}</li>
            <br>
            <li>Species: ${(character.species)!'Unknown'}</li>
            <br>
            <li>Jobs:
                <#if character.jobs??>
                    <#if character.jobs?size gt 0>
                        ${character.jobs?join(', ')}
                    <#else>
                        Unknown
                    </#if>
                <#else>
                    Unknown
                </#if>
            <li>
                <br>
                Titles:
                <#if character.titles??>
                    <#if character.titles?size gt 0>
                        ${character.titles?join(', ')}
                    <#else>
                        Unknown
                    </#if>
                <#else>
                    Unknown
                </#if>
            <li>
                <br>
                wands:
                <#if character.wands??>
                    <#if character.wands?size gt 0>
                        ${character.wands?join(', ')}
                    <#else>
                        Unknown
                    </#if>
                <#else>
                    Unknown
                </#if>
            <li>
                <br>
                Wiki: <a href="${character.wiki!}">${character.wiki!}</a></li>
            </li>
        </ul>
    </section>
</@layout.base>