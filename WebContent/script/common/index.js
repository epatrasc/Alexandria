const parseResponse = responseText => {
  return responseText.indexOf("{") === 0
    ? JSON.parse(responseText)
    : false;
};

const handleError = ()=>{
	console.log(xhttp && xhttp.statusText);
    alert("Oops c'e'stato un errore lato server");
}