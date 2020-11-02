<#import "commons.ftlh" as c>

<@c.page>
    <form method="post" action="">
        <input type="hidden" name="action" value="send"/>
        <select name="target">
            <#list users as user>
                <option value="${user.id}">${user.login}</option>
            </#list>
        </select>
        <div class="value">
            <textarea rows="10" cols="45" id="message" name="message"></textarea>
        </div>
        <div class="button-field">
            <input type="submit" value="Send">
        </div>
    </form>
    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Message</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <#assign key_list = idToUserName?keys/>
            <#assign value_list = idToUserName?values/>
            <#list talks as talk>
                <tr>
                    <td>${value_list[key_list?seq_index_of(talk.sourceUserId)]}</td>
                    <td>${value_list[key_list?seq_index_of(talk.targetUserId)]}</td>
                    <td>${talk.message}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>