<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <td class="user_admin" style="display: none">
                        <div class="user_admin_value">A</div>
                        <a href="#" class="user_admin_toggle">A</a>
                    </td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax({
                action: "findAll"
            }, function(response) {
                const admin = response["user"] === undefined ? false : response["user"]["admin"]
                response["users"].forEach(function(user) {
                    $noData.hide();
                    const $tr = $($(".users template").prop("content")).clone();
                    $tr.find(".user_id").text(user["id"]);
                    $tr.find(".user_login").text(user["login"]);
                    if (admin) {
                        const isAdmin = user["admin"];
                        const toggle = isAdmin ? "Disable" : "Enable";
                        $tr.find("tr .user_admin .user_admin_value").text(isAdmin);
                        $tr.find("tr .user_admin .user_admin_toggle").text(toggle);
                        $tr.find("tr .user_admin .user_admin_toggle").attr("id", user["id"]);
                        $tr.find("tr .user_admin").css( "display", "table-cell" ) ;
                    }
                    $tr.find("tr").attr("data-userId", user["id"]);
                    $(".users tbody").append($tr);
                });
                $(".users tr .user_admin_toggle").click(function() {
                    ajax({
                        action: "toggleAdmin",
                        id: $(this).attr("id")
                    }, function (response) {
                        const isAdmin = response["user"]["admin"];
                        const toggle = isAdmin ? "Disable" : "Enable";
                        let $userAdmin = $("tr[data-userid="+response["user"]["id"]+"]");
                        $userAdmin.find(".user_admin .user_admin_value").text(isAdmin);
                        $userAdmin.find(".user_admin .user_admin_toggle").text(toggle);
                    })
                });
            })
        })
    </script>
</@c.page>
