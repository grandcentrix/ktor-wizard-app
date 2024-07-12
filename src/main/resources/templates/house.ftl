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
        <span class="material-symbols-outlined">House</span>
        House Details
    </h1>
    <section class="content">
        <h2>House Information</h2>
        <ul class="content-block">
            <li>Name: ${house.name}</li>
            <li>Colors: ${house.colors}</li>
            <li>Founder: ${house.founder}</li>
            <li>Animal: ${house.animal}</li>
            <li>Element: ${house.element}</li>
            <li>Ghost: ${house.ghost}</li>
            <li>CommonRoom: ${house.commonRoom}</li>
            <li>Heads: ${house.heads?join(', ')}</li>
            <li>Traits: ${house.traits?join(', ')}</li>

            <#if house.id == "0367baf3-1cb6-4baf-bede-48e17e1cd005">
                <li>Slogan: “Their daring, nerve and chivalry set Gryffindors apart."</li>
            <#elseif house.id == "805fd37a-65ae-4fe5-b336-d767b8b7c73a">
                <li>Slogan: "Wit beyond measure is man's greatest treasure."</li>
            <#elseif house.id == "a9704c47-f92e-40a4-8771-ed1899c9b9c1">
                <li>Slogan: "Slytherin will help you on your way to greatness." </li>
            <#elseif house.id == "85af6295-fd01-4170-a10b-963dd51dce14">
                <li>Slogan:  "You might belong in Hufflepuff, where they are just and loyal. Those patient Hufflepuffs are true, and unafraid of toil."</li>
            </#if>


        </ul>
    </section>
</@layout.base>