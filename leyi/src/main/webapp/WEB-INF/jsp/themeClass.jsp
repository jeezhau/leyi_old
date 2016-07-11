<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>主题分类管理</title>
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
   <!-- =====================顶部主题分类=================== -->
	<ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin:10px 0;">
	   <c:forEach items="${topThemes}" var="item">
	       <c:if test="${themeTreeUp[0].id==item.id}"> <li class="active"><a href="${item.id}">${item.name}</a></li> </c:if>
	       <c:if test="${themeTreeUp[0].id!=item.id}"> <li><a href="${item.id}">${item.name}</a></li> </c:if>
       </c:forEach>
	</ul>
  <div class="panel panel-info">
   <div class="panel-heading" style="margin:0">
	   <ol class="breadcrumb" style="margin:0;">
	     <c:forEach items="${themeTreeUp}" var="item">
	       <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
	       <c:if test="${currTheme.id!=item.id}"> <li><a href="${item.id}">${item.name}</a></li> </c:if>
	     </c:forEach>
	   </ol>
	   <div class="btn-toolbar btn-toolbar-info" role="toolbar">
  		<div class="btn-group">
	     <button class="btn btn-primary" id="addTheme">新增主题分类</button>
	     <button class="btn btn-primary" id="editTheme">编辑主题分类</button>
	     <button class="btn btn-primary" id="deleteTheme">删除主题分类</button>
	    </div>
       </div>
   </div>
   <div class="panel-body">
    <p id="themeDesc-show">&nbsp;&nbsp;&nbsp;&nbsp;${currTheme.descInfo}</p>
    <p id="keywords-show">关键词：${currTheme.keywords}</p>
    
   </div>
   <table class="table table-striped  table-bordered table-hover ">
       <thead>
   	      <tr><th>主题名称</th><th>关键词 </th><th>更新时间 </th></tr>
       </thead>
       <tbody> 
        <c:forEach items="${children}" var="item">
         <tr>
              <td><a href="${item.id}">${item.name}</a></td>
              <td>${item.keywords }</td>
              <td><fmt:formatDate value="${item.updateTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
         </tr>
        </c:forEach>
     </table>
   </div>
  </div>
</div>

<!-- 主题分类模态框（Modal） -->
<div class="modal fade " id="themeModal" tabindex="-1" role="dialog" aria-labelledby="themeModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="themeModalLabel">新增主题分类</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" id="themeForm" method ="post" action="add" role="form" >
               <div class="form-group">
               	  <input type="hidden" id="themeId" name="id" value="${currTheme.id}">
               </div>
               <div id="topThemeFlag" class="form-group">
			       <label class="col-sm-2 control-label">顶层主题</label>
			       <div class="col-sm-10 checkbox">
					 <label><input id="topThemeFlag_true" type="checkbox"> 是</label>
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-sm-2 control-label">主题名称</label>
			      <div class="col-sm-10">
			         <input class="form-control" name="name" id="themeName" type="text" value="" placeholder="请输入主题名称..." >
			      </div>
			   </div>
			   <div class="form-group">
			      <label for="keywords" class="col-sm-2 control-label">关键词</label>
			      <div class="col-sm-10">
			         <textarea class="form-control" name="keywords" id="keywords" placeholder="请输入关键词..." ></textarea>
			      </div>
			   </div>
			   <div class="form-group has-success">
			      <label class="col-sm-2 control-label" for="themeDesc">主题描述</label>
			      <div class="col-sm-10">
			         <textarea  class="form-control" name="descInfo" id="themeDesc" rows="8" placeholder="请输入主题描述..." ></textarea>
			      </div>
			   </div>
			</form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="submit">提交</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
	$(function(){ 
		var mode = '';
		$("#addTheme").click(function(){
			mode = 'add';
			$("#themeModal").modal('show');
			$("#themeForm").attr('action','add');
			$("#themeName").val('');
			$("#themeDesc").val('');
			$("#keywords").val('');
		});
		$("#editTheme").click(function(){
			mode = 'edit';
			$("#themeModal").modal('show');
			$("#topThemeFlag").hide();
			$("#themeForm").attr('action','edit');
			$("#themeName").val('${currTheme.name}');
			$("#themeDesc").val($("#themeDesc-show").text().trim());
			var kws = $("#keywords-show").text().trim();
			$("#keywords").val(kws.substring('关键词：'.length));
		});
		$("#deleteTheme").click(function(){
			mode = 'delete';
			$("#themeForm").attr('action','delete');
			$("#themeForm").submit();
		});
		$("#submit").click(function(){
			if(mode === 'add'){
				if($("#topThemeFlag_true").is(':checked')){
					$("#themeId").val('');
				}
			}
			$("#themeForm").submit();
			$("#themeModal").modal('hide');
		});
	});
</script>

</body>
</html>