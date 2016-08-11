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
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff ;margin-bottom:5px;">

</div>
<div class="container">
  <c:if test="${not empty operator.userId and operator.userId >0}">
  <div class="row">
     <div class="col-md-3" >
      <div class="row" style="height:250px;overflow:auto ;padding:3px;border:3px #CECEF6 solid ;border-radius:5px;">
       <img style="float:left;margin:3px;" src="/leyi/showPic/${userInfo.username}/${userInfo.picture }" width="150" height="150" alt="Profile Photo" >  
         <a href="/leyi/${userInfo.username}"><b>&nbsp;&nbsp;&nbsp;${userInfo.username}</b></a><br>&nbsp;&nbsp;&nbsp; ${userInfo.introduce}
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
	  <div class="panel panel-info">
        <div class="panel-heading text-center"><h3 class="panel-title">${brief.name}</h3></div>
	    <div class="panel-body">
	     <article>
			  <div class="row">
			  	<div class="col-sm-12 "> 关键词：${brief.keywords} </div>
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
			  <c:if test="${mode == 'review'}">
			  <div class="row">
			     <hr>
			     <form id="reviewForm" method="post" action="">
			     <input type="hidden" name="articleId" value="${brief.id }">
			     <div class="form-group">
			        <label  class="col-sm-2 control-label">审核说明</label>
			        <div class="col-sm-10">
			          <textarea class="form-control" id="remark" name="remark" placeholder="请输入审核说明" rows="5" ></textarea>
			        </div>
			      </div>
		         <div class="col-sm-offset-4 col-sm-10">
		           <button type="button" class="btn btn-info" id="accept" style="margin:20px">&nbsp;&nbsp;通过&nbsp;&nbsp;</button>
		           <button type="button" class="btn btn-warning" id="refuse" style="margin:20px">&nbsp;&nbsp;拒绝&nbsp;&nbsp; </button>
		         </div>
		         </form>
		         <script type="text/javascript">
		         $(function(){ 
			 		$("#accept").click(function(){
						$("#reviewForm").attr('action','/leyi/article/accept');
						$("#reviewForm").submit();
					});
			 		$("#refuse").click(function(){
						$("#reviewForm").attr('action','/leyi/article/refuse');
						$("#reviewForm").submit();
					});
		         });
		         </script>
      		  </div>
      		  </c:if>
		  </article>
	    </div>
	  </div> <!-- 文章panel -->
    </div>
  </div><!-- end of row -->
  </c:if>
  
  <c:if test="${empty operator.userId or operator.userId < 1}">
  <!-- 未登录用户 -->
  <div class="row">
    <ul class="nav nav-tabs pull-right" >
     <li><a href="/leyi/login.jsp" target="_blank">登录</a></li>
     <li><a href="/leyi/register.jsp"  target="_blank">注册</a></li>
	</ul>
  </div>
  <div class="row">
     <div class="col-md-3" >
      <div class="row" style="height:250px;overflow:auto ;padding:3px;border:3px #CECEF6 solid ;border-radius:5px;">
       <img style="float:left;margin:3px;" src="/leyi/showPic/${userInfo.username}/${userInfo.picture }" width="150" height="150" alt="Profile Photo" >  
        <a href="/leyi/${userInfo.username}"><b>&nbsp;&nbsp;&nbsp;${userInfo.username}</b></a><br>&nbsp;&nbsp;&nbsp; ${userInfo.introduce}
       </div>
	 </div>
	 
   <!--======================中间主要内容  ===================--> 
    <div class="col-md-9 light-gray-bg">
	  <div class="panel panel-info">
        <div class="panel-heading text-center"><h3 class="panel-title">${brief.name}</h3></div>
	    <div class="panel-body">
	     <article>
			  <div class="row">
			  	<div class="col-sm-12 "> 关键词：${brief.keywords} </div>
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
	  </div> <!-- 文章panel -->
    </div>
  </div><!-- end of row -->
  </c:if>
</div>

</body>
</html>