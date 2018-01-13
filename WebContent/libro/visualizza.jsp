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
					<button type="button" onclick="window.location.href='<c:url value="/catalogo/visualizza"/>'"class="btn btn-outline-primary">Torna al catalogo</button>
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
