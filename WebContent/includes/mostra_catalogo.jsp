<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="card-deck">
<c:forEach var="libro" items="${requestScope.libri}" varStatus="loop">
	<c:if test="${loop.index%2==0}"><div class="row"></c:if>
	<div class="cols">
	<div class="card">
	    <div class="card-block" >
	        <img class="card-img-top" src="${libro.imageUrl}" alt="${libro.titolo}">
	        <div class="card-body">
	            <h4 class="card-title">${libro.titolo}</h4>
	            <p class="card-text">${libro.descrizione}</p>
	            <p class="card-text">${libro.autori}</p>
	            <p class="card-text"><b>${libro.editore}</b></p>
	        </div>
	        <c:if test="${utente != null}">
		        <div class="card-footer">
					<button id="presta" onclick="Prestito.presta(${libro.id})" class="btn btn-lg btn-primary btn-block" type="submit">Prendi in prestito</button>
					<button id="modifica" class="btn btn-lg btn-primary btn-block" type="submit"><a href="/libro/modifica/${libro.id}"></a></button>
		        </div>
	        </c:if>
	    </div>
	</div>
	</div>
	<c:if test="${loop.index%2!=0}"></div></c:if>
</c:forEach>
</div>

