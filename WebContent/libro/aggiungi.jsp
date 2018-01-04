<form id="aggiungiLibro" method="post">
    <label for="titolo" class="sr-only">Titolo</label>
    <input id="titolo" type="text" name="titolo" size="45" required />
    
    <label for="autori" class="sr-only">Autori</label>
    <input id="autori" type="text" name="autori" required />
    
    <label for="editore" class="sr-only">Editore</label>
    <input id="editore" type="text" name="editore" />
    
    <label for="url" class="sr-only">Copertina</label>
    <input id="url" type="text" name="url" required />
    
    <label for="descrizione" class="sr-only">Descrizione</label>
    <textarea id="descrizione" name="descrizione" maxlength="500"></textarea>
    <button class="" type="submit">Aggiungi</button>
</form>