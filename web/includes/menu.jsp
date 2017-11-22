<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
    <ul class="nav nav-pills float-right">
        <li class="nav-item">
            <a class="nav-link active" href="<c:url value="/home" />">Home<span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <c:if test="${utente == null}"><a class="nav-link" href="<c:url value="/login" />">Login</a></c:if>
        </li>
        <c:if test="${utente != null}">
            <li class="nav-item dropdown"><a class="nav-link dropdown-toggle"
                                             data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                                             aria-expanded="false">Catalogo</a>
                <div class="dropdown-menu">
                    <c:if test="${utente != null && utente.ruolo == 'amministratore'}">
                        <a class="dropdown-item" href="<c:url value="/libri/modifica" />">Modifica</a>
                    </c:if>
                    <c:if test="${utente != null && utente.ruolo == 'amministratore'}">
                        <a class="dropdown-item" href="<c:url value="/libri/aggiungi" />">Aggiungi</a>
                    </c:if>
                </div>
            </li>
        </c:if>
        <c:if test="${utente != null}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle"
                   data-toggle="dropdown" href="<c:url value="/prestito" />" role="button" aria-haspopup="true"
                   aria-expanded="false">Libri</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="<c:url value="/presta" />">Presta</a>
                    <a class="dropdown-item" href="<c:url value="/restituisci" />">Restituisci</a>
                </div>
            </li>
        </c:if>
    </ul>
</nav>