<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">House</span>
        House Details
    </h1>
    <section class="content row">
        <div class="item-detail row">
            <#if houseDetail["name"] == "Gryffindor">
                <img alt="" class="item-img" src="/static/img/Gryffindor_symbol.png" />
            <#elseif houseDetail["name"] == "Slytherin">
                <img alt="" class="item-img" src="/static/img/Slytherin_symbol.png" />
            <#elseif houseDetail["name"] == "Ravenclaw">`
                <img alt="" class="item-img" src="/static/img/Ravenclaw_symbol.png" />
            <#else>
                <img alt="" class="item-img" src="/static/img/Hufflepuff_symbol.png" />
            </#if>

            <ul class="item-content">
                <h2>House Information</h2>
                <li>
                    <h3>Name:</h3>
                    <p> ${houseDetail.name}</p>
                </li>
                <li>
                    <h3>Colors:</h3>
                    <p> ${houseDetail.colors}</p>
                </li>
                <li>
                    <h3>Founder:</h3>
                    <p> ${houseDetail.founder}</p>
                </li>
                <li>
                    <h3>Animal:</h3>
                    <p>${houseDetail.animal}</p>
                </li>
                <li>
                    <h3>Element:</h3>
                    <p>${houseDetail.element}</p>
                </li>
                <li>
                    <h3>Ghost:</h3>
                    <p>${houseDetail.ghost}</p>
                </li>
                <li>
                    <h3>CommonRoom:</h3>
                    <p> ${houseDetail.commonRoom}</p>
                </li>
                <li>
                    <h3>Heads:</h3>
                    <p> ${houseDetail.heads?join(', ')}</p>
                </li>
                <li>
                    <h3>Traits:</h3>
                    <p> ${houseDetail.traits?join(', ')}</p>
                </li>

                <#if houseDetail.id == "0367baf3-1cb6-4baf-bede-48e17e1cd005">
                    <li>
                        <h3>Slogan:</h3>
                        <p> “Their daring, nerve and chivalry set Gryffindors apart."</p>
                    </li>
                <#elseif houseDetail.id == "805fd37a-65ae-4fe5-b336-d767b8b7c73a">
                    <li>
                        <h3>Slogan:</h3>
                        <p> "Wit beyond measure is man's greatest treasure."</p>
                    </li>
                <#elseif houseDetail.id == "a9704c47-f92e-40a4-8771-ed1899c9b9c1">
                    <li>
                        <h3>Slogan:</h3>
                        <p> "Slytherin will help you on your way to greatness." </p>
                    </li>
                <#elseif houseDetail.id == "85af6295-fd01-4170-a10b-963dd51dce14">
                    <li>
                        <h3>Slogan:</h3>
                        <p> "You might belong in Hufflepuff,
                            where they are just and loyal.
                            Those patient Hufflepuffs are true,
                            and unafraid of toil."
                        </p>
                    </li>
                </#if>

            </ul>
        </div>
    </section>
</@layout.base>