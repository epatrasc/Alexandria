<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${breadcrumbs != null}">
<nav aria-label="breadcrumb" role="navigation">
	<ol class="breadcrumb">
		<c:forEach items="${breadcrumbs.percorso}" var="breadcrumb" varStatus="loop">
		<c:if test="${!loop.last}">
			<li class="breadcrumb-item"><a href="<c:url value="${breadcrumb.get('url')}"/>">${breadcrumb.get('name')}</a></li>
		</c:if>
		<c:if test="${loop.last}">
			<li class="breadcrumb-item active" >${breadcrumb.get('name')}</li>
		</c:if>
		</c:forEach>
	</ol>
</nav>


</c:if>