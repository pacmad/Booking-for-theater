package com.Teatr;

public class Spektakl {
    private Miejsce siedzenia[][];//punkt 15 tablice, tablica dwywmiarowa do miejsc na spektakl
    private Miejsce siedzeniaVIP[];
    private Teatr teatr;//2 typ referencyjny na teatr
    private int LiczbaWolnychMiejsc;//liczba wolnych miejsc
    private int LiczbaWolnychMiejscVIP;
    private String Nazwa; //punkt 8 czyli hermetyzacja/ukrywanie implementacji przez private
    private int N;
    private int M;//ilosc rzedow i miejsc w rzedach zwyklych
    private int V;//ilosc miejsc vip
    Spektakl(int n,int m,int v,String nazwa,Teatr t)//kontruktor przyjmujacy liczbe rzedow, miejsc w rzedzie,liczbe miejsc vip,nazwe i referencje na teatr
    {
        this.N=n;
        this.M=m;
        this.V=v;//przypisujemy calkowita liczbe miejsc
        this.siedzenia=new Miejsce[n][m]; //punkt 7 i 15
        this.siedzeniaVIP=new Miejsce[v]; //punkt 10 miejsca są w kompozycji ze Spektakle, są jej składową oraz nie mogą występować bez niej(mają na nią referencje), tak samo jest z SPektakl <-> com.Teatr.Teatr
        this.teatr=t;//punkt 2
        this.Nazwa=nazwa;
        this.LiczbaWolnychMiejsc=n*m;
        this.LiczbaWolnychMiejscVIP=v;
        for(int i=0;i<n;i++)//wypelniamy tablice miejscami
        {
            for(int j=0;j<m;j++)
            {
                this.siedzenia[i][j]=new MiejscePodstawowe(i,j,this);
            }
        }
        for(int i=0;i<v;i++)//wypelniamy tablice miejscami vip
        {
            this.siedzeniaVIP[i]=new MiejsceVIP(i,this);
        }
    }
    public String getNazwa()
    {
        return this.Nazwa;
    }
    public int getLiczbaWolnychMiejsc()//getter wolnych miejsc
    {
        return this.LiczbaWolnychMiejsc;
    }
    public int getLiczbaWolnychMiejscVIP()//getter wolnych miejsc
    {
        return this.LiczbaWolnychMiejscVIP;
    }
    public Miejsce getMiejsce (int x,int y) throws ArrayIndexOutOfBoundsException//getter danego miejsca
    {
        if(x>=this.N || y>=this.M)
            throw new ArrayIndexOutOfBoundsException();//punkt 14 obsluga wyjscia za tablice
        return this.siedzenia[x][y];
    }
    public Miejsce getMiejsce(int x) throws ArrayIndexOutOfBoundsException//getter danego miejsca vip,przeciazenie metody
    {
        if(x>=this.V)
            throw new ArrayIndexOutOfBoundsException();//punkt 14 obsluga wyjscia za tablice
        return this.siedzeniaVIP[x];
    }

    public int getN()
    {
        return this.N;
    }
    public int getM()
    {
        return this.M;
    }
    public int getV()
    {
        return this.V;
    }//gettery do n,m i v
    public void zmniejszLiczbeMiejscWolnych()
    {
        this.LiczbaWolnychMiejsc--;
    }
    public void zmniejszLiczbeMiejscWolnychVIP()
    {
        this.LiczbaWolnychMiejscVIP--;
    }
    public void zwiekszLiczbeMiejscWolnych()
    {
        this.LiczbaWolnychMiejsc++;
    }
    public void zwiekszLiczbeMiejscWolnychVIP()
    {
        this.LiczbaWolnychMiejscVIP++;
    }
}
