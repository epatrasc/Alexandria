<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="card-deck">
	<c:forEach var="libro" items="${requestScope.libri}" varStatus="loop">
		<c:if test="${loop.index%2==0}">
			<div class="row">
		</c:if>
		<div class="cols">
			<div class="card">
				<div class="card-block">
					<img class="card-img-top" src="${libro.imageUrl}"
						alt="${libro.titolo}">
					<div class="card-body">
						<h4 class="card-title">${libro.titolo}</h4>
						<p class="card-text">${libro.autori}</p>
						<p class="card-text">${libro.descrizione}</p>
						<p class="card-text">
							<b>${libro.editore}</b>
						</p>
					</div>
					<c:if test="${utente != null}">
						<div class="card-footer">
							<button id="modifica" class="btn btn-lg btn-primary btn-block"
								type="submit">
								<a href="<c:url value="/libro/visualizza?idLibro=${libro.id}" />">Visualizza</a>
							</button>
						<c:if test="${libro.disponibile}">
							<c:if test="${utente.ruolo != 'amministratore'}">
								<button id="presta" onclick="Prestito.presta(${libro.id})" class="btn btn-lg btn-primary btn-block" type="submit">Prendi in prestito</button>
							</c:if>
							<c:if test="${utente.ruolo == 'amministratore'}">
								<button id="prestaButton" type="button" class="btn btn-primary" 
								data-toggle="modal" data-target="#prestaModalAskUser"
								onClick = "$('#idLibroModal').val(${libro.id})">
								Presta
								</button>
							</c:if>
						</c:if>
						<c:if test="${utente.ruolo == 'amministratore'}">
							<c:if test="${!libro.disponibile}">
								<button id="presta" onclick="Prestito.restituisci(${libro.id})" class="btn btn-lg btn-primary btn-block" >Restituisci</button>
							</c:if>
							<button id="modifica" class="btn btn-lg btn-primary btn-block"
								type="submit">
								<a href="<c:url value="/libro/modifica?idLibro=${libro.id}" />">Modifica</a>
							</button>
							<button id="cancella" onclick="Libro.cancella(${libro.id})" class="btn btn-lg btn-primary btn-block" >
								Cancella
							</button>
						</c:if>
						</div>
					</c:if>
				</div>
			</div>
		</div>
<c:if test="${loop.index%2!=0}">
	</div>
</c:if>
</c:forEach>

<!-- Modal -->
<div class="modal fade" id="prestaModalAskUser" tabindex="-1" role="dialog" aria-labelledby="modalLabelQuestion" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalLabelQuestion">Inserire l'utente a cui effettuare il prestito</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		<input id="idUtenteModal" type="number" required="required">
        <input id="idLibroModal" type="hidden">
     </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
        <button onclick="Prestito.prestaModal(this)" type="button" class="btn btn-primary">Presta</button>
      </div>
    </div>
  </div>
</div>
</div>
