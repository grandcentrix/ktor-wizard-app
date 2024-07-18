<#macro base>
    <#assign route = route!"">
    <section class="pagination">
        <ul>
            <#if (pagination.first)??>
                <li>
                    <a href="${route}/${pagination.first}">
                        ${pagination.first}
                    </a>
                </li>
            </#if>

            <#if !(pagination.previous)?? && !(pagination.next)??>
                <p class="color: #F2BD79">...</p>
            </#if>

            <#if (pagination.previous)??>
                <li>
                    <a href="${route}/${pagination.previous}">
                        ${pagination.previous}
                    </a>
                </li>
            </#if>

            <#if (pagination.current)??>
                <li class="current">
                    <a href="${route}/${pagination.current}">
                        ${pagination.current}
                    </a>
                </li>
            </#if>

            <#if (pagination.next)??>
                <li>
                    <a href="${route}/${pagination.next}">
                        ${pagination.next}
                    </a>
                </li>
            </#if>

            <#if (pagination.last)??>
                <p class="color: #F2BD79">...</p>
            </#if>

            <#if (pagination.last)??>
                <li>
                    <a href="${route}/${pagination.last}">
                        ${pagination.last}
                    </a>
                </li>
            </#if>

            <#--                <li>${pagination.records}</li>-->
        </ul>
    </section>
</#macro>