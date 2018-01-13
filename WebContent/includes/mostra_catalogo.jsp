<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="container">
	<c:forEach var="libro" items="${requestScope.libri}" varStatus="loop">
		<c:if test="${loop.index==0 || loop.index%3==0}">
			<div class="row mt-4">
				<div class="card-deck">
		</c:if>
		<div class="card card-outline-secondary text-center w-25 pb-1">
			<img class="card-img-top mb-1" src="${libro.imageUrl}"
				style="height: 100%" alt="${libro.titolo}">
			<c:if test="${utente != null}">
				<div class="card-block">
					<button id="modifica" class="btn  btn-outline-primary mb-2"
						onclick="window.location.href='<c:url value="/libro/visualizza?idLibro=${libro.id}" />'"
						type="submit">Dettaglio</button>
					<c:if test="${libro.disponibile}">
						<c:if test="${utente.ruolo != 'amministratore'}">
							<button id="presta" onclick="Prestito.presta(${libro.id})"
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
							<button id="presta" onclick="Prestito.restituisci(${libro.id})"
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
		<c:if
			test="${(loop.index + 1)%3==0 || fn:length(requestScope.libri)==loop.index}">
</div>
</div>
</c:if>
</c:forEach>
</div>

<style>
button{
min-width: 92px
}
</style>
<!-- Modal -->
<div class="modal fade" id="prestaModalAskUser" tabindex="-1"
	role="dialog" aria-labelledby="modalLabelQuestion" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalLabelQuestion">Inserire
					l'utente a cui effettuare il prestito</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<input id="idUtenteModal" type="number" required="required">
				<input id="idLibroModal" type="hidden">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				<button onclick="Prestito.prestaModal(this)" type="button"
					class="btn btn-outline-primary">Presta</button>
			</div>
		</div>
	</div>
</div>
