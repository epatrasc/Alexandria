const Libro = {};
Libro.urlRedirect = '../libro/visualizza?idLibro=';

Libro.aggiungi = () => {
  console.log("richiesta aggiunta a catalogo di un nuovo libro");

  const url = "/libro/insert";
  const params = Libro.getParams();

  const xhttp = new XMLHttpRequest();
  xhttp.open("POST", encodeURI(contextPath + url), true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.onload = ()=> handleResponseLibro(xhttp);
  xhttp.onerror = ()=> handleError(xhttp);
  xhttp.send(params);
};

Libro.modifica = () => {
  console.log("richiesta di modifica di un libro");

  const url = "/libro/update";
  const params = Libro.getParams();

  const xhttp = new XMLHttpRequest();
  xhttp.open("POST", encodeURI(contextPath + url), true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.onload = ()=> handleResponseLibro(xhttp);
  xhttp.onerror = ()=> handleError(xhttp);
  xhttp.send(params);
};

Libro.cancella = (idLibro) => {
  console.log("richiesta di cancellazione libro con id "+ idLibro);
  
  if (!idLibro) return;
  
  const url = `/libro/cancella?idLibro=${idLibro}`;

  const xhttp = new XMLHttpRequest();
  xhttp.open("GET", encodeURI(contextPath + url), true);
  xhttp.onload = ()=> handleResponseLibroDelete(xhttp);
  xhttp.onerror = ()=> handleError(xhttp);
  xhttp.send();
};
	
Libro.getParams = () => {
  var params = "";
  params += `titolo=${encodeURIComponent($("#titolo").val())}`;
  params += `&autori=${encodeURIComponent($("#autori").val())}`;
  params += `&editore=${encodeURIComponent($("#editore").val())}`;
  params += `&url=${encodeURIComponent($("#url").val())}`;
  params += `&descrizione=${encodeURIComponent($("#descrizione").val())}`;
  params += $("#idLibro").val() ? `&idLibro=${encodeURIComponent($("#idLibro").val())}`:'';
  return params;
};

$("#aggiungiLibro").submit(function(e) {
  e.preventDefault();
  Libro.aggiungi();
  return false;
});

$("#modificaLibro").submit(function(e) {
  e.preventDefault();
  Libro.modifica();
  return false;
});

const handleResponseLibro = (xhttp) => {
    const response = parseResponse(xhttp.responseText);
    
    if (response && response.done) {
    	const idLibro = response.param;
    	showSnackBar(response.messaggio, () => {window.location.href = Libro.urlRedirect + idLibro});
    }
};

const handleResponseLibroDelete = (xhttp) => {
    const response = parseResponse(xhttp.responseText);
    
    if(response && response.done){
    	showSnackBar(response.messaggio, () => location.reload());
    	return;
    }
    
    showSnackBar(response.messaggio);
};
