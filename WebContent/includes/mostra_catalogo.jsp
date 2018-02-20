<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="container clearfix">
	<c:forEach var="libroAction" items="${requestScope.libriAction}" varStatus="loop">
		<c:if test="${loop.index==0 || loop.index%3==0}">
			<div class="row mt-4">
		</c:if>
		<div class="col">
			<div class="card card-outline-secondary text-center w-100 pb-1">
				<img class="card-img-top pointer" src="${libroAction.libro.imageUrl}"
					onclick="window.location.href='<c:url value="/libro/visualizza?idLibro=${libroAction.libro.id}" />'"
					style="height: 100%" alt="${libroAction.libro.titolo}">
				<c:if test="${utente == null}">
					<button id="dettaglio" class="btn  btn-outline-primary mt-2"
						onclick="window.location.href='<c:url value="/libro/visualizza?idLibro=${libroAction.libro.id}" />'"
						type="submit">Dettaglio</button>
				</c:if>
				<c:if test="${utente != null}">
					<div class="card-block mt-1">
						<button id="dettaglio" class="btn  btn-outline-primary mb-2 btn-fixed-size"
							onclick="window.location.href='<c:url value="/libro/visualizza?idLibro=${libroAction.libro.id}" />'"
							type="submit">Dettaglio</button>
							<c:if test="${utente.ruolo != 'amministratore'}">
								<c:if test="${libroAction.action == 'presta'}">
									<button id="presta"
										onclick="Prestito.confirmPresta(${libroAction.libro.id},'${libroAction.libro.titolo}')"
										class="btn  btn-outline-primary mb-2 btn-fixed-size" type="submit">Prendi
										in prestito</button>
								</c:if>
								<c:if test="${libroAction.action == 'restituisci'}">
									<button id="restituisci"
										onclick="Prestito.confirmRestituisci(${libroAction.libro.id},'${libroAction.libro.titolo}')"
										class="btn  btn-outline-primary mb-2 btn-fixed-size" type="submit">Restituisci</button>
								</c:if>
								<c:if test="${libroAction.action == 'no-action'}">
									<button id="no-action" disabled
										class="btn  btn-outline-primary mb-2 btn-fixed-size" type="submit">Non Disponibile</button>
								</c:if>
							</c:if>
						<c:if test="${utente.ruolo == 'amministratore'}">
							<c:if test="${libroAction.libro.disponibile}">
								<button id="prestaButton" type="button"
									class="btn btn-outline-primary mb-2  btn-fixed-size" data-toggle="modal"
									data-target="#prestaModalAskUser"
									onClick="$('#idLibroModal').val(${libroAction.libro.id})">Presta</button>
							</c:if>
							<c:if test="${!libroAction.libro.disponibile}">
								<button id="presta"
									onclick="Prestito.confirmRestituisci(${libroAction.libro.id})"
									class="btn  btn-outline-primary mb-2  btn-fixed-size">Restituisci</button>
							</c:if>
							<button id="modifica" class="btn  btn-outline-primary mb-2  btn-fixed-size"
								onclick="window.location.href='<c:url value="/libro/modifica?idLibro=${libroAction.libro.id}" />'"
								type="submit">Modifica</button>
							<button id="cancella" onclick="Libro.cancella(${libroAction.libro.id})"
								class="btn  btn-outline-primary mb-2  btn-fixed-size">Cancella</button>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
		<c:if test="${(loop.index + 1)%3==0 || fn:length(requestScope.libriAction)==loop.index}" > </div> </c:if>
	</c:forEach>
</div>
