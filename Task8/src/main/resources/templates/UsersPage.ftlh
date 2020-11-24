<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if userId??>
                    <th>Status</th>
                </#if>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as u>
                <tr>
                    <td class="id">${u.id}</td>
                    <td class="login">${u.login}</td>
                    <#if userId??>
                        <td class="status">
                            <form method="post" action="/toggleUser">
                                <input type="hidden" name="id" value="${u.id}"/>
                                <input type="hidden" name="disabled" value="${u.disabled?string("0", "1")}"/>
                                <div class="button-field">
                                    <input type="submit" value="${u.disabled?string("Enable", "Disable")}">
                                </div>
                            </form>
                        </td>
                    </#if>
                    <td class="creationTime">${u.creationTime}</td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
