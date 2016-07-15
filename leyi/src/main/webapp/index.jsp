<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>Jee的主页</title>
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
  	<ul class="nav nav-tabs pull-right" >
     <li><a href="login.jsp" target="_blank">登录</a></li>
     <li><a href="register.jsp"  target="_blank">注册</a></li>
	</ul>
  </div>
  <div class="row">
<!--=============================左边菜单链接  ===================================--> 
    <div class="col-md-3" >
      <div class="row" style="height:250px;overflow:auto ;padding:3px;border:0px #443322 solid ;">
        <img style="float:left;margin:3px;" src="images/profile-photo.jpg" width="150" height="150" alt="Profile Photo" class="img-responsive">  
        
          个人履历:<br>
        	根据 HTML5 规范：
强烈建议为 html 根元素指定 lang 属性，从而为文档设置正确的语言。这将有助于语音合成工具确定其所应该采用的发音，
有助于翻译工具确定其翻译时所应遵守的规则等等。更多关于 lang 属性的知识可以从 此规范 中了解。
        	
        </div>     

        
        <nav class="navbar navbar-info "  role="navigation">          
          <ul class="nav nav-tabs nav-stacked">
		   <li class="active"><a href="#">Home</a></li>
		   <li><a href="#">SVN</a></li>
		   <li><a href="#">iOS</a></li>
		   <li><a href="#">VB.Net</a></li>
		   <li><a href="#">Java</a></li>
		   <li><a href="#">PHP</a></li>
		</ul> 
        </nav>
        
        
  </div>
<!--======================中间主要内容  ===================--> 
  <div class="col-md-9 light-gray-bg">
    <!-- =====================顶部主题分类=================== -->
	<ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin:10px 0;">
	   <li class="active"><a href="#">Home</a></li>
	   <li><a href="#">SVN</a></li>
	   <li><a href="#">iOS</a></li>
	   <li><a href="#">VB.Net</a></li>
	   <li><a href="#">Java</a></li>
	   <li><a href="#">PHP</a></li>
	</ul>
		  
    <div class="panel panel-info">
      <div class="panel-heading">
			<h3 class="panel-title">文章标题</h3>
		</div>
		<div class="panel-body">
		文章简介
		</div>
	  </div> <!-- 文章panel -->
	  <div class="panel panel-info">
      <div class="panel-heading">
			<h3 class="panel-title">文章标题</h3>
		</div>
		<div class="panel-body">
		文章简介
		</div>
	  </div> <!-- 文章panel -->
	  <div class="panel panel-info">
      <div class="panel-heading">
			<h3 class="panel-title">文章标题</h3>
		</div>
		<div class="panel-body">
		文章简介
		</div>
	  </div> <!-- 文章panel -->
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