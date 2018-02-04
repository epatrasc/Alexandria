<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header clearfix">
	<%@include file="menu.jsp" %>
	<div class="row">
		<div class="col-4"><h4 class="text-muted">Library of <strong>Alexandria</strong></h4></div>
		<c:if test="${isLogged == true && utente != null}">
		<div class="col">
			<div class="login-space">
				<div class="row">
					<div class="col-4">
						<span class="clear rounded-square  rounded-square--primary">${utente.nome.substring(0, 1).toUpperCase()}</span>
					</div>
					<div class="col">
						<div class="text-primary capitalize"><b>${utente.nome}</b><em class="text-secondary"> (${utente.ruolo})</em></div>
						<div class="w-100"></div>
						<a class="btn btn-sm btn-outline-secondary" href="<c:url value="/logout" />">Logout</a>
					</div>
				</div>
			</div>
		</div>
		</c:if>
	</div>
</header>
<c:if test="${isLogged == true && utente != null}">
	<%@include file="breadcrumb.jsp" %>
</c:if>