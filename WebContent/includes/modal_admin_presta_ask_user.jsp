<!-- Modal prestito utente for admin-->
<div class="modal fade" id="prestaModalAskUser" tabindex="-1" role="dialog" aria-labelledby="modalLabelQuestion" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabelQuestion">Scelta utente a cui effettuare il prestito</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="row">
                        <div class="form-group">
                            <label for="idUtenteModal">Utente</label>
                            <select class="custom-select" id="idUtenteModal">
                                <c:forEach var="utente" items="${requestScope.utenti}" varStatus="loop">
                                    <c:if test="${loop.index==0 }">
                                        <option selected>Scegli utente...</option>
                                    </c:if>
                                    <option value="${utente.id}">${utente.nome}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row-2">
                        <div id="idUtenteModalAlert" class="alert alert-danger alert-dismissible d-none" role="alert">
                            Scegliere un utente
                            <button type="button btn-sml" class="close" data-dismiss="alert" aria-label="Chiudi">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <input id="idLibroModal" type="hidden">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <button onclick="Prestito.prestaModal(this)" type="button" class="btn btn-outline-primary">Presta</button>
                </div>
            </div>
        </div>
    </div>
</div>