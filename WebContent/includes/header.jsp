<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header clearfix">
	<nav>
		<ul class="nav nav-pills float-right">
			<li class="nav-item">
				<a class="nav-link active" href="home">Home
					<span class="sr-only">(current)</span>
				</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="libri">Libri</a></li>
			<li class="nav-item"><a class="nav-link" href="prestito">Prestito</a></li>
		</ul>
	</nav>
	<div class="row">
		<div class="col-4"><h3 class="text-muted">Alexandria</h3></div>
		<c:if test="${isLogged == true}">
		<div class="col">
			<div class="login-space">
				<div class="row">
					<div class="col-3">
						<img alt="utente" src="http://via.placeholder.com/65x65" />
					</div>
					<div class="col">
						<div class="h5 text-primary capitalize">${utente.nome}<span class="h6 text-secondary"> (${utente.ruolo})</span></div>
						<div class="w-100"></div>
						<a class="btn btn-sm btn-outline-secondary" href="<c:url value="/logout" />">Logout</a>
					</div>
				</div>
			</div>
		</div>
		</c:if>
	</div>
</header>
<%@include file="breadcrumb.jsp" %>