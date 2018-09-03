package com.Teatr;

public class RezerwacjaException extends Exception {//punkt 14 wlasny wyjatek
    public RezerwacjaException()
    {
        System.out.println("Brak miejsc!");//konstruktor wyjatku
    }
}
