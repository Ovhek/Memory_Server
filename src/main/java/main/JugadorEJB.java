package main;

import common.IJugador;
import common.Jugador;
import common.JugadorException;
import common.Partida;
import common.PartidaException;
import common.Utils;
import java.security.Principal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 * *
 * Classe Stateful, que manté l'estat de les dades entre diverses crides als
 * seus mètodes.
 *
 * @author manel
 */
@Stateful
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER) //Simply put, in container-managed concurrency, the container controls how clients' access to methods
@TransactionManagement(value = TransactionManagementType.BEAN) // o bé és el contenidor el que gestiona les transaccions a la BBDD o bé és el programador/a manualment
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
    @PersistenceContext(unitName = "Exemple1PersistenceUnit", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    String emailUsuario = null;

    // Injecció d'un EJB local. En aquest cas no cal fer lookup.
    @EJB
    AppSingletonEJB singleton;

    Queue<String> mensajes;

    private static final Logger log = Logger.getLogger(JugadorEJB.class.getName());

    @PostConstruct
    public void init() {

        log.log(Level.INFO, "Inicializando JugadorEJB: client={0} ; emailUsuario={1}; singletonEJB uptime={2}", new Object[]{sessionContext.getCallerPrincipal().getName(), this.emailUsuario, this.singleton.getUptimeUTC().toString()});

        try {

            mensajes = new LinkedList<>();

            Principal p = ejbContext.getCallerPrincipal();

            log.log(Level.INFO, "Usuario remoto: {0}", p.getName());
            log.log(Level.INFO, "Hash: {0}", p.hashCode());

        } catch (Exception ex) {
            log.log(Level.INFO, "ERROR conectando:  {0} ", new Object[]{ex.toString()});
        }
    }

    @Override
    public Jugador getSesion(Jugador j) throws JugadorException {
        if ((j.getEmail() == null || j.getEmail().isBlank() || j.getEmail().isEmpty())
                || (j.getNombre() == null || j.getNombre().isBlank() || j.getNombre().isEmpty())) {
            String msg = "El formato del nombre o email no es válido";
            log.log(Level.WARNING, msg);
            throw new JugadorException(msg);
        }

        String consulta = "SELECT j FROM Jugador j WHERE j.email = :email";
        TypedQuery<Jugador> query = em.createQuery(consulta, Jugador.class);
        Jugador jugador = query.setParameter("email", j.getEmail()).getSingleResult();

        if (jugador == null) {
            String msg = "El jugador no existe.";
            log.log(Level.WARNING, msg);
            throw new JugadorException(msg);
        }

        log.log(Level.INFO, "Jugador obtenido: " + jugador.getEmail());
        return jugador;
    }

    @Remove
    @Override
    public void cerrarSesion() {
        log.log(Level.INFO, "Sesión finalizada: " + this.emailUsuario);
    }

    @Override
    public void unirseAPartida(Partida partida) throws PartidaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Jugador registrarUsuario(Jugador jugador) throws Exception {
        Jugador j = null;
        try {
            String consulta = "SELECT j FROM Jugador j WHERE j.email = :email";
            TypedQuery<Jugador> query = em.createQuery(consulta, Jugador.class);
            j = query.setParameter("email", jugador.getEmail()).getSingleResult();
        } catch (Exception ex) {

        }

        if (j != null) {
            String msg = "El usuario ya existe";
            log.log(Level.WARNING, msg);
            throw new JugadorException(msg);
        }

        j = (Jugador) Utils.persisteixAmbTransaccio(jugador, userTransaction, em, log);

        return j;
    }

    @Override
    public void salirDePartida(Partida partida) throws PartidaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
