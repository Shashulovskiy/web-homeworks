<#import "commons.ftlh" as c>

<@c.page>
    <div class="article">
        <div class="header">New Article</div>
        <div class="body">
            <form method="post" action="addArticle">
                <input type="hidden" name="action" value="addArticle"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title" value=""/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <input id="text" name="text" value=""/>
                    </div>
                </div>
                <div class="error-message">
                    ${error!}
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".article form").submit(function () {
                const title = $("input[name='title']").val();
                const text = $("input[name='text']").val();
                ajax({
                    action: "addArticle",
                    title,
                    text
                }, function (response) {
                    if (response["error"]) {
                        $(".error-message").text(response["error"]);
                    }
                });

                return false;
            });
        })
    </script>
</@c.page>