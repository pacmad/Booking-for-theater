package com.Teatr;

public class MiejsceVIP extends Miejsce{//dziedziczenie po klasie miejsce punkt 9
    MiejsceVIP(int x,Spektakl s)
    {
        super();//wywolujemy kontstukor klasy miejsce
        this.x=x;
        this.spektakl=s;//wpisujemy dane
    }
    @Override
    public void zwolnij()
    {
        super.zwolnij();
        this.spektakl.zwiekszLiczbeMiejscWolnychVIP();
    }//nadpisanie metod w celu zwiekszania i zmneijszania odpowidniej ilosci wolnych miejsc

    @Override
    public void rezerwuj(String dane) {
        super.rezerwuj(dane);
        this.spektakl.zmniejszLiczbeMiejscWolnychVIP();
    }
}
