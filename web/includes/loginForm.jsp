<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form class="form-signin" action="<c:url value="/login" />" method="post">
    <h2 class="form-signin-heading">Login</h2>
    <div class="form-group">
        <label for="nome" class="sr-only">Nome Utente</label>
        <input type="text" id="nome" name="nome" class="form-control" placeholder="Utente" required autofocus>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
    </div>
    <div class="form-group">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Invia</button>
    </div>
   	<c:if test="${logout == true }">
	    <div class="form-group">
	    <div class="alert alert-success" role="alert">Logout avvenuto con successo!</div>
	    </div>
    </c:if>
    <c:if test="${loginError == true }">
	    <div class="form-group">
	    <div class="alert alert-danger" role="alert">${messaggio}</div>
	    </div>
    </c:if>
</form>