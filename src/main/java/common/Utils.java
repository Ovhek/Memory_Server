/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import main.Validadors;

/**
 *
 * @author alex
 */
public class Utils {

    /**
     * *
     * Valida regles de negoci anotades (veure anotacions al BEAN +
     * https://javaee.github.io/tutorial/bean-validation002.html) i controla
     * transacció
     *
     * @param ob
     * @param userTransaction
     * @param em
     * @param log
     * @return
     * @throws Exception
     */
    public static Object persisteixAmbTransaccio(Object ob, UserTransaction userTransaction, EntityManager em, Logger log) throws Exception {
        List<String> errors = Validadors.validaBean(ob);

        if (errors.isEmpty()) {
            try {

                userTransaction.begin();
                em.persist(ob);
                userTransaction.commit();

            } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                String msg = "Error desant: " + errors.toString();
                log.log(Level.INFO, msg);
                throw new Exception(msg);
            }

        } else {
            String msg = "Errors de validació: " + errors.toString();
            log.log(Level.INFO, msg);
            throw new Exception(msg);
        }

        return ob;
    }
}
