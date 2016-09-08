<!-- 用户的个人简介 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = "";
	if(request.getServletPath().contains("userShow")){
		path = "";
	}else{
		path = "/detail";
	}
%>
<div class="row" style="height:250px;padding:0px;border:0px #CECEF6 solid ;border-radius:0px;">
   <p><img style="margin:0px;" src="/leyi/common/showPic/${userInfo.username}/${userInfo.picture }" width="100%" height="200" alt="Profile Photo" ></p>  
   <p class="text-center"><a href="/leyi/${userInfo.username}<%=path%>"><b>${userInfo.username}</b></a></p>

</div>
