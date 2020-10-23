<#import "./common.ftlh" as c>

<@c.page>
    <div class="posts">
        <#list posts?reverse as p>
            <@c.post p "short"/>
        </#list>
    </div>
</@c.page>
