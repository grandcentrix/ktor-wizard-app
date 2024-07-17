<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">House</span>
        House Details
    </h1>
    <section class="content">
        <h2>House Information</h2>
        <ul class="content-block">
            <li><span class="house_details">Name:</span> <span class="larger-text"> ${detail_house.name}</span></li>
            <br>
            <li><span class="house_details">Colors:</span> <span class="larger-text"> ${detail_house.colors}</span></li>
            <br>
            <li><span class="house_details">Founder:</span> <span class="larger-text"> ${detail_house.founder}</span></li>
            <br>
            <li><span class="house_details">Animal:</span> <span class="larger-text">${detail_house.animal}</span></li>
            <br>
            <li><span class="house_details">Element:</span> <span class="larger-text">${detail_house.element}</span></li>
            <br>
            <li><span class="house_details">Ghost:</span> <span class="larger-text">${detail_house.ghost}</span></li>
            <br>
            <li><span class="house_details">CommonRoom:</span><span class="larger-text"> ${detail_house.commonRoom}</span></li>
            <br>
            <li><span class="house_details">Heads:</span><span class="larger-text"> ${detail_house.heads?join(', ')}</span></li>
            <br>
            <li><span class="house_details">Traits:</span><span class="larger-text"> ${detail_house.traits?join(', ')}</span></li>
            <br>

            <#if detail_house.id == "0367baf3-1cb6-4baf-bede-48e17e1cd005">
                <li><span class="house_details">Slogan:</span><span class="larger-text"> “Their daring, nerve and chivalry set Gryffindors apart."</span></li>
            <#elseif detail_house.id == "805fd37a-65ae-4fe5-b336-d767b8b7c73a">
                <li><span class="house_details">Slogan:</span><span class="larger-text"> "Wit beyond measure is man's greatest treasure."</span></li>
            <#elseif detail_house.id == "a9704c47-f92e-40a4-8771-ed1899c9b9c1">
                <li><span class="house_details">Slogan:</span><span class="larger-text"> "Slytherin will help you on your way to greatness." </span></li>
            <#elseif detail_house.id == "85af6295-fd01-4170-a10b-963dd51dce14">
                <li><span class="house_details">Slogan:</span> <span class="larger-text"> "You might belong in Hufflepuff, where they are just and loyal. Those patient Hufflepuffs are true, and unafraid of toil."</span></li>
            </#if>


        </ul>
    </section>
</@layout.base>