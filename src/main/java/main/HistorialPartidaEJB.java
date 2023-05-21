package main;

import common.HistorialPartida;
import common.IHistorialPartida;
import common.Utils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
* Classe @Stateless que ens proporciona mètodes que realitzen diverses funcions
* Es comporta exactament igual que una classe amb mètodes estàtics.
* No permet mantenir les dades entre una crida i la següent.
*/
// https://docs.wildfly.org/26/Developer_Guide.html// https://docs.wildfly.org/26/Developer_Guide.html
@Stateless
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER) //Simply put, in container-managed concurrency, the container controls how clients' access to methods
@TransactionManagement(value=TransactionManagementType.BEAN)
public class HistorialPartidaEJB implements IHistorialPartida {
    
    @PersistenceContext(unitName = "Exemple1PersistenceUnit")
    private EntityManager em;
    
    @Inject
    private UserTransaction userTransaction;

    private static final Logger log = Logger.getLogger(HistorialPartidaEJB.class.getName());

    @Override
    public List<HistorialPartida> verHistorial(int idJugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardarHistorial(HistorialPartida historial) {
        try {
            Utils.persisteixAmbTransaccio(historial, userTransaction, em, log);
        } catch (Exception ex) {
            log.log(Level.INFO, ex.getMessage());
        }
    }
    
    
   
}