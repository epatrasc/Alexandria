const idConfirmDialog = "modal_confirm";

const parseResponse = responseText => {
  return responseText.indexOf("{") === 0
    ? JSON.parse(responseText)
    : false;
};

const handleError = (xhttp)=>{
	console.log(xhttp && xhttp.statusText);
	showSnackBar("Oops c'e'stato un errore lato server", ()=>{} ,'danger');
}


const clearConfirmModal= ()=>{
	const dialog = $(`#${idConfirmDialog}`);
	const dialogTitle = dialog.find(".modal-title");
	const dialogBody = dialog.find(".modal-body > p");
	const bntConferma = dialog.find(".btn-primary");
	
	dialogTitle.text("");
	dialogBody.text("");
	bntConferma.click(false);
}
const askConfirmAction = (title, message, callback)=>{
	const dialog = $(`#${idConfirmDialog}`);
	const dialogTitle = dialog.find(".modal-title");
	const dialogBody = dialog.find(".modal-body > p");
	const bntConferma = dialog.find(".btn-primary");
	
	dialogTitle.text(title);
	dialogBody.text(message);
	bntConferma.click(() =>{ dialog.modal('hide'); callback()});
	
	dialog.modal('show')
	
}

const showSnackBar = (text, callback=()=>{}, level='primary') => {
    const snackBar = document.getElementById("snackbar")
    snackBar.innerHTML = text;
    snackBar.className = snackBar.className + " show snackbar--"+ level;
    
    setTimeout(function(){ 
    	snackBar.className = snackBar.className.replace("show snackbar--"+ level, "");
    	callback();
    }, 3000);
}

const validateURL = event => {
  const url = $("#url").val();

  if (isValidUrl(url)) {
	  if ($("#url").hasClass("is-invalid")) $("#url").removeClass("is-invalid");
      $("#imgBook").attr("src", url);
      
      return;
  }
  
  
  // invalid url
  if (!$("#url").hasClass("is-invalid")) $("#url").addClass("is-invalid");
  
  if (event) {
	event.preventDefault();
  	event.stopPropagation();
  }
};

const isValidUrl = (str) => {
 var pattern = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
  
  if(!pattern.test(str)) return false;
  
  return true;
}

(function() {
  'use strict';
  window.addEventListener('load', function() {
    var forms = document.getElementsByClassName('validazione');
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
    	  validateURL(event);
      }, false);
    });
  }, false);
})();