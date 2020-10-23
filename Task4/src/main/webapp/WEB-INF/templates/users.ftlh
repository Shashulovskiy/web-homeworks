<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td><@c.userlink u true/></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
