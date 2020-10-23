<#-- @ftlvariable name="user_id" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as p>
        <#if p.user_id == user_id>
            <@c.post p "short"/>
        </#if>
    </#list>
</@c.page>