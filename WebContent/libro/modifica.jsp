<form id="modificaLibro" method="post">
    <label for="titolo" class="sr-only">Titolo</label>
    <input id="titolo" type="text" name="titolo" value="${libro.titolo}" size="45" required >
    
    <label for="autori" class="sr-only">Autori</label>
    <input id="autori" type="text" name="autori" value="${libro.autori}" required >
    
    <label for="editore" class="sr-only">Editore</label>
    <input id="editore" type="text" name="editore" value="${libro.editore}" required >
    
    <label for="url" class="sr-only">Copertina</label>
    <input id="url" type="text" name="url" value="${libro.imageUrl}" required >
    
    <label for="descrizione" class="sr-only">Descrizione</label>
    <textarea id="descrizione" name="descrizione">
    	${libro.descrizione}
    </textarea>
    <input id="idLibro" type="hidden" name="idLibro" value="${libro.id}">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Modifica</button>
</form>