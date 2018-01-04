<label for="titolo" class="sr-only">Titolo</label>
<input id="titolo" type="text" name="titolo" value="${libro.titolo}"
	size="45" readonly />

<label for="autori" class="sr-only">Autori</label>
<input id="autori" type="text" name="autori" value="${libro.autori}"
	readonly />

<label for="editore" class="sr-only">Editore</label>
<input id="editore" type="text" name="editore" value="${libro.editore}"
	readonly />

<label for="url" class="sr-only">Copertina</label>
<input id="url" type="text" name="url" value="${libro.url}"
	readonly />

<label for="descrizione" class="sr-only">Descrizione</label>
<textarea id="descrizione" type="" name="descrizione" size="45" readonly>
    	${libro.descrizione}
</textarea>
