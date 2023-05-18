/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import main.HistorialPartidaEJB;
import main.JugadorEJB;
import main.PartidaEJB;

/**
 * Classe encarregada de fer les connexions amb els EJB remots
 * @author manel
 */
public class Lookups {
    
    private static final String APP_VERSION = "1.0.0";
    
    private static final String wildFlyInitialContextFactory = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    
    private static final String appName = "Server_Memory-" + APP_VERSION;
    
    public static IJugador jugadorEJBRemoteLookup() throws NamingException
    {
        // "/EJB_Exemple1_Server-1/CarroCompraEJB!common.ICarroCompra?stateful"
        
        String strlookup = "ejb:/" + appName + "/" + JugadorEJB.class.getSimpleName() + "!" + IJugador.class.getName()+"?stateful";
            
        Properties jndiProperties = new Properties();

        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  wildFlyInitialContextFactory);
        
        Context context = new InitialContext(jndiProperties);

        return (IJugador) context.lookup(strlookup);
    }
    
    public static IPartida partidaEJBRemoteLookup() throws NamingException
    {
        String strlookup = "ejb:/" + appName + "/" + PartidaEJB.class.getSimpleName() + "!" + IPartida.class.getName();
            
        Properties jndiProperties = new Properties();

        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  wildFlyInitialContextFactory);
        
        Context context = new InitialContext(jndiProperties);

        return (IPartida) context.lookup(strlookup);
    }
    
    public static IHistorialPartida historialPartidaEJBRemoteLookup() throws NamingException
    {
        String strlookup = "ejb:/" + appName + "/" + HistorialPartidaEJB.class.getSimpleName() + "!" + IHistorialPartida.class.getName();
            
        Properties jndiProperties = new Properties();

        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  wildFlyInitialContextFactory);
        
        Context context = new InitialContext(jndiProperties);

        return (IHistorialPartida) context.lookup(strlookup);
    }
}
