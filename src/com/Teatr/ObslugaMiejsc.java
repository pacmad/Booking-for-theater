package com.Teatr;

public interface ObslugaMiejsc { //interfejs dla miejsc punkt 13
    void rezerwuj(String dane);
    void zwolnij();//nasza klasa musi zawierac te metody
    boolean getCzyZajete();
    int getx();
    int gety();
}
