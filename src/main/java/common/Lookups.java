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
import main.JuegoEJB;

/**
 * Classe encarregada de fer les connexions amb els EJB remots
 * @author alexandru
 */
public class Lookups {
    
    private static final String APP_VERSION = "1.0.0";
    
    private static final String wildFlyInitialContextFactory = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    
    private static final String appName = "Server_Memory-" + APP_VERSION;
    
    public static IJuego jugadorEJBRemoteLookup() throws NamingException
    {
        // "/EJB_Exemple1_Server-1/CarroCompraEJB!common.ICarroCompra?stateful"
        
        String strlookup = "ejb:/" + appName + "/" + JuegoEJB.class.getSimpleName() + "!" + IJuego.class.getName()+"?stateful";
            
        Properties jndiProperties = new Properties();

        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  wildFlyInitialContextFactory);
        
        Context context = new InitialContext(jndiProperties);

        return (IJuego) context.lookup(strlookup);
    }
    
}
