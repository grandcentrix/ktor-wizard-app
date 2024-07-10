<section class="pagination">
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
</section>