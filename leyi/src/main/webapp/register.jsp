<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>注册</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">

  <link rel="stylesheet" href="/leyi/bootstrap-3.3.5/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="/leyi/bootstrap-3.3.5/js/bootstrap.min.js"></script>

  <script src="/leyi/ckeditor/ckeditor.js"></script>
</head>
<body>
<div style="height:38px;background-color:#b3b3ff ;margin:0 0 10px 0">

</div>
<div class="container" > 
  <h3 style="text-align:center;margin:20px 0 ">用户注册</h3>
  <div class="row">
	<form class="form-horizontal" id="registerForm" action="register" method ="post" role="form" >
	  <div class="form-group">
	    <label for="username" class="col-sm-2 control-label">用户名<span style="color:red">*</span></label>
	    <div class="col-sm-5">
	      <input type="text" class="form-control" id="username" name="username" value="" maxLength="25" required placeholder="请输入用户名">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for=email class="col-sm-2 control-label">邮箱<span style="color:red">*</span></label>
	    <div class="col-sm-5">
	      <input class="form-control" type="text" id="email" name="email" required placeholder="请输入邮箱">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="password" class="col-sm-2 control-label">密码<span style="color:red">*</span></label>
	    <div class="col-sm-5">
	      <input class="form-control" type="text" id="password" name="passwd" required placeholder="请输入密码">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="re-password" class="col-sm-2 control-label">确认密码<span style="color:red">*</span></label>
	    <div class="col-sm-5">
	      <input class="form-control" type="text" id="re-password" name="re-password" required placeholder="请输入确认密码">
	    </div>
	  </div>	  	  
	  <div class="form-group">
        <label for="age" class="col-sm-2 control-label">年龄</label>
        <div class="col-sm-3">
          <input class="form-control" type="number" id="age" name="age" min="1" max="999" placeholder="请输入年龄">
        </div>
      </div>
 	  <div class="form-group">
        <label  for="sex" class="col-sm-2 control-label">性别</label>
        <div class="col-sm-3">
          <select class="form-control" id="sex" name="sex" >
           <option value="0">男</option>
           <option value="1">女</option>
           <option value="2">其他</option>
          </select>
        </div>
      </div>    
      <div class="form-group">
        <label for="city" class="col-sm-2 control-label">所在城市</label>
        <div class="col-sm-3">
          <input class="form-control" id="city" name="city" placeholder="请输入城市" >
        </div>
      </div> 
      <div class="form-group">
        <label for="favourite" class="col-sm-2 control-label">兴趣爱好</label>
        <div class="col-sm-5">
          <input class="form-control" id="favourite" name="favourite" placeholder="请输入兴趣爱好" >
        </div>
      </div> 
      <div class="form-group">
        <label for="profession" class="col-sm-2 control-label">职业</label>
        <div class="col-sm-5">
          <input class="form-control" id="favourite" name="profession" placeholder="请输入职业" >
        </div>
      </div>       
      <div class="form-group">
        <label for="introduce" class="col-sm-2 control-label">个人简介<span style="color:red">*</span></label>
        <div class="col-sm-8">
          <textarea class="form-control" id="introduce" name="introduce" rows=5 required ></textarea>
        </div>
      </div>
      <div class="form-group">
        <label for="introduce" class="col-sm-2 control-label">照片</label>
        <div class="col-sm-5">
          <input type="file" id="picture" name="picture" >
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

</body>
</html>