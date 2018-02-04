const Prestito = {};
Prestito.idLibroRestituisci;
Prestito.idLibroPresta;

Prestito.confirmPresta = (idLibro)=>{
	Prestito.idLibroPresta = idLibro;
	
	const title = `Conferma richiesta prestito libro`;
	const body = `Vuoi prendere in prestito il libro con id ${idLibro}?`;
	const callback = Prestito.presta;
	
	askConfirmAction(title, body, callback);
}

Prestito.presta = () => {
  const idLibro = Prestito.idLibroPresta;
  console.log("richiesta prestito libro con id = " + idLibro);

  if (!idLibro) return;

  const xhttp = new XMLHttpRequest();
  xhttp.open("GET", contextPath + "/prestito/presta?idLibro=" + idLibro, true);
  xhttp.onload = () => handleResponsePrestito(xhttp);
  xhttp.onerror = () => handleError(xhttp);
  xhttp.send();
};

Prestito.prestaModal = () => {
  const idLibro = $("#idLibroModal").val();
  const idUtente = $("select#idUtenteModal option:checked").val();
  console.log(
    `richiesta prestito libro con id = ${idLibro} per l'utente ${idUtente}`
  );

  if (!Prestito.isValidIdUtente(idUtente)) {
    $(".alert").alert();
    $(".alert").removeClass("d-none");
    return;
  }

  // clean modal
  $("#idLibroModal").val("");
  $("#idUtenteModal").val("");
  $("#prestaModalAskUser").modal("hide");

  const url = `${contextPath}/prestito/presta?idLibro=${idLibro}&idUtente=${idUtente}`;
  const xhttp = new XMLHttpRequest();
  xhttp.open("GET", url, true);
  xhttp.onload = () => handleResponsePrestito(xhttp);
  xhttp.onerror = () => handleError(xhttp);
  xhttp.send();
};

Prestito.confirmRestituisci = (idLibro)=>{
	Prestito.idLibroRestituisci = idLibro;
	
	const title = `Conferma restituzione libro`;
	const body = `Vuoi resituire il libro con id ${idLibro}?`;
	const callback = Prestito.restituisci;
	
	askConfirmAction(title, body, callback);
}

Prestito.restituisci = () => {
  const idLibro = Prestito.idLibroRestituisci;
  console.log("richiesta prestito libro con id = " + idLibro);
  try{
	  if (!idLibro) return;

	  const xhttp = new XMLHttpRequest();
	  xhttp.onload = () => handleResponsePrestito(xhttp);
	  xhttp.onerror = () => handleError(xhttp);
	  xhttp.open("GET", `${contextPath}/prestito/restituisci?idLibro=${idLibro}`, true);
	  xhttp.send();
  }finally{
	  Prestito.idLibroRestituisci = undefined;
  }
};

Prestito.onChange = $("#idUtenteModal").change(() => {
  if (!$(".alert").hasClass("d-none")) {
    $(".alert").addClass("d-none");
  }
});

Prestito.isValidIdUtente = idUtente => {
  if (!idUtente || idUtente === "") {
    return false;
  }

  if (isNaN(idUtente)) {
    return false;
  }

  return true;
};

const handleResponsePrestito = xhttp => {
  const response = parseResponse(xhttp.responseText);
  if (response && response.done) {
	  showSnackBar(response.messaggio, () => window.location.reload(true));
	  return;
  }
  
  showSnackBar(response.messaggio);
};
