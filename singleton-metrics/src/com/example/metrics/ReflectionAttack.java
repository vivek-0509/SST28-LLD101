package com.example.metrics;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionAttack {

    public static void main(String[] args) throws Exception {
        MetricsRegistry singleton = MetricsRegistry.getInstance();
        System.out.println("Singleton identity: " + System.identityHashCode(singleton));

        Constructor<MetricsRegistry> ctor = MetricsRegistry.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        try {
            MetricsRegistry evil = ctor.newInstance();
            System.out.println("Reflection attack succeeded (should not happen)");
            System.out.println("Evil identity: " + System.identityHashCode(evil));
        } catch (InvocationTargetException e) {
            System.out.println("Reflection attack blocked: " + e.getCause().getMessage());
        }
    }
}
