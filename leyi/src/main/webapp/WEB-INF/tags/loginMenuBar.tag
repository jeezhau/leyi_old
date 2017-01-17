<!-- 根据用户是否登录生成顶部菜单栏 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <div class="row">
   <c:if test="${empty operator.userId or operator.userId < 1}">
  	<ul class="nav nav-tabs pull-right" >
     <li><a href="login.jsp">登录</a></li>
	</ul>
	</c:if>
	<c:if test="${not empty operator.userId and operator.userId > 0}">
  	<ul class="nav nav-tabs pull-right" >
     <li><a href="/leyi/${operator.username }">我的主页</a></li>
     <li><a href="/leyi/logout">退出</a></li>
	</ul>
   </c:if>
  </div>
