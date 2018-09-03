package com.Teatr;

public class MiejscePodstawowe extends Miejsce {//dziedziczenie po klasie miejsce punkt 9
    private int y; //miejsce zwykle zawiera jeszcze rzedy
    MiejscePodstawowe(int x,int y,Spektakl s)
    {
        super();//wywolujemy kontstukor klasy miejsce
        this.x=x;
        this.y=y;
        this.spektakl=s;//przepisujemy dane
    }
    @Override
    public int gety()
    {
        return this.y;
    }
    @Override
    public void zwolnij()
    {
        super.zwolnij();
        this.spektakl.zwiekszLiczbeMiejscWolnych();
    }

    @Override
    public void rezerwuj(String dane) {
        super.rezerwuj(dane);
        this.spektakl.zmniejszLiczbeMiejscWolnych();
    }//nadpisanie metod w celu zwiekszania i zmneijszania odpowidniej ilosci wolnych miejsc
}
