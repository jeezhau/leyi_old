<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>${brief.name}</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">

  <link rel="stylesheet" href="/leyi/bootstrap-3.3.5/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="/leyi/bootstrap-3.3.5/js/bootstrap.min.js"></script>

  <script src="/leyi/ckeditor/ckeditor.js"></script>
</head>
<body>
<div style="height:38px;background-color:#b3b3ff ">

</div>
<div class="container">
  <div class="row">
   <!-- =====================顶部文章=================== -->
	<ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin:10px 0;">
	   <c:forEach items="${topThemes}" var="item">
	       <c:if test="${themeTreeUp[0].id==item.id}"> <li class="active"><a href="/leyi/article/theme/${item.id}">${item.name}</a></li> </c:if>
	       <c:if test="${themeTreeUp[0].id!=item.id}"> <li><a href="/leyi/article/theme/${item.id}">${item.name}</a></li> </c:if>	
       </c:forEach>
	</ul>
  </div>
  <article>
	  <div class="row">
		 <div class="col-sm-12 text-center"><h3>${brief.name}</h3></div>
	  </div>
	  <div class="row">
	    <hr>
	  	<div class="col-sm-12 "> 关键词：${brief.keywords} </div>
	  </div>
	  <div class="row">
	     <hr>
	     <div class="col-sm-6 "> 来&nbsp;&nbsp;&nbsp;&nbsp;源：
	     <c:choose>
	     	<c:when test="${brief.source == 0}">自创</c:when>
	     	<c:when test="${brief.source == 1}">转摘</c:when>
	     	<c:when test="${brief.source == 2}">其他</c:when>
	     </c:choose>   
	     </div>
	  	 <div class="col-sm-6 "> 类&nbsp;&nbsp;&nbsp;&nbsp;型：
	  	 <c:choose>
	     	<c:when test="${brief.type == 0}">文本</c:when>
	     	<c:when test="${brief.type == 1}">图册</c:when>
	     	<c:when test="${brief.type == 2}">视频</c:when>
	     	<c:when test="${brief.type == 3}">语音</c:when>
	     	<c:when test="${brief.type == 4}">混合</c:when>
	     </c:choose>
	     </div>
	  </div>
	  <div class="row">
	    <hr>
	  	<div class="col-sm-12 "> 简&nbsp;&nbsp;&nbsp;&nbsp;介：${brief.brief} </div>
	  </div> 
	  <div class="row">
	    <hr>
	    <div class="col-sm-12 "> ${content.content} </div>
	  </div>
  </article>
</div>

</body>
</html>