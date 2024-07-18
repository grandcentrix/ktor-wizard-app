<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">person</span>
        ${character.name!}
    </h1>

    <section class="content row">

        <div class="item-detail row">
            <#if character["imageUrl"]??>
                <img alt="" class="item-img" src="${character.imageUrl}" />
            <#else>
                <img alt="" class="item-img" src="/static/img/no_image.png" />
            </#if>


            <ul class="item-content">
                <h2>Character Information</h2>

                <li>
                    <h3>Alias:</h3>
                    <#if character.aliasNames??>
                        <#if character.aliasNames?size gt 0>
                            <p>
                                ${character.aliasNames?join(', ')}
                            </p>
                        <#else>
                            <p>Unknown</p>
                        </#if>
                    <#else>
                        <p>Unknown</p>
                    </#if>
                </li>

                <li>
                    <h3>Animagus:</h3>
                    <p>${(character.animagus)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Boggart:</h3>
                    <p>${(character.boggart)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Birth:</h3>
                    <p>${(character.birth)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Death:</h3>
                    <p>${(character.death)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Family Members:</h3>
                    <#if character.familyMembers??>
                        <#if character.familyMembers?size gt 0>
                            <p>${character.familyMembers?join(', ')}</p>
                        <#else>
                            <p>Unknown</p>
                        </#if>
                    <#else>
                        <p>Unknown</p>
                    </#if>
                </li>

                <li>
                    <h3>House:</h3>
                    <p>${(character.house)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Nationality:</h3>
                    <p>${(character.nationality)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Gender:</h3>
                    <p>${(character.gender)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Blood Status:</h3>
                    <p>${(character.blood_status)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Species:</h3>
                    <p>${(character.species)!'Unknown'}</p>
                </li>

                <li>
                    <h3>Jobs:</h3>
                    <#if character.jobs??>
                        <#if character.jobs?size gt 0>
                            <p>${character.jobs?join(', ')}</p>
                        <#else>
                            <p>Unknown</p>
                        </#if>
                    <#else>
                        <p>Unknown</p>
                    </#if>
                </li>

                <li>
                    <h3>Titles:</h3>
                    <#if character.titles??>
                        <#if character.titles?size gt 0>
                            <p>${character.titles?join(', ')}</p>
                        <#else>
                            <p>Unknown</p>
                        </#if>
                    <#else>
                        <p>Unknown</p>
                    </#if>
                </li>

                <li>
                    <h3>Wands:</h3>
                    <#if character.wands??>
                        <#if character.wands?size gt 0>
                            <p>${character.wands?join(', ')}</p>
                        <#else>
                            <p>Unknown</p>
                        </#if>
                    <#else>
                        <p>Unknown</p>
                    </#if>
                </li>

                <li>
                    <p>
                        <a class="with-icon" href="${character.wiki!}">
                            <span class="material-symbols-outlined">open_in_new</span>
                            Wiki
                        </a>
                    </p>

                </li>
            </ul>
        </div>

    </section>
</@layout.base>