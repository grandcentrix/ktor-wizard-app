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
        <span class="material-symbols-outlined">book</span>
        Character Details
    </h1>
    <section class="content">
        <h2>Character Information</h2>
        <ul class="content-block">
            <li>Name: ${character.name!}</li>
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
            <li>Animagus: ${(character.animagus)!'Unknown'}</li>
            <li>Boggart: ${(character.boggart)!'Unknown'}</li>
            <li>Birth: ${(character.birth)!'Unknown'}</li>
            <li>Death: ${(character.death)!'Unknown'}</li>
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
            <li>House: ${(character.house)!'Unknown'}</li>
            <li>Nationality: ${(character.nationality)!'Unknown'}</li>
            <li>Gender: ${(character.gender)!'Unknown'}</li>
            <li>Blood Status: ${(character.blood_status)!'Unknown'}</li>
            <li>Species: ${(character.species)!'Unknown'}</li>
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
            <li>Titles:
                <#if character.titles??>
                    <#if character.titles?size gt 0>
                        ${character.titles?join(', ')}
                    <#else>
                        Unknown
                    </#if>
                <#else>
                    Unknown
                </#if>
            <li>wands:
                <#if character.wands??>
                    <#if character.wands?size gt 0>
                        ${character.wands?join(', ')}
                    <#else>
                        Unknown
                    </#if>
                <#else>
                    Unknown
                </#if>
            <li>Wiki: <a href="${character.wiki!}">${character.wiki!}</a></li>
            </li>
        </ul>
    </section>
</@layout.base>