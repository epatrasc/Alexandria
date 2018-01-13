<form id="modificaLibro" method="post" class="form-group">
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
		<div class="row">
		<div class="col-4">
				<img src="${libro.imageUrl}" />
			</div>
			<div class="col-8">
				<label for="url">Copertina</label> <input id="url" name="url"
					class="form-control" type="text" value="${libro.imageUrl}" required>
			</div>
			
		</div>
	</div>

	<div class="form-group">
		<label for="descrizione">Descrizione</label>
		<textarea id="descrizione" name="descrizione" class="form-control">
    	${libro.descrizione}
    </textarea>
	</div>

	<input id="idLibro" type="hidden" name="idLibro" value="${libro.id}">
	<button class="btn btn-primary btn-block" type="submit">Modifica</button>
</form>