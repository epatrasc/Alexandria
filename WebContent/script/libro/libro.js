const xhttp = new XMLHttpRequest();

const Libro = {};
Libro.aggiungi = () => {
  console.log("richiesta aggiunta a catalogo di un nuovo libro");

  const url = "/libro/insert";
  const params = Libro.getParams();

  
  xhttp.open("POST", contextPath + url, true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.onload = handleResponseLibro;
  xhttp.onerror = handleError;
  xhttp.send(params);
};

Libro.getParams = () => {
  var params = "";
  params += `titolo=${$("#titolo").val()}`;
  params += `&autori=${$("#autori").val()}`;
  params += `&editore=${$("#editore").val()}`;
  params += `&url=${$("#url").val()}`;
  params += `&descrizione=${$("#descrizione").val()}`;

  return params;
};

$("#aggiungiLibro").submit(function(e) {
  e.preventDefault();
  Libro.aggiungi();
  return false;
});

const handleResponseLibro = () => {
    const response = parseResponse(xhttp.responseText);

    if (response && response.done) {
      alert(response.messaggio);
      window.location.href = '../catalogo/visualizza';
    }
};
