<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<%@include file="includes/head.jsp"%>
<body>
	<div class="container">
		<%@include file="includes/header.jsp"%>
		<div role="main" class="container">
			<c:if test="${requestScope.listaPrestiti != null}">
				<table>
					<tr>
						<th>Libro</th>
						<th>Data Prestito</th>
						<th>Data Data Restituzione</th>
						<th> </th>
					</tr>
					<c:forEach var="prestiti" items="${requestScope.listaPrestiti}"
						varStatus="loop">
						<tr>
							<td>${prestiti.idLibro}</td>
							<td>${prestiti.dataPrestito}</td>
							<td>${prestiti.dataRestituzione}</td>
							<td><c:if test="${!prestiti.restituito}"><button onclick="Prestito.restituisci(${prestiti.idLibro})">Restituisci</button></c:if></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${requestScope.listaPrestiti == null}">
				<p>Non hai preso in prestito nessun libro</p>
			</c:if>
		</div>
	</div>
	<%@include file="/includes/footer.jsp"%>
</body>
</html>

