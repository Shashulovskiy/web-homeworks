<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = ""/>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    </#if>
    <#if user_id??>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>
    <#if u != "">
        <#assign next = c.findNext(users, "handle", u.handle)!/>
        <#assign prev = c.findPrev(users, "handle", u.handle)!/>
        <#assign post_count = 0>

        <#list posts as p>
            <#if p.user_id == u.id>
                <#assign post_count = post_count + 1>
            </#if>
        </#list>

        <div class="userbody">
            <div class="userinfo">
                <p class="name"><@c.userlink u true/></p>
                <ul>
                    <li>ID: ${u.id}</li>
                    <li>Handle: ${u.handle}</li>
                    <li>Name: ${u.name}</li>
                    <li>Posts: <a href="/posts?user_id=${u.id}">${post_count}</a></li>
                </ul>
                <div class="profileimage">
                    <img src="/img/no-title.jpg">
                </div>
            </div>
        </div>
        <div class="navigation">
            <#if prev?has_content>
                <a href="/user?handle=${prev.handle}" class="active">&#8592;</a>
            <#else>
                <a href="#" class="deactivated">&#8592;</a>
            </#if>

            <#if next?has_content>
                <a href="/user?handle=${next.handle}" class="active">&#8594;</a>
            <#else>
                <a href="#" class="deactivated">&#8594;</a>
            </#if>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
