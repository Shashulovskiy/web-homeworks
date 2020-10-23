<#-- @ftlvariable name="currentPage" type="java.lang.String" -->
<#macro if if then else=""><#if if>${then}<#else>${else}</#if></#macro>

<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user false/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a href="${item.href}"
                           class="${(item.href == currentPage)?then("selected", "")}">${item.name}</a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as p>
            <section>
                <div class="header">Post #${p.id}</div>
                <div class="body">${p.text?truncate(250, "...")}</div>
                <div class="footer">
                    <a href="/post?id=${p.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}" class="${user.color}">${user.handle}</a>
    <#else>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    </#if>

</#macro>

<#macro post p length>
    <article>
        <#assign u = findBy(users, "id", p.user_id)/>
        <div class="title">${p.title}</div>
        <div class="information">By <span><@userlink u true/></span>, at ${p.date}</div>
        <div class="body">
            <p><@if (length == "short") p.text?truncate(250, "...") p.text></@if></p>
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                ${p.date}
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>s
</#function>

<#function findNext items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item_index + 1]/>
        </#if>
    </#list>
</#function>

<#function findPrev items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item_index - 1]/>
        </#if>
    </#list>
</#function>
