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
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff;margin-bottom:5px; ">

</div>
<div class="container">
  <div class="row">
   <!-- =====================顶部主题=================== -->
	<ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin:10px 0;">
	   <c:forEach items="${topThemes}" var="item">
	       <c:if test="${themeTreeUp[0].id==item.id}"> <li class="active"><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}">${item.name}</a></li> </c:if>
	       <c:if test="${themeTreeUp[0].id!=item.id}"> <li><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}">${item.name}</a></li> </c:if>	
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
	       <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}">${item.name}</a></li> </c:if>
	      </c:forEach>
	      </ol>
        </div>
  		<div class="panel-body">
    	  <p id="themeDesc-show">&nbsp;&nbsp;&nbsp;&nbsp;${currTheme.descInfo}</p>
    	  <p id="keywords-show">关键词：${currTheme.keywords}</p>
   		</div>
	   	<ul class="list-group">
	     <c:forEach items="${children}" var="item">
	       <li class="list-group-item"><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}">${item.name}</a></li>
	     </c:forEach>
	   	</ul>
  	  </div>
    </div><!-- end of 左面主题 -->
    <!-- 右面文章列表 -->
    <div class="col-md-8" >
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
	     	<a href="/leyi/${operator.username}/article_mgr/editing" target="_blank">新增文章</a>
        </div>
        <table class="table table-striped  table-bordered table-hover ">
          <thead>
   	        <tr><th>文章标题</th><th>关键词 </th><th>操作 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${currArticles}" var="item">
            <tr>
              <td>${item.name}</td>
              <td>${item.keywords }</td>
              <td>
                [<a href="/leyi/${operator.username}/article_mgr/editing?articleId=${item.id }" target="_blank">编辑</a>]&nbsp;&nbsp;
                [<a href="/leyi/${operator.username}/article_mgr/delete?articleId=${item.id }">删除</a>]&nbsp;&nbsp;
                [<a href="/leyi/${operator.username}/article_mgr/detail/${item.id }"  target="_blank">显示</a>]&nbsp;&nbsp;
              </td>
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