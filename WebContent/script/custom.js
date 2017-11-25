var Prestito = {};

Prestito.presta = function() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log(this.responseText);
			var response = this.responseText.indexOf("{")===0 ? JSON.parse(this.responseText) : false;
			
			if(response ){
				alert(response.messaggio)
			}
			
		}
	};
	xhttp.open("GET", contextPath + "/prestiti/presta?idUtente=1&idLibro=1", true);
	xhttp.send();
}
