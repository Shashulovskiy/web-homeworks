<#-- @ftlvariable name="registerForm" type="ru.itmo.wp.form.UserCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="add-notice">
        <h3 class="header">Add Notice</h3>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="login">Content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content" style="resize: none"></textarea>
                    </div>
                    <@c.error "notice.content"></@c.error>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>
