var reload = false;
var Prestito = {};

Prestito.presta = (idLibro) => {
    console.log("richiesta prestito libro con id = " + idLibro);

    if (!idLibro) return;
    reload = false;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = printResponse;
    xhttp.open("GET", contextPath + "/prestito/presta?idLibro=" + idLibro, true);
    xhttp.send();
}

Prestito.restituisci = (idLibro) => {
    console.log("richiesta prestito libro con id = " + idLibro);

    if (!idLibro) return;
    reload = true;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = printResponse;
    xhttp.open("GET", contextPath + "/prestito/restituisci?idLibro=" + idLibro, true);
    xhttp.send();
}


function printResponse() {
    console.log("%j", this)
    // 200 ok
    if (this.readyState == 4 && this.status == 200) {
        console.log(this.responseText);
        var response = this.responseText.indexOf("{") === 0 ? JSON.parse(this.responseText) : false;

        if (response) {
            alert(response.messaggio)
            reload && location.reload();
        }
    }

    // 500 errore server
    if (this.readyState == 2 && this.status == 500) {
        console.log(this.responseText);
        alert("Oops c'e'stato un errore lato server");
    }
}