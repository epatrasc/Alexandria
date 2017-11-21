<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="../includes/head.jsp"%>
<body>
	<div class="container">
		<%@include file="../includes/header.jsp" %>
		<main role="main" class="container">
			<div class="container">
			    <div class="row">
			        <div class="col-md-12">
			            <div class="error-template">
			                <h1>Oops!</h1>
			                <h2>500 - Internal server Error</h2>
			                <div class="error-details">
			                    Sembra che qualcuno abbia fatto pasticci con il codice.
			                </div>
			                <div>Codice errore: xxxxx</div>
			                <div>Messaggio: futuro messaggio di errore</div>
			                <div class="error-actions">
			                    <a href="<c:url value="/home" />" class="btn btn-primary btn-lg">
			                    <span class="glyphicon glyphicon-home"></span>Torna alla Home</a>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
		</main>
	</div>
</body>
</html>