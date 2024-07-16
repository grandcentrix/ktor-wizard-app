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
            <li>Characteristics: ${potion.characteristics!'Unknown'}</li>
            <li>Effect: ${potion.effect!'Unknown'}</li>
            <li>Side Effects: ${potion.sideEffects!'Unknown'}</li>
            <li>Ingredients: ${potion.ingredients!'Unknown'}</li>
            <li>Time: ${potion.time!'Unknown'}</li>
            <li>Difficulty: ${potion.difficulty!'Unknown'}</li>
            <li>Manufacturers: ${potion.manufacturers!'Unknown'}</li>
            <li>Inventors: ${potion.inventors!'Unknown'}</li>
            <li>Wiki: <a href="${potion.wiki!}">${potion.wiki!}</a></li>
        </ul>
    </section>
</@layout.base>