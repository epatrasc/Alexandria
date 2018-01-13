const Prestito = {};
Prestito.presta = idLibro => {
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

Prestito.restituisci = idLibro => {
  console.log("richiesta prestito libro con id = " + idLibro);

  if (!idLibro) return;

  const xhttp = new XMLHttpRequest();
  xhttp.onload = () => handleResponsePrestito(xhttp);
  xhttp.onerror = () => handleError(xhttp);
  xhttp.open(
    "GET",
    contextPath + "/prestito/restituisci?idLibro=" + idLibro,
    true
  );
  xhttp.send();
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
  if (response) {
    alert(response.messaggio);
  }

  if (response && response.done) {
    window.location.reload(true);
  }
};
