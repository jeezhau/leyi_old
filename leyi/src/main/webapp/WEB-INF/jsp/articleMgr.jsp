<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>文章记录管理</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">

  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

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
  
  <div class="row">
	<!-- 左面主题 -->
    <div class="col-md-4" >
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
          <ol class="breadcrumb" style="margin:0;">
	      <c:forEach items="${themeTreeUp}" var="item">
	       <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
	       <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/article/theme/${item.id}">${item.name}</a></li> </c:if>
	      </c:forEach>
	      </ol>
        </div>
  		<div class="panel-body">
    	  <p id="themeDesc-show">&nbsp;&nbsp;&nbsp;&nbsp;${currTheme.descInfo}</p>
    	  <p id="keywords-show">关键词：${currTheme.keywords}</p>
   		</div>
	   	<ul class="list-group">
	     <c:forEach items="${children}" var="item">
	       <li class="list-group-item"><a href="/leyi/article/theme/${item.id}">${item.name}</a></li>
	     </c:forEach>
	   	</ul>
  	  </div>
    </div><!-- end of 左面主题 -->
    <!-- 右面文章列表 -->
    <div class="col-md-8" >
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
	      <div class="btn-toolbar btn-toolbar-info" role="toolbar">
	        <div class="btn-group">
	          <button class="btn btn-primary" id="addArticle">新增文章</button>
	          <button class="btn btn-primary" id="editArticle">编辑文章</button>
	          <button class="btn btn-primary" id="deleteArticle">删除文章</button>
	        </div>
          </div>
        </div>
        <table class="table table-striped  table-bordered table-hover ">
          <thead>
   	        <tr><th>标题</th><th>关键词 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${currArticles}" var="item">
            <tr>
              <td>${item.name}</td>
              <td>${item.keywords }</td>
            </tr>
           </c:forEach> 
          </tbody>
        </table>
      </div>
    </div><!-- end of 右面文章列表 -->
    
  </div>
</div>

<script>

</script>

</body>
</html>