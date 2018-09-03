package com.Teatr;

public abstract class Miejsce implements ObslugaMiejsc{ //klasa abstrakcyjna implementujaca interfejs, punkt 13
protected int x;
protected boolean CzyZajete;//punkt 1, zmienna logiczna okreslajaca czy miejsce jest zajete
protected Spektakl spektakl;
protected String Dane;//podstawowe zmienne zawierajace dane miejsca
    public Miejsce()
    {
        this.CzyZajete=false;
        this.Dane="";//konstruktor domyslny klasy abstrakcyjnej
    }
    public void rezerwuj(String dane)
    {
        this.Dane=dane;
        this.CzyZajete=true;
    }
    public void zwolnij()
    {
        this.Dane="";
        this.CzyZajete=false;
    }
    public int getx()
    {
        return this.x;
    }
    public boolean getCzyZajete() {
        return this.CzyZajete;
    }
    public int gety()
    {
        return 0;
    }
    public String getDane()
    {
        return this.Dane;
    }
}
