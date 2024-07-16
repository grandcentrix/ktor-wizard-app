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
        <span class="material-symbols-outlined">stockpot</span>
        Potion Details
    </h1>
    <section class="content">
        <h2>Potion Information</h2>
        <ul class="content-block">
            <li>Potion Name: ${potion.name!}</li>
            <br>
            <li>Characteristics: ${potion.characteristics!'Unknown'}</li>
            <br>
            <li>Effect: ${potion.effect!'Unknown'}</li>
            <br>
            <li>Side Effects: ${potion.sideEffects!'Unknown'}</li>
            <br>
            <li>Ingredients: ${potion.ingredients!'Unknown'}</li>
            <br>
            <li>Time: ${potion.time!'Unknown'}</li>
            <br>
            <li>Difficulty: ${potion.difficulty!'Unknown'}</li>
            <br>
            <li>Manufacturers: ${potion.manufacturers!'Unknown'}</li>
            <br>
            <li>Inventors: ${potion.inventors!'Unknown'}</li>
            <br>
            <li>Wiki: <a href="${potion.wiki!}">${potion.wiki!}</a></li>
        </ul>
    </section>
</@layout.base>