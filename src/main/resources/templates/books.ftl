<#import "_layout.ftl" as layout />
<#assign userSession = session in layout>
<#assign profilePicture = profilePictureData in layout>

<@layout.base>
    <h1>
        <span class="material-symbols-outlined">book</span>
        Books
    </h1>
    <section class="content">
        <ul class="content-list">
            <#list books as book>
                <li><a href="/books/${book.slug}">${book.title}</a></li>
            </#list>
        </ul>
        <footer class="pagination">
            <ul>
                <#if (pagination.first)??>
                    <li>${pagination.first}</li>
                </#if>

                <#if (pagination.previous)??>
                <li>${pagination.previous}</li>
                </#if>

                <#if (pagination.current)??>
                    <li>${pagination.current}</li>
                </#if>

                <#if (pagination.next)??>
                    <li>${pagination.next}</li>
                </#if>

                <#if (pagination.last)??>
                    <li>${pagination.last}</li>
                </#if>

<#--                <li>${pagination.records}</li>-->
            </ul>
        </footer>
    </section>

</@layout.base>