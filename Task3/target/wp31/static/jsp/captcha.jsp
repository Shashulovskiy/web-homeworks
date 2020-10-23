<%@ page import="java.util.Base64" %>
<%@ page import="ru.itmo.wp.util.ImageUtils" %><%--
  Created by IntelliJ IDEA.
  User: artemshashulovskiy
  Date: 02.10.2020
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<img src="data:image/png;base64, <%out.println(request.getSession().getAttribute("captcha-image"));%>" alt="Captcha">
<form>
    <label for="captcha">Input the numbers shown on the image: </label><br>
    <input type="text" id="captcha" name="captcha"><br>
</form>
</body>
</html>
