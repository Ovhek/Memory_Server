package main;

import common.Carta;
import common.IJugador;
import common.IPartida;
import common.MazoDeCartas;
import common.Partida;
import common.PartidaException;
import java.util.logging.Logger;
import javafx.scene.image.Image;
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
public class PartidaEJB implements IPartida {
    
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
   
   IJugador jugador;
   
   private static final Logger log = Logger.getLogger(PartidaEJB.class.getName());

    @Override
    public void empezarPartida(Partida partida) throws PartidaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void terminarPartida(Partida partida) throws PartidaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MazoDeCartas mezclarCartas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cartasConciden(Carta primera, Carta segunda) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int sumarIntentos(int anteriorNumero) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Image voltearCarta(Carta carta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   

}