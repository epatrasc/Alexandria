const Prestito = {};

Prestito.presta = idLibro => {
  console.log("richiesta prestito libro con id = " + idLibro);

  if (!idLibro) return;

  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = printResponse;
  xhttp.open("GET", contextPath + "/prestito/presta?idLibro=" + idLibro, true);
  xhttp.send();
};

Prestito.restituisci = idLibro => {
  console.log("richiesta prestito libro con id = " + idLibro);

  if (!idLibro) return;

  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = Libro.handleResponse;
  xhttp.open(
    "GET",
    contextPath + "/prestito/restituisci?idLibro=" + idLibro,
    true
  );
  xhttp.send();
};

Prestito.handleResponse = () => {
  console.log("%j", this);
  // 200 ok
  if (document.readyState == XMLHttpRequest.DONE && document.status == 200) {
    console.log(this.responseText);
    const response = parseResponse(this.responseText);

    if (response) {
      alert(response.messaggio);
    }
    
    if(response.done){
    	window.location.href = "../catalogo/visualizza";
    }
  }

  // 500 errore server
  if (document.readyState == 2 && document.status == 500) {
    console.log(this.responseText);
    alert("Oops c'e'stato un errore lato server");
  }
};

const xhttpError = (statusText)=>{
	
}
