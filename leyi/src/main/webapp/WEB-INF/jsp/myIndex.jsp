<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>${userInfo.username}的主页</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">

  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet' type='text/css'>
  <link href="css/font-awesome.min.css" rel="stylesheet">
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/templatemo-style.css" rel="stylesheet">
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff ;margin-bottom:5px;">

</div>
<div class="container">
  <div class="row">
  <c:if test="${not empty operator.userId and operator.userId > 0}">
  	<ul class="nav nav-tabs pull-right" >
  	 <li><a href="/leyi/${operator.username }/theme_mgr/" >分类管理</a></li>
     <li><a href="/leyi/${operator.username }/article_mgr/" >文章管理</a></li>
     <li><a href="/leyi/${operator.username }/review" >信息审核</a></li>
     <li><a href="/leyi/logout">退出</a></li>
	</ul>
   </c:if>
   <c:if test="${empty operator.userId or operator.userId < 1}">
    <ul class="nav nav-tabs pull-right" >
     <li><a href="/leyi/login.jsp" target="_blank">登录</a></li>
     <li><a href="/leyi/register"  target="_blank">注册</a></li>
	</ul>
	</c:if>
  </div>
  <div class="row">
<!--=============================左边菜单链接  ===================================--> 
    <div class="col-md-3" >
      <div class="row" style="height:250px;overflow:auto ;padding:3px;border:3px #CECEF6 solid ;border-radius:5px;">
         <img style="float:left;margin:3px;" src="/leyi/common/showPic/${userInfo.username}/${userInfo.picture }" width="150" height="150" alt="Profile Photo" class="img-responsive">  
          <a href="/leyi/${userInfo.username}/detail"><b>&nbsp;&nbsp;&nbsp;${userInfo.username} 的个人信息</b></a><br>&nbsp;&nbsp;&nbsp; ${userInfo.introduce}
      </div>
	  <div class="row light-gray-bg">
	      <ol class="breadcrumb " style="margin:10px 0;">
		   <c:forEach items="${themeTreeUp}" var="item">
		    <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
		    <c:if test="${currTheme.id!=item.id}"> <li><a href="${item.id}">${item.name}</a></li> </c:if>
		   </c:forEach>
		  </ol>
	      <nav class="navbar navbar-info "  role="navigation">          
	        <ul class="nav nav-tabs nav-stacked">
	         <c:forEach items="${children}" var="item">
	          <c:if test="${currTheme.id==item.id}"> <li class="active"><a href="/leyi/${userInfo.username}/theme/${item.id}">${item.name}</a></li></c:if>
	          <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/${userInfo.username}/theme/${item.id}">${item.name}</a></li></c:if>
	         </c:forEach>
			</ul>
	      </nav> 
      </div>       
    </div>
    <!--======================中间主要内容  ===================--> 
    <div class="col-md-9 light-gray-bg">
      <!-- =====================顶部主题分类=================== -->
	  <ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin:10px 0;">
	   <c:forEach items="${topThemes}" var="item">
        <c:if test="${currTheme.id==item.id}"> <li class="active"><a href="/leyi/${userInfo.username}/theme/${item.id}">${item.name}</a></li></c:if>
        <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/${userInfo.username}/theme/${item.id}">${item.name}</a></li></c:if>
       </c:forEach>
	  </ul>
	<c:forEach items="${articleBriefs}" var="item">	  
	  <div class="panel panel-info" style="margin-bottom:5px">
        <div class="panel-heading"><h4 class="panel-title"><a target="_blank" href="/leyi/${userInfo.username}/article/${item.id}">${item.name}</a></h4></div>
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
      <p> Copyright &copy; 2084 Company Name 
        </p>
    </footer>         
  </div> 
</div> <!-- end of container -->
 
</body>
</html> 