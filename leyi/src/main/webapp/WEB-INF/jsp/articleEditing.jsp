<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>文章编辑</title>
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
	       <c:if test="${themeTreeUp[0].id==item.id}"> <li class="active"><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}">${item.name}</a></li> </c:if>
	       <c:if test="${themeTreeUp[0].id!=item.id}"> <li><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}">${item.name}</a></li> </c:if>	
       </c:forEach>
	</ul>
  </div>
  
  <div class="row">
	<form class="form-horizontal" id="articleForm" action="add" method ="post" role="form" >
	  <c:if test="${not empty error}">
      <div class="alert alert-warning alert-dismissable">${error}
        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
      </div>
	  </c:if>
	  <div class="form-group">
	    <label for="name" class="col-sm-2 control-label">归属主题<span style="color:red">*</span></label>
	    <div class="col-sm-5">
	      <input type="text" class="form-control" value="${currTheme.name}" readonly>
	      <input type="hidden" class="form-control" name="themeId" value="${currTheme.id}" >
	      <c:if test="${not empty themeId}">
	      <div class="alert alert-warning alert-dismissable">${themeId}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
		  </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="name" class="col-sm-2 control-label">标题<span style="color:red">*</span></label>
	    <div class="col-sm-5">
	      <input type="hidden" name="id" value="${brief.id}">
	      <input type="text" class="form-control" id="name" name="name" value="${brief.name}" maxLength=50 required placeholder="请输入标题">
	      <c:if test="${not empty name}">
	      <div class="alert alert-warning alert-dismissable">${name}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
		  </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="keywords" class="col-sm-2 control-label">关键词<span style="color:red">*</span></label>
	    <div class="col-sm-10">
	      <textarea class="form-control" id="keywords" name="keywords" maxLength=255 required placeholder="请输入关键词">${brief.keywords}</textarea>
	      <c:if test="${not empty keywords}">
	      <div class="alert alert-warning alert-dismissable">${keywords}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
		  </c:if>
	    </div>
	  </div>
	  <div class="form-group">
        <label  class="col-sm-2 control-label">来源<span style="color:red">*</span></label>
        <div class="col-sm-3">
          <select class="form-control" id="source" name="source" required>
           <option value="0">自创</option>
           <option value="1">转摘</option>
           <option value="2">其他</option>
          </select>
          <c:if test="${not empty source}">
	      <div class="alert alert-warning alert-dismissable">${source}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
		  </c:if>
        </div>
        <label  class="col-sm-2 control-label">类型<span style="color:red">*</span></label>
        <div class="col-sm-3">
          <select class="form-control" id="type" name="type" required>
           <option value="0">文本</option>
           <option value="1">图册</option>
           <option value="2">视频</option>
           <option value="3">语音</option>
           <option value="4">混合</option>
          </select>
          <c:if test="${not empty type}">
	      <div class="alert alert-warning alert-dismissable">${type}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
		  </c:if>
        </div>
      </div>
  	
      <div class="form-group">
        <label  class="col-sm-2 control-label">简介<span style="color:red">*</span></label>
        <div class="col-sm-10">
          <textarea class="form-control" id="brief" name="brief" placeholder="请输入简介" rows="5" maxLength=600 required>${brief.brief}</textarea>
          <c:if test="${not empty brief}">
	      <div class="alert alert-warning alert-dismissable">${brief}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
		  </c:if>
        </div>
      </div> 
      <div class="form-group">
        <label class="col-sm-2 control-label">内容<span style="color:red">*</span></label>
        <div class="col-sm-10">
          <div id="hidden-Content" style="display:none">${content.content }</div>
          <textarea class="form-control" id="content" name="content" maxLength=10240 required></textarea>
          <c:if test="${not empty content}">
	      <div class="alert alert-warning alert-dismissable">${content}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
		  </c:if>
        </div>
      </div>
      <div class="form-group">
         <div class="col-sm-offset-5 col-sm-10">
           <button type="submit" class="btn btn-info" id="save" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
         </div>
      </div>
	</form>
	
  </div>
</div>

<script>
$(function(){ 
	var articleId = '${brief.id}';
	CKEDITOR.replace( 'content' );
	if(articleId){
		var content = CKEDITOR.instances['content'];
		content.setData($('#hidden-Content').html()); 
		$('#source').val('${brief.source}');
		$('#type').val('${brief.type}');
	}
	
	$("#save").click(function(){
		/* if(!$("#articleForm").checkValidity()){
			return false;	
		} */
		if(articleId){
			$("#articleForm").attr('action','edit');
		}else{
			$("#articleForm").attr('action','add');
		}
		return true;
		//$("#articleForm").submit();
	});
	$("#reset").click(function(){
		if(articleId){
			window.location.reload();
		}else{
			$("#articleForm").reset();
		}
	});
});
</script>

</body>
</html>