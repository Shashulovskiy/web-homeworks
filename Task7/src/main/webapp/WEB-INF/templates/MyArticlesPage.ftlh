<#import "commons.ftlh" as c>

<@c.page>

    <div class="users datatable">
        <div class="caption">Articles</div>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Title</th>
            <th>Button</th>
        </tr>
        </thead>
        <tbody>
        <#if articles?size == 0>
            <tr class="noData">
                <td colspan="3">
                    No Articles
                </td>
            </tr>
        </#if>
        <#list articles as article>
            <tr>
                <td class="article_id">${article.id}</td>
                <td class="article_title">${article.title}</td>
                <td class="article_hide" id="${article.id}"><a href="#">${article.hidden?string("Hidden", "Shown")}</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
    </div>

    <script>
        $(".article_hide a").click(function() {
            ajax({
                action: "toggleHidden",
                id: $(this).parent().attr("id")
            }, function (response) {
                let newValue = response["article"]["hidden"] ? "Hidden" : "Shown";
                $("#" + response["article"]["id"]).find("a").text(newValue);
            }, "/article")
        });
    </script>
</@c.page>