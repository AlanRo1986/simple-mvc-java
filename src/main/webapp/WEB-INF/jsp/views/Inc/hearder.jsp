<%--
  Created by IntelliJ IDEA.
  User: alan.luo
  Date: 2017/11/3
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, user-scalable=0,minimum-scale=0.5">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="author" content="alan.luo"/>
    <meta name="version" content="1.0"/>
    <meta name="keywords" content="${keywords}">
    <meta name="description" content="${description}">

    <script src="/resource/js/jquery1.11.1.js" type="text/javascript"></script>
    <script src="/resource/js/bootstrap.min.js" type="text/javascript"></script>

    <link href="/resource/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="/resource/css/font-awesome/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
    <title>${title} - 微论坛</title>
</head>
<body>

<header>
    <div class="container">


    <div class="bs-example" data-example-id="default-navbar">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">BBS</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="/home">首页</a></li>
                    </ul>

                    <form class="navbar-form navbar-left">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search">
                        </div>
                        <button type="submit" class="btn btn-default">Search</button>
                    </form>

                </div>
            </div>
        </nav>
    </div>
    </div>
</header>

<div class="container">
    <div class="loading">
        <i class="fa fa-spinner fa-spin"></i>
    </div>
