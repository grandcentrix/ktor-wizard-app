<#import "_layout.ftl" as layout>
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <style>
        a {
            color: #4682B4; /* Lighter shade of blue */
        }

        .potion-detail {
            font-size: 1.4em; /* make the text a bit bigger */
            text-decoration: underline;
        }

        .larger-text {
            font-size: 1.2em; /* make the text even bigger */
        }
    </style>
    <h1>
        <span class="material-symbols-outlined">stockpot</span>
        Potion Details
    </h1>
    <section class="content">
        <h2>Potion Information</h2>
        <ul class="content-block">
            <li><span class="potion-detail">Potion Name:</span> <span class="larger-text">${potion.name!}</span></li>
            <br>
            <li><span class="potion-detail">Characteristics:</span> <span class="larger-text">${potion.characteristics!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Effect:</span> <span class="larger-text">${potion.effect!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Side Effects:</span> <span class="larger-text">${potion.sideEffects!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Ingredients:</span> <span class="larger-text">${potion.ingredients!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Time:</span> <span class="larger-text">${potion.time!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Difficulty:</span> <span class="larger-text">${potion.difficulty!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Manufacturers:</span> <span class="larger-text">${potion.manufacturers!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Inventors:</span> <span class="larger-text">${potion.inventors!'Unknown'}</span></li>
            <br>
            <li><span class="potion-detail">Wiki:</span> <a href="${potion.wiki!}"><span class="larger-text">${potion.wiki!}</span></a></li>
        </ul>
    </section>
</@layout.base>