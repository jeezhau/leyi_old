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
<div style="height:38px;background-color:#b3b3ff ">

</div>
<div class="container">
  <div class="row">
  <c:if test="${not empty operator.userId and operator.userId == userInfo.id}">
  	<ul class="nav nav-tabs pull-right" >
  	 <li><a href="/leyi/theme/" target="_blank">分类管理</a></li>
     <li><a href="/leyi/article/" target="_blank">文章收录</a></li>
	</ul>
   </c:if>
  </div>
  <div class="row">
<!--=============================左边菜单链接  ===================================--> 
    <div class="col-md-3" >
      <div class="row" style="height:250px;overflow:auto ;padding:3px;border:0px #443322 solid ;">
        <img style="float:left;margin:3px;" src="/leyi/upload/${userInfo.username}/${userInfo.picture }" width="150" height="150" alt="Profile Photo" class="img-responsive">  
         ${userInfo.introduce}
      </div>     

      <ol class="breadcrumb" style="margin:0;">
	   <c:forEach items="${themeTreeUp}" var="item">
	    <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
	    <c:if test="${currTheme.id!=item.id}"> <li><a href="${item.id}">${item.name}</a></li> </c:if>
	   </c:forEach>
	  </ol>
      <nav class="navbar navbar-info "  role="navigation">          
        <ul class="nav nav-tabs nav-stacked">
         <c:forEach items="${children}" var="item">
          <c:if test="${currTheme.id==item.id}"> <li class="active"><a href="/leyi/mypage/theme/${item.id}">${item.name}</a></li></c:if>
          <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/mypage/theme/${item.id}">${item.name}</a></li></c:if>
         </c:forEach>
		</ul>
      </nav>        
    </div>
    <!--======================中间主要内容  ===================--> 
    <div class="col-md-9 light-gray-bg">
      <!-- =====================顶部主题分类=================== -->
	  <ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin:10px 0;">
	   <c:forEach items="${topThemes}" var="item">
        <c:if test="${currTheme.id==item.id}"> <li class="active"><a href="/leyi/mypage/theme/${item.id}">${item.name}</a></li></c:if>
        <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/mypage/theme/${item.id}">${item.name}</a></li></c:if>
       </c:forEach>
	  </ul>
	<c:forEach items="${articleBriefs}" var="item">	  
	  <div class="panel panel-info">
        <div class="panel-heading"><h3 class="panel-title">${item.name}</h3></div>
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
        | Designed by <a href="http://www.templatemo.com" target="_parent">templatemo</a></p>
    </footer>         
  </div> 
</div> <!-- end of container -->
 
</body>
</html> 