<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <nav>
        <ul class="nav nav-pills float-right">
            <li class="nav-item">
                <a class="nav-link <c:if test="${menu_active == 'home'}">active</c:if>" href="<c:url value="/home" />">
                <span><img alt="home" src="<c:url value="/images/icon/ic_home_white_48dp_1x.png"/>" height="24px" width="24px" class="d-inline-block align-top"/></span>
                Home
                </a>
            </li>
            <c:if test="${utente == null}">
            <li class="nav-item">
				<a class="nav-link <c:if test="${menu_active == 'login'}">active</c:if>" href="<c:url value="/login" />">Login </a>
            </li>
            </c:if>
            <c:if test="${utente != null}">
            	<li class="nav-item">
                    <a class="nav-link <c:if test="${menu_active == 'prestiti'}">active</c:if>" href="<c:url value="/prestiti" />" role="button">Prestiti</a>
                </li>
            </c:if>
            <c:if test="${utente != null}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle <c:if test="${menu_active == 'catalogo'}">active</c:if>" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Catalogo</a>
                    <div class="dropdown-menu">
                    	<c:if test="${utente != null}">
                            <a class="dropdown-item" href="<c:url value="/catalogo/visualizza" />">Visualizza</a>
                        </c:if>
<%--                         <c:if test="${utente != null && utente.ruolo == 'amministratore'}"> --%>
<%--                             <a class="dropdown-item" href="<c:url value="/catalogo/modifica" />">Modifica</a> --%>
<%--                         </c:if> --%>
                        <c:if test="${utente != null && utente.ruolo == 'amministratore'}">
                            <a class="dropdown-item" href="<c:url value="/catalogo/aggiungi" />">Aggiungi</a>
                        </c:if>
                    </div>
                </li>
            </c:if>
        </ul>
    </nav>