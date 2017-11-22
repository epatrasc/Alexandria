<nav aria-label="breadcrumb" role="navigation">
    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a href="#">Home</a>
        </li>
        <li class="breadcrumb-item">
            <a href="#">Libri</a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">Crea</li>
    </ol>
</nav>
<div>
    <h4>Aggiungi Libro</h4>

    <hr class="my-4">

    <form action="#" method="POST">
        <div class="form-group">
            <label for="titolo">Titolo</label>
            <input type="text" class="form-control" id="titolo" placeholder="Inserire il titolo" required autofocus>
        </div>
        <div class="form-group">
            <label for="autori">Autori</label>
            <input type="text" class="form-control" id="autori" placeholder="Inserisci gli autori">
        </div>
        <div class="form-group">
            <label for="descrizione">Descrizione</label>
            <input type="text" class="form-control" id="descrizione" placeholder="Inserire una descrizione">
        </div>
        <div class="form-group">
            <label for="editore">Editore</label>
            <input type="text" class="form-control" id="editore" placeholder="Inserire un'editore">
        </div>
        <div class="form-group">
            <label for="imageUrl">Immagine</label>
            <input type="file" class="form-control-file" id="imageUrl">
        </div>
        <button type="submit" class="btn btn-primary">Crea Libro</button>
    </form>
</div>