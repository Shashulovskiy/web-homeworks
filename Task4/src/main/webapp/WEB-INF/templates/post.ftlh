<#-- @ftlvariable name="id" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign p = c.findBy(posts, "id", id?number)!/>
    <@c.post p "long"/>
</@c.page>