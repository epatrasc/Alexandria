<section>
	<div class="container ">
		<div class="row">
			<div class="col">
				<div></div>
				<div class="card" style="width: 20rem;">
					<img class="card-img-top" src="${libro.imageUrl}"
						alt="${libro.titolo}">
				</div>
			</div>
			<div class="col">
				<div class="row">
					<h2>${libro.titolo}</h2>
				</div>
				<div class="row">
					<h4>
						<i>${libro.autori}</i>
					</h4>
				</div>
				<div class="row">
					<i>Editore: <span>${libro.editore}</span></i>
				</div>
				<hr>
				<div class="row">
					<strong>Descrizione:</strong>
					<p>${libro.descrizione}</p>
				</div>
				<div class="row">
					<c:if test="${utente != null}">
					<div class="col-6">
						<button type="button" onclick="window.location.href='<c:url value="/catalogo/visualizza"/>'"class="btn btn-outline-primary pointer">Torna al catalogo</button>
					</div>
					<c:if test="${utente.ruolo == 'amministratore'}">
						<c:if test="${libro.disponibile}">
						<div class="col-2">
							<button id="prestaButton" type="button" class="btn btn-outline-primary mb-2" data-toggle="modal" data-target="#prestaModalAskUser"
                            	onClick="$('#idLibroModal').val(${libro.id})">Presta</button>
                        </div>
                        </if>
						</c:if>
					</c:if>
					
					<c:if test="${utente.ruolo != 'amministratore'}">
						<c:if test="${libro.disponibile}">
							<div class="col-2">
								<button id="presta" onclick="Prestito.confirmPresta(${libro.id})" class="btn  btn-outline-primary mb-2" type="submit">Prendi in prestito</button>
							</div>
						</c:if>
					</c:if>
					<c:if test="${!libro.disponibile}">
						<div class="col-2">
							<button id="presta" onclick="Prestito.confirmRestituisci(${libro.id})"
							class="btn  btn-outline-primary mb-2">Restituisci</button>
						</div>
					</c:if>
					</c:if>
					
					<c:if test="${utente == null}">
					<div class="col-6">
						<button type="button" onclick="window.location.href='<c:url value="/home"/>'"class="btn btn-outline-primary pointer">Torna alla home</button>
					</div>
					</c:if>
				</div>
			</div>
		</div>

	</div>
</section>
<input id="idLibro" type="hidden" name="idLibro" value="${libro.id}">

<style>
.card {
	/* Add shadows to create the "card" effect */
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	transition: 0.3s;
}

/* On mouse-over, add a deeper shadow */
.card:hover {
	box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}

/* Add some padding inside the card container */
.container {
	padding: 2px 16px;
}
</style>
