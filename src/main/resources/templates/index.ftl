<#import "_layout.ftl" as layout />
<#assign userSession = userSession in layout>
<#assign avatar = avatar in layout>

<@layout.base>

<#--    <br/><span>${loginStatus}</span><br/>-->
    <section class="content">

            <article style="background-image: url('/static/img/book.png')" class="home-items">
                <h2><a href="/books" >Books</a></h2>
            </article>
            <article style="background-image: url('/static/img/movie.png')" class="home-items">
                <h2><a href="/movies" >Movies</a></h2>
            </article>
            <article style="background-image: url('/static/img/character.png')" class="home-items">
                <h2><a href="/characters" >Characters</a></h2>
            </article>

            <article style="background-image: url('/static/img/spell.png')" class="home-items">
                <h2><a href="/spells" >Spells</a></h2>
            </article>
            <article style="background-image: url('/static/img/potion.png')" class="home-items">
                <h2><a href="/potions" >Potions</a></h2>
            </article>
            <article style="background-image: url('/static/img/house.png')" class="home-items">
                <h2><a href="/houses" >Houses</a></h2>
            </article>


</@layout.base>


