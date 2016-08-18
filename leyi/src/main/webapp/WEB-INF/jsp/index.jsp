<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>一叶知秋</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link href="images/favicon.ico" rel="shortcut icon" />
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet' type='text/css'>
  <link href="css/font-awesome.min.css" rel="stylesheet">
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/templatemo-style.css" rel="stylesheet">
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff;margin-bottom:5px; ">

</div>
<div class="container">
  <div class="row">
   <c:if test="${empty operator.userId or operator.userId < 1}">
  	<ul class="nav nav-tabs pull-right" >
     <li><a href="login.jsp" target="_blank">登录</a></li>
     <li><a href="/leyi/register"  target="_blank">注册</a></li>
	</ul>
	</c:if>
	<c:if test="${not empty operator.userId and operator.userId > 0}">
  	<ul class="nav nav-tabs pull-right" >
     <li><a href="/leyi/${operator.username }">我的主页</a></li>
     <li><a href="/leyi/logout">退出</a></li>
	</ul>
   </c:if>
  </div>

  <div class="row">
<!--=============================左边个人简介  ===================================--> 
    <div class="col-md-3" >
      <div class="row" style="height:250px;overflow:auto ;padding:3px;border:3px #CECEF6 solid ;border-radius:5px;">
         <img style="float:left;margin:3px;" src="/leyi/common/showPic/${userInfo.username}/${userInfo.picture }" width="150" height="150" alt="Profile Photo" >  
         <a href="/leyi/${userInfo.username}"><b>&nbsp;&nbsp;&nbsp;${userInfo.username}</b></a><br>&nbsp;&nbsp;&nbsp; ${userInfo.introduce}
      </div>
 	</div>
<!--======================中间主要内容  ===================--> 
    <div class="col-md-9 light-gray-bg">
	 <c:forEach items="${hotnew}" var="item">
	  <div class="panel panel-info" style="margin-bottom:5px">
	    <div class="panel-heading"><h4 class="panel-title"><a target="_blank" href="/leyi/${userInfo.username}/article/${item.id}">${item.name}</a></h4></div>
		<div class="panel-body">${item.brief}</div>
	  </div> <!-- 文章panel -->
	 </c:forEach>  
	</div>
  </div><!-- end of row -->
<!--==============================================================================    
=======================页面底部相关说明 ===================================
=================================================================================-->  
  <div class="row">
   <footer class="text-center">
      <p> Copyright &copy; 2084 Company Name</p>
    </footer>         
  </div> 
</div> <!-- end of container -->
 
</body>
</html> 