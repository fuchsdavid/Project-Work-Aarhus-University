package org.database.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DataBase {
    protected final EntityManagerFactory emf;
    protected final EntityManager em;

    public DataBase() {
        this.emf = Persistence.createEntityManagerFactory("default");
        this.em = this.emf.createEntityManager();
    }

    public void testConnection() {
        System.out.println("\n\n\n\n");
        try {
            this.em.getTransaction().begin();
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void stopConnection() {
        em.close();
        emf.close();
    }

}
