const xhttp = new XMLHttpRequest();

const Prestito = {};
Prestito.presta = idLibro => {
  console.log("richiesta prestito libro con id = " + idLibro);

  if (!idLibro) return;

  xhttp.open("GET", contextPath + "/prestito/presta?idLibro=" + idLibro, true);
  xhttp.onload = handleResponsePrestito;
  xhttp.onerror = handleError;
  xhttp.send();
};

Prestito.restituisci = idLibro => {
  console.log("richiesta prestito libro con id = " + idLibro);

  if (!idLibro) return;

  xhttp.onload = handleResponsePrestito;
  xhttp.onerror = handleError;
  xhttp.open(
    "GET",
    contextPath + "/prestito/restituisci?idLibro=" + idLibro,
    true
  );
  xhttp.send();
};

const handleResponsePrestito = () => {
    const response = parseResponse(xhttp.responseText);
    if(response){
    	alert(response.messaggio);
    }
    
    if (response && response.done) {
      window.location.reload(true);
    }
};
