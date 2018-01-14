<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="container clearfix">
	<c:forEach var="libro" items="${requestScope.libri}" varStatus="loop">
		<c:if test="${loop.index==0 || loop.index%3==0}">
			<div class="row mt-4">
		</c:if>
		<div class="col">
			<div class="card card-outline-secondary text-center w-100 pb-1">
				<img class="card-img-top pointer" src="${libro.imageUrl}"
					onclick="window.location.href='<c:url value="/libro/visualizza?idLibro=${libro.id}" />'"
					style="height: 100%" alt="${libro.titolo}">
				<c:if test="${utente == null}">
					<button id="modifica" class="btn  btn-outline-primary mt-2"
						onclick="window.location.href='<c:url value="/libro/visualizza?idLibro=${libro.id}" />'"
						type="submit">Dettaglio</button>
				</c:if>
				<c:if test="${utente != null}">
					<div class="card-block mt-1">
						<button id="modifica" class="btn  btn-outline-primary mb-2"
							onclick="window.location.href='<c:url value="/libro/visualizza?idLibro=${libro.id}" />'"
							type="submit">Dettaglio</button>
						<c:if test="${libro.disponibile}">
							<c:if test="${utente.ruolo != 'amministratore'}">
								<button id="presta"
									onclick="Prestito.confirmPresta(${libro.id})"
									class="btn  btn-outline-primary mb-2" type="submit">Prendi
									in prestito</button>
							</c:if>
							<c:if test="${utente.ruolo == 'amministratore'}">
								<button id="prestaButton" type="button"
									class="btn btn-outline-primary mb-2" data-toggle="modal"
									data-target="#prestaModalAskUser"
									onClick="$('#idLibroModal').val(${libro.id})">Presta</button>
							</c:if>
						</c:if>
						<c:if test="${utente.ruolo == 'amministratore'}">
							<c:if test="${!libro.disponibile}">
								<button id="presta"
									onclick="Prestito.confirmRestituisci(${libro.id})"
									class="btn  btn-outline-primary mb-2">Restituisci</button>
							</c:if>
							<button id="modifica" class="btn  btn-outline-primary mb-2"
								onclick="window.location.href='<c:url value="/libro/modifica?idLibro=${libro.id}" />'"
								type="submit">Modifica</button>
							<button id="cancella" onclick="Libro.cancella(${libro.id})"
								class="btn  btn-outline-primary mb-2">Cancella</button>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
		<c:if test="${(loop.index + 1)%3==0 || fn:length(requestScope.libri)==loop.index}" > </div> </c:if>
	</c:forEach>
</div>
