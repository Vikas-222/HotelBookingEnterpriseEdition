package com.example.common.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ManagerFactory {

    private static EntityManagerFactory emf;

    private ManagerFactory(){}

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("HotelPersistenceUnit");
        }
        return emf;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

}
