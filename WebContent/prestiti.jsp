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
						<th scope="col">Data Prestito</th>
						<th scope="col">Data Restituzione</th>
						<th scope="col">Stato</th>
						<th scope="col">Azione</th>
					</tr>
					<c:forEach var="prestiti" items="${requestScope.listaPrestiti}"
						varStatus="loop">
						<tr>
							<td>${prestiti.idUtente}</td>
							<td>${prestiti.idLibro}</td>
							<td>${prestiti.dataPrestito}</td>
							<td>${prestiti.dataRestituzione}</td>
							<td>
								<c:if test="${prestiti.restituito}">Restituito</c:if>
								<c:if test="${!prestiti.restituito}">In prestito</c:if>
							</td>
							<td>
								<c:if test="${!prestiti.restituito}">
									<button onclick="Prestito.restituisci(${prestiti.idLibro})">Restituisci</button>
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
						<th scope="col">Data Prestito</th>
						<th scope="col">Data Restituzione</th>
						<th scope="col">Stato</th>
						<th scope="col">Azione</th>
					</tr>
					<c:forEach var="prestiti" items="${requestScope.listaPrestiti}"
						varStatus="loop">
						<tr>
							<td>${prestiti.idLibro}</td>
							<td>${prestiti.dataPrestito}</td>
							<td>${prestiti.dataRestituzione}</td>
							<td>
								<c:if test="${prestiti.restituito}">Restituito</c:if>
								<c:if test="${!prestiti.restituito}">In prestito</c:if>
							</td>
							<td>
								<c:if test="${!prestiti.restituito}">
									<button onclick="Prestito.restituisci(${prestiti.idLibro})">Restituisci</button>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
			</c:if>
			<c:if test="${requestScope.listaPrestiti == null}">
				<p>Non hai preso in prestito nessun libro</p>
			</c:if>
		</div>
	</div>
	<%@include file="/includes/footer.jsp"%>
</body>
</html>

