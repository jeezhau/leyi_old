<!-- 顶级主题菜单栏 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin-bottom:10px;">
  <c:forEach items="${topThemes}" var="item">
      <c:if test="${currTheme.id==item.id}"> <li class="active"><a href="/leyi/${userInfo.username}/theme/${item.id}">${item.name}</a></li></c:if>
      <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/${userInfo.username}/theme/${item.id}">${item.name}</a></li></c:if>
     </c:forEach>
 </ul> 
