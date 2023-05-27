package main;

import common.CartaMemory;
import common.IJuego;
import common.Jugador;
import common.JugadorException;
import common.MazoDeCartas;
import common.Partida;
import common.PartidaException;
import common.Utils;
import java.io.IOException;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
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
public class JuegoEJB implements IJuego {

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

    private String emailUsuario = null;
    private Partida partidaActual = null;
    private CartaMemory carta1 = null;
    private CartaMemory carta2 = null;
    private boolean primerVolteo = true;
    private boolean victoria = false;
    private boolean derrota = false;
    private MazoDeCartas mazo;
    private Timer timer;

    // Injecció d'un EJB local. En aquest cas no cal fer lookup.
    @EJB
    AppSingletonEJB singleton;

    Queue<String> mensajes;

    private static final Logger log = Logger.getLogger(JuegoEJB.class.getName());

    @PostConstruct
    public void init() {

        log.log(Level.INFO, "Inicializando JugadorEJB: client={0} ; emailUsuario={1}; singletonEJB uptime={2}", new Object[]{sessionContext.getCallerPrincipal().getName(), this.emailUsuario, this.singleton.getUptimeUTC().toString()});

        try {

            mensajes = new LinkedList<>();

            Principal p = ejbContext.getCallerPrincipal();

            log.log(Level.INFO, "Usuario remoto: {0}", p.getName());
            log.log(Level.INFO, "Hash: {0}", p.hashCode());

        } catch (IllegalStateException ex) {
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

        emailUsuario = jugador.getEmail();
        log.log(Level.INFO, "Jugador obtenido: " + jugador.getEmail());
        return jugador;
    }

    @Remove
    @Override
    public void cerrarSesion() {
        log.log(Level.INFO, "Sesi�n finalizada: " + this.emailUsuario);
    }

    @Override
    public Jugador registrarUsuario(Jugador jugador) throws Exception {

        if ((jugador.getEmail() == null || jugador.getEmail().isBlank() || jugador.getEmail().isEmpty())
                || (jugador.getNombre() == null || jugador.getNombre().isBlank() || jugador.getNombre().isEmpty())) {
            String msg = "El formato del nombre o email no es v�lido";
            log.log(Level.WARNING, msg);
            throw new JugadorException(msg);
        }

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
    public Partida terminarPartida() throws Exception {

        if (partidaActual == null) {
            throw new PartidaException("Esta partida no es la misma que la actual");
        }

        partidaActual.setPuntos(calcularPuntos());
        Partida p = (Partida) Utils.actualizaAmbTransaccio(partidaActual, userTransaction, em, log);

        partidaActual = null;
        timer.cancel();
        return p;
    }

    @Override
    public Partida empezarPartida(Partida partida) throws PartidaException {
        Partida p = null;
        partidaActual = null;
        carta1 = null;
        carta2 = null;
        primerVolteo = true;
        victoria = false;
        derrota = false;

        switch (partida.getDificultad()) {
            case 0:
                partida.setTiempoRestante(300);
                break;
            case 2:
                partida.setTiempoRestante(200);
                break;
            case 3:
                partida.setTiempoRestante(100);
                break;
            default:
                throw new AssertionError();
        }
        try {
            p = (Partida) Utils.persisteixAmbTransaccio(partida, userTransaction, em, log);

        } catch (Exception ex) {
            String msg = "Error al empezar la partida: " + ex.getMessage();
            log.log(Level.WARNING, msg);
            throw new PartidaException(msg);
        }
        partidaActual = p;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    actualizarTiempoPartida();
                } catch (PartidaException ex) {
                    log.log(Level.WARNING, ex.getMessage());
                }
            }
        }, 1000, 1000);
        return p;
    }

    @Override
    public List<Partida> verHistorial(int idJugador) {
        TypedQuery<Partida> query = em.createQuery("SELECT p FROM Partida p WHERE p.jugador.id = :idjugador", Partida.class);
        query.setParameter("idjugador", idJugador);
        return query.getResultList();
    }

    @Override
    public MazoDeCartas obtenerMazoMezclado() {
        mazo = new MazoDeCartas();
        mazo.mezclar();
        return mazo;
    }

    @Override
    public boolean cartasConciden() {
        if (carta1 == null || carta2 == null) {
            return false;
        }
        boolean coinciden = carta1.isMismaCarta(carta2);
        if (coinciden) {
            carta1.setMatched(true);
            carta2.setMatched(true);
            carta1 = null;
            carta2 = null;
        }
        return coinciden;
    }

    @Override
    public int sumarIntentos() throws Exception {
        if (partidaActual == null) {
            throw new PartidaException("NO existe partida actual");
        }
        partidaActual.setNumIntentos(partidaActual.getNumIntentos());
        Utils.persisteixAmbTransaccio(partidaActual, userTransaction, em, log);

        return partidaActual.getNumIntentos();
    }

    @Override
    public byte[] voltearCarta(CartaMemory carta) {
        if (carta.isEmparejada()) {
            try {
                return carta.getImage();
            } catch (IOException ex) {
                ex.printStackTrace();
                log.log(Level.SEVERE, "error imagenes: " + ex.getMessage());
            }
        }
        carta.setGirada(!carta.isGirada());

        if (carta1 == null) {
            carta1 = carta;
        } else if (carta2 == null) {
            carta2 = carta;
        } else {
            if (primerVolteo) {
                carta1 = carta;
                carta2 = null;
            } else {
                carta2 = carta;
                carta1 = null;
            }
        }
        primerVolteo = !primerVolteo;
        try {
            return carta.isGirada() ? carta.getImage() : carta.getBackOfCardImage();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.log(Level.SEVERE, "error imagenes: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Partida> getHallOfGame() throws Exception {
        TypedQuery<Partida> query = em.createQuery("SELECT DISTINCT p.jugador.id FROM Partida p ORDER BY p.puntos DESC", Partida.class);
        return query.getResultList();
    }

    private int calcularPuntos() {
        int puntos = 0;
        int intentos = partidaActual.getNumIntentos();
        int segundos = partidaActual.getTiempoRestante();

        int maxTiempo = 300;
        switch (partidaActual.getDificultad()) {
            case 0:
                maxTiempo = 300;
                break;
            case 1:
                maxTiempo = 200;
                break;
            case 2:
                maxTiempo = 100;
                break;
            default:
                throw new AssertionError();
        }

        puntos = (80 - intentos) * (maxTiempo - segundos);

        if (derrota) {
            puntos = Math.max(puntos, 0);
        }

        return puntos;
    }

    private void actualizarTiempoPartida() throws PartidaException {
        if (partidaActual == null) {
            return;
        }
        int tiempoActual = partidaActual.getTiempoRestante();

        if (tiempoActual > 0) {
            partidaActual.setTiempoRestante(tiempoActual - 1);
        } else {
            derrota = true;
        }
        System.out.println("Tiempo actualizado: " + tiempoActual);
    }

    @Override
    public int obtenerTiempoPartida() {
        return partidaActual.getTiempoRestante();
    }

    @Override
    public boolean comprobarVictoria() {
        victoria = mazo.getCartas().stream().allMatch(carta -> carta.isEmparejada() == true);

        return victoria;
    }

    @Override
    public boolean comprobarDerrota() {
        return derrota;
    }
}
