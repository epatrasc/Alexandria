<form id="aggiungiLibro" method="post">
    <div class="form-group">
        <label for="titolo">Titolo</label>
        <input id="titolo" class="form-control" type="text" name="titolo" size="45" required />
    </div>

    <div class="form-group">
        <label for="autori">Autori</label>
        <input id="autori" class="form-control" type="text" name="autori" required />
    </div>

    <div class="form-group">
        <label for="editore">Editore</label>
        <input id="editore" class="form-control" type="text" name="editore" required/>
    </div>

    <div class="form-group">
    <div class="row">
        <div class="col-4">
            <img src="http://via.placeholder.com/200x350" />
        </div>
        <div class="col col-md-8">
            <div class="row">
                <label for="url">Copertina</label>
                <input id="url" name="url" class="form-control" type="text" required>

            </div>
            <div class="row">
                <label for="descrizione">Descrizione</label>
                <textarea id="descrizione" class="form-control" name="descrizione" maxlength="500"></textarea>
            </div>
        </div>
        </div>
    </div>

    <button class="btn btn-primary btn-block" type="submit">Aggiungi</button>
</form>