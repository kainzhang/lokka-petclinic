<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SPECIES</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div id=species-message>
		<font color="green" size="18">
		<%
		if(request.getAttribute("message")!= null)
			out.print(request.getAttribute("message")+"<br>");
		%>
		</font>
	</div>
	<div id="species-search">
		<form action="SpeciesServlet?method=searchSpecies" method="post">
			<label>关键字：</label>
			<input type="text" name="keyword" value=${param.keyword}>
			<input type="submit" value="查询">
		</form>
	</div>
	<div id="species-insert">
		<form action="SpeciesServlet?method=insertSpecies" method="post">
			<label>新物种：</label>
			<input type="text" name="name">
			<input type="submit" value="添加">
		</form>
	</div>
	<div id="species-show">
		<table border="1">
			<c:forEach items="${list}" var="item">
				<tr>
					<td>${item.id }</td>
					<td>${item.name }</td>
					<td><label for="link1-trigger" class="modal-link" type="Species" title="${item.id}" onclick="setInfo(this)">修改</label></td>
					<td><a href="SpeciesServlet?method=deleteSpecies&id=${item.id}">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<%@ include file="popup.jsp" %>
	<%@ include file="footer.jsp" %>
</body>
</html></html>