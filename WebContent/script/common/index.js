const idConfirmDialog = "modal_confirm";

const parseResponse = responseText => {
  return responseText.indexOf("{") === 0
    ? JSON.parse(responseText)
    : false;
};

const handleError = (xhttp)=>{
	console.log(xhttp && xhttp.statusText);
    alert("Oops c'e'stato un errore lato server");
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
	bntConferma.click(callback);
	
	dialog.modal('show')
	
}