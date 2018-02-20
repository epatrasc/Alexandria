<form id="modificaLibro" method="post" class="form-group validazione">
	<div class="form-group">
		<label for="titolo">Titolo</label> <input id="titolo" name="titolo"
			class="form-control" value="${libro.titolo}" type="text"
			aria-describedby="Titolo" placeholder="Inserire il titolo" size="45"
			required autofocus>
	</div>

	<div class="form-group">
		<label for="autori">Autori</label> <input id="autori" name="autori"
			class="form-control" type="text" value="${libro.autori}" required>
	</div>

	<div class="form-group">
		<label for="editore">Editore</label> <input id="editore"
			name="editore" class="form-control" type="text"
			value="${libro.editore}" required>
	</div>

	<div class="form-group">
		<div class="row form-group">
		<div class="col-4 form-group">
				<img id="imgBook" src="${libro.imageUrl}" class="copertina" />
			</div>
			<div class="form-group col-8">
				<div class="row form-group">
				<label for="url">Copertina</label> 
				<input id="url" name="url" class="form-control" type="text" value="${libro.imageUrl}"  onblur="validateURL()" required placeholder="Url">
				 <div class="invalid-feedback">Immettere un url valido</div>
				</div>
				<div class="row form-group">
					<label for="descrizione">Descrizione</label>
					<textarea id="descrizione" name="descrizione" rows="8" class="form-control">${libro.descrizione}</textarea>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<button class="btn btn-primary btn-block" type="submit">Modifica</button>
	</div>
	<input id="idLibro" type="hidden" name="idLibro" value="${libro.id}">
	<input id="titoloLibro" type="hidden" name="titoloLibro" value="${libro.titolo}">
	<input id="titoloLibro" type="hidden" name="titoloLibro" value="${libro.titolo}">
</form>