<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="card-deck">
<c:forEach var="libro" items="${requestScope.libri}" varStatus="loop">
	<div class="card" style="width: 20rem;">
	    <div class="card-block" >
	        <img class="card-img-top" src="${libro.imageUrl}" alt="${libro.titolo}">
	        <div class="card-body">
	            <h4 class="card-title">${libro.titolo}</h4>
	            <p class="card-text">${libro.descrizione}</p>
	            <p class="card-text"><b>${libro.editore}</b></p>
	        </div>
	    </div>
	</div>
</c:forEach>
</div>

