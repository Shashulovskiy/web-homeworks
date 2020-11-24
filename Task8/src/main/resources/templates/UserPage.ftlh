<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if error??>
        <div>${error}</div>
    <#else>
        <div>UserID: ${user.id}</div>
        <div>Login: ${user.login}</div>
        <div>Created: ${user.creationTime}</div>
    </#if>
</@c.page>
