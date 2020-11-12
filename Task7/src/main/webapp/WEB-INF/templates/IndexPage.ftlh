<#import "commons.ftlh" as c>

<@c.page>
    <div class="posts">
        <div class="noData">No data</div>
        <template>
            <article>
                <div class="title">Loading..</div>
                <div class="information">Loading..</div>
                <div class="body">
                    Loading...
                </div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        Loading...
                    </div>
                </div>
            </article>
        </template>
    </div>

    <script>
        $(function () {
            $("aside section").click(function () {
                $(this).css("background-color", "red");
            });
        })
        $(function () {
            const $noData = $(".posts .noData");
            $noData.text("Loading...");

            ajax({
                action: "findAll"
            }, function (response) {
                let shownArticles = response["articles"].filter(a => a["hidden"] === false);
                if (shownArticles.length === 0) {
                    $noData.text("No Articles yet! Be the first one to post something that nobody cares about!");
                }
                shownArticles.forEach(function (article) {
                    $noData.hide();
                    const $tr = $($(".posts template").html()).clone().attr("id", "article" + article["id"]);
                    $tr.find(".title").text(article["title"]);
                    $tr.find(".body").text(article["text"]);
                    $tr.find(".footer .right").text(article["creationTime"]);
                    $(".posts").append($tr);

                    ajax({
                        action: "findLoginById",
                        userId: article["userId"]
                    }, function (userIdResponse) {
                        $("#article" + article["id"] + " .information").text("By " + userIdResponse["userLogin"] + " on " + article["creationTime"]);
                    }, "/users")
                });
            }, "/article")
        })
    </script>
</@c.page>
