<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<%@include file="includes/head.jsp"%>
<body>
	<div class="container">
		<%@include file="includes/header.jsp"%>
		<div role="main" class="container">
			<c:if test="${action.equals('visualizza') }"><%@include file="libro/visualizza.jsp" %></c:if>
			<c:if test="${action.equals('modifica') }"><%@include file="libro/modifica.jsp" %></c:if>
			<c:if test="${action.equals('aggiungi') }"><%@include file="libro/aggiungi.jsp" %></c:if>
		</div>
	</div>
	<%@include file="/includes/footer.jsp"%>
	<script src="<c:url value="/script/libro/libro.js" />"></script>
</body>
</html>

