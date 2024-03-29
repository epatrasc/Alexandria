<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<%@include file="includes/head.jsp"%>
<body>
	<div class="container">
		<%@include file="includes/header.jsp"%>
		<div role="main" class="container">
			<c:if test="${requestScope.listaPrestiti != null}">
				<c:if test="${utente.ruolo == 'amministratore'}">
				<table class="table table-bordered">
					<tr class="thead-light">
						<th scope="col">Utente</th>
						<th scope="col">Libro</th>
						<th scope="col">Data <br>Prestito</th>
						<th scope="col">Data <br>Restituzione</th>
						<th scope="col">Stato</th>
						<th scope="col">Azione</th>
					</tr>
					<c:forEach var="prestiti" items="${requestScope.listaPrestiti}"
						varStatus="loop">
						<tr>
							<td><span class="capitalize">${prestiti.nomeUtente}</span></td>
							<td><img src="${prestiti.urlImageLibro}" alt="${prestiti.titoloLibro}" class="zoom"></td>
							<td>${prestiti.dataPrestito}</td>
							<td>${prestiti.dataRestituzione}</td>
							<td>
								<c:if test="${prestiti.restituito}"><img src="<c:url value="/images/icon/book-returned.png"/>" height="24px" width="24px" class="rounded" alt="Restituito"/><span class="ml-1">Restituito</span></c:if>
								<c:if test="${!prestiti.restituito}"><img src="<c:url value="/images/icon/borrow-book-icon.png"/>" height="24px" width="24px" class="rounded" alt="Restituito"/><span class="ml-1">In prestito</span></c:if>
							</td>
							<td>
								<c:if test="${!prestiti.restituito}">
									<button onclick="Prestito.confirmRestituisci(${prestiti.idLibro})" class="btn btn-primary">Restituisci</button>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
				<c:if test="${utente.ruolo != 'amministratore'}">
				<table class="table table-bordered">
					<tr class="thead-light">
						<th scope="col">Libro</th>
						<th scope="col">Data <br>Prestito</th>
						<th scope="col">Data <br>Restituzione</th>
						<th scope="col">Stato</th>
						<th scope="col">Azione</th>
					</tr>
					<c:forEach var="prestiti" items="${requestScope.listaPrestiti}"
						varStatus="loop">
						<tr>
							<td><img src="${prestiti.urlImageLibro}" alt="${prestiti.titoloLibro}" class="zoom"></td>
							<td>${prestiti.dataPrestito}</td>
							<td>${prestiti.dataRestituzione}</td>
							<td>
								<c:if test="${prestiti.restituito}"><img src="<c:url value="/images/icon/book-returned.png"/>" height="24px" width="24px" class="rounded" alt="Restituito"/><span class="ml-1">Restituito</span></c:if>
								<c:if test="${!prestiti.restituito}"><img src="<c:url value="/images/icon/borrow-book-icon.png"/>" height="24px" width="24px" class="rounded" alt="Restituito"/><span class="ml-1">In prestito</span></c:if>
							</td>
							<td>
								<c:if test="${!prestiti.restituito}">
									<button onclick="Prestito.confirmRestituisci(${prestiti.idLibro})" class="btn btn-primary">Restituisci</button>	
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
			</c:if>
			<c:if test="${requestScope.listaPrestiti == null}">
				<c:if test="${utente.ruolo != 'amministratore'}">
				<p>Non hai preso in prestito nessun libro</p>
				</c:if>
				<c:if test="${utente.ruolo == 'amministratore'}">
				<p>Nessun libro risulta in prestito</p>
				</c:if>
				<button type="button" onclick="window.location.href='<c:url value="/home"/>'"class="btn btn-outline-primary pointer">Torna alla Home Page</button>
			</c:if>
		</div>
	</div>
	<%@include file="/includes/footer.jsp"%>
</body>
</html>

