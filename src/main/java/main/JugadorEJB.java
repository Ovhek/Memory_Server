package main;

import common.IJugador;
import common.JugadorException;
import common.Partida;
import common.PartidaException;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/***
 * Classe Stateful, que manté l'estat de les dades entre diverses crides als seus mètodes.
 * @author manel
 */
@Stateful
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER) //Simply put, in container-managed concurrency, the container controls how clients' access to methods
@TransactionManagement(value=TransactionManagementType.CONTAINER) // o bé és el contenidor el que gestiona les transaccions a la BBDD o bé és el programador/a manualment
//@TransactionAttribute(TransactionAttributeType.REQUIRED) //https://gerardo.dev/ejb-basics.html
public class JugadorEJB implements IJugador {
    
   @Resource
   private SessionContext sessionContext;    
   
   // tenim accès a la funcionalitat "injectada" al BEAN
   @Resource
   private EJBContext ejbContext;
   
   // Si utilitzem el entitymanager al llarg de més d'un mètode, 
   // la transacció es pot extendre al llarg de tot el BEAN i passar de mètode a mètode
   // Aquest paràmetre és necessàri per a un TransactionManagementType = CONTAINER
   // Per defecte es fa commit de la transacció, si s'ha fet un persist, al finalitzar cada mètode.
   // Es fa rollback si abans de sortir del mètode, es produeix una excepció
   @PersistenceContext(unitName = "Exemple1PersistenceUnit",type=PersistenceContextType.EXTENDED)
   private EntityManager em;
   
   // Injecció d'un EJB local. En aquest cas no cal fer lookup.
   @EJB
   AppSingletonEJB singleton;
   
   private static final Logger log = Logger.getLogger(JugadorEJB.class.getName());

    @Override
    public int getSesion(String login) throws JugadorException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cerrarSesion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void unirseAPartida(Partida partida) throws PartidaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void salirDePartida() throws PartidaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}