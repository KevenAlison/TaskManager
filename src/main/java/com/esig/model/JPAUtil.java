package com.esig.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory factory;
    
    public static EntityManagerFactory getEntityManagerFactory() {
    	if(factory == null) {
    	    factory = Persistence.createEntityManagerFactory("taskManagerPersistence");
    	}
        return factory;
    }
    
    public static void closeEntityManager(){
    	if(factory!=null && factory.isOpen()) {
    		factory.close();
    	}
    }
}