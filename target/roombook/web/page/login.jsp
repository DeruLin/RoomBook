<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Free HTML5 Template by FreeHTML5.co"/>
    <meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive"/>

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/style.css">


    <script src="${pageContext.request.contextPath}/web/js/modernizr-2.6.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/web/js/respond.min.js"></script>

</head>
<body class="style-3">

<div class="container">
    <div class="row">
        <div class="col-md-12 text-center">
            <ul class="menu">
                <li><h1>ROOM BOOK</h1></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-push-8">
            <form action="${pageContext.request.contextPath}/login" method="post" class="fh5co-form animate-box"
                  data-animate-effect="fadeInRight" id="login-form">
                <h2>Sign In</h2>
                <div class="form-group">
                    <label for="id" class="sr-only">Username</label>
                    <input type="text" class="form-control" name="id" id="id" placeholder="公共数据库账号" autocomplete="off">
                </div>
                <div class="form-group">
                    <label for="pwd" class="sr-only">Password</label>
                    <input type="password" class="form-control" name="pwd" id="pwd" placeholder="公共数据库密码"
                           autocomplete="off">
                </div>
                <div class="form-group <c:if test="${error==null}">hidden</c:if> ">
                    <p style="color: red">error: ${error}</p>
                </div>
                <div class="form-group">
                    <label for="remember"><input type="checkbox" id="remember" name="remember"> Remember Me</label>
                </div>
                <div class="form-group">
                    <input type="submit" value="Sign In" class="btn btn-primary">
                </div>
            </form>
        </div>
    </div>
    <div class="row" style="padding-top: 60px; clear: both;">
        <div class="col-md-12 text-center">
            <p>
                <small>&copy;Lin All Rights Reserved.</small>
            </p>
        </div>
    </div>
</div>

<script src="${pageContext.request.localName}/web/js/jquery.min.js"></script>
<script src="${pageContext.request.localName}/web/js/bootstrap.min.js"></script>
<script src="${pageContext.request.localName}/web/js/jquery.placeholder.min.js"></script>
<script src="${pageContext.request.localName}/web/js/jquery.waypoints.min.js"></script>
<script src="${pageContext.request.localName}/web/plugin/layer/layer.js"></script>
<script src="${pageContext.request.localName}/web/js/main.js"></script>
<script src="${pageContext.request.localName}/web/js/common.js"></script>

</body>
</html>
