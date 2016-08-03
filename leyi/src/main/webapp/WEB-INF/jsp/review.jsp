<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>信息审核</title>
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
    <!-- 右面文章列表 -->
    <div class="col-md-8" >
      <div class="panel panel-info">
        <table class="table table-striped  table-bordered table-hover ">
          <thead>
   	        <tr><th>文章标题</th><th>关键词 </th><th>来源</th><th>类型 </th><th>操作 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${articles}" var="item">
            <tr>
              <td>${item.name}</td>
              <td>${item.keywords }</td>
              <td><a href="/leyi/article/review/${item.id}" target="_blank">审核</a></td>
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