package com.Teatr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

public class Teatr { //punkt 4
    private List<Spektakl>Spektakle;//punkt 15 kolekcja, lista spektakli w teatrze
    public final ImageIcon WolneMiejsce = new ImageIcon("src\\Wolne.png");
    public final ImageIcon ZajeteMiejsce = new ImageIcon("src\\Zajete.png");//zmiene do trzymania dwoch rysnkow, do miejsca zajetego i wolnego, sa one final bo przypiszemy im wartosc tylko raz
    private final int PlanszaN=350;
    private final int PlanszaM=350;
    private final int VIPN=200;
    private final int VIPM=350;//zmiene do rozmiaru planszy do rysowania miejsc
    private int x;
    private int y;//wspolrzedne nacisneitego pola
    private JLabel pola[][];//tablica dwuwymiarowa do przechowywania rysnkow miejsc
    private JLabel polaVIP[];//analogicznie do pol tylko dla vip
    private Spektakl OstatnioWybrany;//zmienna do przechowywania ostatniego spektaklu na liscie by go potem usunac
    public Teatr()//punkt 6 konstruktor
    {
        this.Spektakle=new LinkedList<Spektakl>();//tworzymy liste spektatkli
        this.pola=null;
        this.polaVIP=null;
        this.OstatnioWybrany=null;
    }
    public void add(int N,int M,int V,String nazwa)//metoda dodawajaca spektakl, przyjmujemy argument liczbe rzedow i miejsc w rzedzie,liczbe miejsc vip,nazwe
    {
        this.Spektakle.add(new Spektakl(N,M,V,nazwa,this));//punkt 7 tworzymy nowy spektakl wedlug jego kosstruktora i dodajemy go, ostatni wskaznik to this na nasz teatr
    }
    public void remove(int id) //metoda usuwajaca dany spektakl o danym id. jednoczesnie punkt 1 typ pierwotny int
    {
        this.Spektakle.remove(id);//usuwamy spektakl o danym indeksie
    }
    public void remove(String nazwa) throws ArrayIndexOutOfBoundsException//punkt 5 przeciążenie usuwania spektaklu, tym razem po nazwie
    {
        int i;//punkt 1, sluzy do zapamietania gdzie usunac
        for (i = 0; i < this.Spektakle.size(); i++)//iterujemy po całej
        {
            if (this.Spektakle.get(i).getNazwa().equals(nazwa))//punkt 3 sprawdzamy po kolei spektakle az trafimy na nasz
            {
                break;//gdy znajdziemy zatrzymujemy petle
            }
        }
        if(i>=this.Spektakle.size())
          throw new ArrayIndexOutOfBoundsException();//punkt 14 wykrywamy blad jesli nie znajdziemy i wyjdziemy za tablice
         this.Spektakle.remove(i);
    }
    public List<Spektakl> getLista()
    {
        return this.Spektakle;
    }
    public Spektakl get(int id)//wyszukanie spektaklu po id
    {
       return this.Spektakle.get(id);//zwracamy spektakl o danym id
    }
    public Spektakl get(String nazwa) throws NullPointerException//punkt 5 przeciążenie wyszukanie spektaklu, tym razem po nazwie
    {
        Spektakl tmp=null;//przypisujemy spektakl tymczasowy;
        for(int i=0;i<this.Spektakle.size();i++)//iterujemy po całej
        {
            if (this.Spektakle.get(i).getNazwa().equals(nazwa))//punkt 3 sprawdzamy po kolei spektakle az trafimy na nasz
            {
                tmp=this.Spektakle.get(i);//przypisujemy do tymczawsowej
                break;//gdy znajdziemy konczymy petle
            }
        }
        if(tmp==null)
            throw new NullPointerException();//punkt 14 wykrywamy blad, ze nie znalezlismy takiego spektaklu i pointer jest na null
        return tmp;//zwracamy znaleziony com.Teatr.Spektakl
    }
    public int getx()
    {
        return this.x;
    }
    public int gety()
    {
        return this.y;
    }
    public void setxy(int x,int y)
    {
        this.x=x;
        this.y=y;
    }//gettery i setter do x y
    public JLabel getPole(int x,int y)
    {
        return this.pola[x][y];
    }
    public JLabel getPole(int x)
    {
        return this.polaVIP[x];
    }//gettery dla pola, przeciazane
    public Spektakl getOstatnioWybrany()
    {
        return this.OstatnioWybrany;
    }
    public void setOstatnioWybrany(Spektakl s)
    {
        this.OstatnioWybrany=s;
    }//setter i getter do ostatnio wybranmego
    public void clear(JFrame frame,Spektakl spektakl)//metoda do usuwania obrazkow ostatniego spektaklu z okna
    {
        if(this.pola==null ||this.OstatnioWybrany==null)//jesli nie bylo nic wczesniej nic nie roibmy
            return;
        for(int i=0;i<spektakl.getN();i++)//chodzimy po tablicy i usuwamy kazdy element z frame'a
        {
            for(int j=0;j<spektakl.getM();j++)
            {
                frame.remove(this.pola[i][j]);
            }
        }
        for(int i=0;i<spektakl.getV();i++)//analogicznie
            frame.remove(this.polaVIP[i]);
        frame.revalidate();
        frame.repaint();//rysujemy i walidujemy ponownie okno

    }
    public void RysujSale(Spektakl spektakl,JFrame frame,JLabel wspolrzedne,JLabel Dane, JTextField PodajDane,JButton ZmianaStatusu)//metoda do narysowania sali do spektaklu, bedziemy rysowac zielone kwadraty jak miejsce wolne i czerwone jak zajete
    {                                                                                               //Bierzemy jako argumenty spektakl, okno, panele do danych iwspolrzednych oraz przycisk
        JLabel rysuj;//bedziemy trzymac elementy do rysowania
        this.pola=new JLabel[spektakl.getN()][spektakl.getM()];
        this.polaVIP=new JLabel[spektakl.getV()];//tworzymy tablice
        int x=40,y=100;//bedziemy chodzic po x i y
        int size;//zmienna do rozmiaru naszych kwadracików
        if(spektakl.getN()!=0 && spektakl.getM()!=0) {//jesli ktorys jest rowny zero to nie skalujemy
            if (spektakl.getN() < spektakl.getM())//przeskalujemy po wiekszym rozmiarze
            {
                size = this.PlanszaN / spektakl.getM();
            } else {
                size = this.PlanszaN / spektakl.getN();
            }//skalujemy
        }else
            size=0;
        for(int i=0;i<spektakl.getN();i++)//tworzymy petla w petli miejsca na ekranie
        {
            for(int j=0;j<spektakl.getM();j++)
            {
                if(spektakl.getMiejsce(i,j).CzyZajete==false)//jesli jest wolne to dajemy obrazek zielone, w przeciwnym razie czerwony
                    rysuj=new JLabel(this.WolneMiejsce);
                else
                    rysuj=new JLabel(this.ZajeteMiejsce);
                Miejsce tmp=spektakl.getMiejsce(i,j);//pobieramy miejsce o takim id
                this.pola[i][j]=rysuj;//przpiyusjemy do naszej tablicy
                rysuj.addMouseListener(new MouseListener() {//dodajemy sluchacz na nacisniecie kwadracika

                    @Override
                    public void mouseClicked(MouseEvent e) {//na klikniecie myszki
                        boolean CzyZajete=tmp.getCzyZajete();//pobieramy do zmiennej logicznej czy miejsce jest zajete
                        ZmianaStatusu.setVisible(true);//zmimenieamy status na widoczny
                        String status;//zmienna do przechowywania teskstu statusu
                        if(CzyZajete) {//jestli jest zajety to
                            ZmianaStatusu.setText("Zwolnij");//zmieniamy tekst na przycisku na zwolnij
                            Dane.setVisible(true);//pole do pokazania danych na tru
                            PodajDane.setVisible(false);//do wpisania na false
                            status = "   Zajęte";
                            Dane.setText("Rezerwacja na " +tmp.getDane());//podajemy na kogo jest rezerwacja
                        }
                        else
                        {
                            ZmianaStatusu.setText("Rezerwuj");//analogicznie
                            Dane.setVisible(false);
                            PodajDane.setVisible(true);
                            status="   Wolne";
                        }
                        wspolrzedne.setText("Miejsce "+Integer.toString( tmp.getx())+" "+Integer.toString(tmp.gety())+status);//zmieniamy wspolrzedne na naszego miejsca i dopisujemy status
                        setxy(tmp.getx(),tmp.gety());//zmienamy wspolrzedne aktualnego nacisnietego miejsca metoda

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                rysuj.setBounds(x,y,size,size);//zmieniamy rozmiary i polozenie
                x+=size+1;//przechodzimy dalej o rozmiar + jeden pixel przerwy nsaszym x
                frame.add(rysuj);//dodajemy do ekranu
            }
            x=40;
            y+=size+1;//wracamy do kolejnego rzedu
        }
        //miejsca vip
        x=20;
        y=550;//poczatkowe wspolrzedne
        if(spektakl.getV()==0)//analogicznie
            size=0;
        else
            size=VIPN/spektakl.getV();//skalujemy
        for(int i=0;i<spektakl.getV();i++)//analogicnzie do poprzedniego tylko teraz jedna petla poniewaz jest jeden rzad vip(powiedzmy, że loża)
        {
            if(spektakl.getMiejsce(i).CzyZajete==false)
                rysuj=new JLabel(this.WolneMiejsce);
            else
                rysuj=new JLabel(this.ZajeteMiejsce);//analogicznie
            rysuj.setBounds(x,y,size,size);
            this.polaVIP[i]=rysuj;
            Miejsce tmp=spektakl.getMiejsce(i);
            rysuj.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    boolean CzyZajete=tmp.getCzyZajete();
                    ZmianaStatusu.setVisible(true);
                    String status;
                    if(CzyZajete) {
                        ZmianaStatusu.setText("Zwolnij");
                        Dane.setVisible(true);
                        PodajDane.setVisible(false);
                        status = "   Zajęte";
                        Dane.setText("Rezerwacja na " +tmp.getDane());
                    }
                    else
                    {
                        ZmianaStatusu.setText("Rezerwuj");
                        Dane.setVisible(false);
                        PodajDane.setVisible(true);
                        status="   Wolne";
                    }
                    wspolrzedne.setText("Miejsce VIP "+Integer.toString( tmp.getx())+" "+status);
                    setxy(tmp.getx(),-1);

                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            x+=size+1;
            frame.add(rysuj);//wszystko analogicznie
        }
        frame.revalidate();
        frame.repaint();//rewalidujemy i rysujemy ponownie nasze okno w celu pokazania nowych elementow
    }
}

