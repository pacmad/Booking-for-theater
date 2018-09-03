package com.Teatr;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        Teatr teatr=new Teatr();
        JFrame frame=new JFrame();
        frame.setSize(1080,720);//Tworzymy obiekt teatr, nasze okno i ustawiamy mu rozmiar
        ///////////////////////////////////////////////////
        JLabel Dodawanie=new JLabel("Dodaj Spektakl");
        JLabel Tytul=new JLabel("Program do rezerwacji miejsc na spektakl w teatrze");
        JLabel Opis=new JLabel("Lista Spektakli");
        JLabel Opis2= new JLabel("Podaj Tytuł:");
        JLabel OpisRzedy=new JLabel("Ilość rzędów:");
        JLabel OpisMiejsca=new JLabel("Ilosc miejsc w rzędzie :");
        JLabel OpisVIP=new JLabel("Liczba miejsc VIP:");
        JLabel Error=new JLabel("Błedne dane!");
        JLabel SpektaklInfo=new JLabel();
        JLabel WolneMiejsca=new JLabel();
        JLabel WolneMiejscaVIP=new JLabel();
        JLabel Scena=new JLabel("Scena");
        JLabel ScenaMiejsca=new JLabel("Miejsca");
        JLabel ScenaVIP=new JLabel("Miejsca VIP");
        JLabel Wspolrzedne=new JLabel();
        JLabel Dane=new JLabel();//tworzymy odpowiednie pola tesktow
        //////////////////////////////////////////////
        JTextField TytulSpektaklu=new JTextField();
        JTextField LiczbaRzedow=new JTextField("8");
        JTextField LiczbaMiejscWRzedzie=new JTextField("10");
        JTextField LiczbaMiejscVIP=new JTextField("5");
        JTextField PodajDane=new JTextField();//tworzymy odpowiednie text field'y
        ////////////////////////////////////////////////
        JButton ZmianaStatusu=new JButton("");//tworzymy button do zmiany statusu bo bedzie nam potrzbny przy lisccie
        JScrollPane scroll = new JScrollPane();//tworzymy scroll'owany panell
        JList<Spektakl>Lista=new JList<>();//tworzymy liste na spektakle
        scroll.setViewportView(Lista);//dodajemy liste do panelu
        DefaultListModel model = new DefaultListModel();//tworzeymy liste modeli do ktorej bedziemy dodawac nazwy naszych spektakli do wyswietlenia
        Lista.setModel(model);//dodajemy model do listy
        Lista.addListSelectionListener(new ListSelectionListener() {//tworzymy sluchacz na zmiane elementu nacisnietego na liscie
                                           @Override
                                           public void valueChanged(ListSelectionEvent e) {//po zmianie wartosci
                                               int index=Lista.getSelectedIndex();//pobieramy index aktualnego elementu (-1 to flaga ktora oznacza brak wybranego elementu)
                                               if(Lista.getSelectedIndex()==-1) {//jesli jest to -1 czyli zostal usuniety jakis element to ukrywamy niektore elementy i konczymy returnem
                                                   SpektaklInfo.setText("");
                                                   WolneMiejsca.setText("");
                                                   WolneMiejscaVIP.setText("");
                                                   Wspolrzedne.setText("");
                                                   Dane.setText("");
                                                   PodajDane.setText("");
                                                   PodajDane.setVisible(false);
                                                   Dane.setVisible(false);
                                                   ZmianaStatusu.setVisible(false);
                                                   Scena.setVisible(false);
                                                   ScenaMiejsca.setVisible(false);
                                                   ScenaVIP.setVisible(false);
                                                   return;
                                               }
                                               teatr.clear(frame,teatr.getOstatnioWybrany());//w przeciwnym razie czyscimy poprzednią pokazaną sale teatru(nasze kwadraciki)
                                               Scena.setVisible(true);//zmieniamy niektore potrzebne nam elementy na widoczne
                                               ScenaMiejsca.setVisible(true);
                                               ScenaVIP.setVisible(true);
                                               SpektaklInfo.setText("Spektakl "+teatr.get(index).getNazwa());
                                               WolneMiejsca.setText("Wolne miejsca: "+Integer.toString(teatr.get(index).getLiczbaWolnychMiejsc()));
                                               WolneMiejscaVIP.setText("Wolne miejsca VIP: "+Integer.toString(teatr.get(index).getLiczbaWolnychMiejscVIP()));//pokazujemy odpowiednie informacje
                                               teatr.RysujSale(teatr.get(index),frame,Wspolrzedne,Dane,PodajDane,ZmianaStatusu);//ryusjemy metoda sale
                                               teatr.setOstatnioWybrany(teatr.get(index));//zapisujemy spektakl jako ostatnio wybrany w celu by potem mozna go bylo latwo usunac
                                           }
                                       }

        );
        //////////////////////////////////////////////
        JButton DodajSpektakl=new JButton("Dodaj Spektakl");//tworzymy odpowiednie przyciki
        DodajSpektakl.addActionListener(new ActionListener()//dajemy sluchacz do przecisku dodania spektaklu
        {
            @Override
            public void actionPerformed(ActionEvent e)//po nacisnieciu
            {
                try {
                    teatr.add(Integer.parseInt(LiczbaRzedow.getText()), Integer.parseInt(LiczbaMiejscWRzedzie.getText()), Integer.parseInt(LiczbaMiejscVIP.getText()), TytulSpektaklu.getText());//rztuowanie punkt 11
                    model.add(model.size(), TytulSpektaklu.getText());   //rztutowanie string na int, dodajemy odpowiedni spektakl do teatru oraz modelu
                    Error.setVisible(false);//chowamy error jesli zostal wczesniej wyswietlony
                }catch(NumberFormatException exc)//wyjatek, jestli zamiaast liczby wpisalismy teskt
                {
                    Error.setVisible(true);//zareagujemy errorem
                }catch(NegativeArraySizeException exce)//wyjatek jesli wpiszemy liczbe ujemna
                {
                    Error.setVisible(true);//analogoicznie
                }
            }
        });
        JButton UsunSpektakl=new JButton("Usuń Spektakl");//przycisk do usuwania spektaklu
        UsunSpektakl.addActionListener(new ActionListener() {//analogicznie
            @Override
             public void actionPerformed(ActionEvent e) {
                    int index=Lista.getSelectedIndex();//pobieramy index
                    if(index==-1)//jesli nie ma wybranego czyli nasza flaga to konczymy
                        return;
                    teatr.clear(frame,teatr.get(index));//czyscimy plansze teatru
                    model.remove(index);//usuwamy z modelu i teatru
                    teatr.remove(index);
                    Lista.setSelectedIndex(-1);//ustawiamy zaznaczony element na brak
                }
        }

        );
        ZmianaStatusu.addActionListener(new ActionListener() {//przycisk do rezerowania/zwalniani miejscea
            @Override
            public void actionPerformed(ActionEvent exd) {
                Spektakl spektakl=teatr.get(Lista.getSelectedIndex());//pobieramy odpowiedni spektakl
                String status;
                Miejsce tmp;
                JLabel kwadrat;//zmiene do statusu, miejsca i Jlabela-naszego narysowanego kwadratu
                if(teatr.gety()==-1) {//jesli gety jest -1 to jest to miejsce vip
                    tmp = spektakl.getMiejsce(teatr.getx());
                    kwadrat=teatr.getPole(teatr.getx());//wiec pobieramy miejsce i odpowiedni kwadrat
                }
                else {
                    tmp = spektakl.getMiejsce(teatr.getx(), teatr.gety());
                    kwadrat=teatr.getPole(teatr.getx(),teatr.gety());//analogicznie
                }
                if (!tmp.getCzyZajete() && !PodajDane.getText().equals("")) {//jesli miejsce jest zajete i pole do poadnia danych uzupelnione
                    tmp.rezerwuj(PodajDane.getText());//rezerwujemy metoda miejsce
                    ZmianaStatusu.setText("Zwolnij");//zmieniamy teskt na buttonie
                    Dane.setVisible(true);
                    PodajDane.setVisible(false);
                    PodajDane.setText("");//pokazujemy/chowamy odpowiednie elementy i czyscimy formularz do podania danych
                    status = "   Zajęte";//status zajety
                    Dane.setText("Rezerwacja na " +tmp.getDane());//uzupelniamy dane
                    kwadrat.setIcon(teatr.ZajeteMiejsce);//zmieniamy obrazek
                } else {
                    tmp.zwolnij();//analogicznie
                    ZmianaStatusu.setText("Rezerwuj");
                    Dane.setVisible(false);
                    PodajDane.setVisible(true);
                    status="   Wolne";
                    kwadrat.setIcon(teatr.WolneMiejsce);
                }
                if(teatr.gety()==-1)
                    Wspolrzedne.setText("Miejsce VIP "+Integer.toString( tmp.getx())+status);
                else
                    Wspolrzedne.setText("Miejsce "+Integer.toString( tmp.getx())+" "+Integer.toString(tmp.gety())+status); //podajemy odpowiednie wspolrzedne i status zalezenie od tego czy to vip
                WolneMiejsca.setText("Wolne miejsca: "+Integer.toString(spektakl.getLiczbaWolnychMiejsc()));
                WolneMiejscaVIP.setText("Wolne miejsca VIP: "+Integer.toString(spektakl.getLiczbaWolnychMiejscVIP()));//aktualizujemy liczbe wolnych miejsc

            }
        });
        ////////////////////////////////////
        Dane.setBounds(480,155,200,20);//ustawiamy odpowiednie polozenie i rozmiary naszych elementow
        PodajDane.setBounds(480,155,180,20);
        ZmianaStatusu.setBounds(480,205,130,65);
        Wspolrzedne.setBounds(420,125,260,20);
        ScenaVIP.setBounds(180,500,100,20);
        ScenaMiejsca.setBounds(180,70,100,20);
        Scena.setBounds(180,40,100,20);
        WolneMiejsca.setBounds(420,45,260,20);
        WolneMiejscaVIP.setBounds(420,85,260,20);
        SpektaklInfo.setBounds(420,5,260,20);
        Error.setBounds(840,160,150,20);
        UsunSpektakl.setBounds(900,400,130,65);
        OpisVIP.setBounds(680,120,105,20);
        OpisRzedy.setBounds(680,80,80,20);
        LiczbaRzedow.setBounds(768,75,30,30);
        LiczbaMiejscWRzedzie.setBounds(954,75,30,30);
        OpisMiejsca.setBounds(810,80,140,20);
        Opis2.setBounds(680,40,70,20);
        TytulSpektaklu.setBounds(750,40,200,20);
        Dodawanie.setBounds(680,5,200,20);
        Opis.setBounds(680,300,200,20);
        scroll.setBounds(680,340,200,300);
        Tytul.setBounds(5,5,400,20);
        LiczbaMiejscVIP.setBounds(800,115,30,30);
        DodajSpektakl.setBounds(680,160,130,65);
        //////////////////////////////////////////////
        Error.setVisible(false);
        ZmianaStatusu.setVisible(false);
        PodajDane.setVisible(false);
        Dane.setVisible(false);
        Scena.setVisible(false);
        ScenaMiejsca.setVisible(false);
        ScenaVIP.setVisible(false);//zmieniamy wybrane elementy na niewidoczne metoda setVisible
        ///////////////////////////////////////////////
        frame.add(Dane);//dodajemy wszystkie potrzebne nam elemeenty metoda add do naszego okna
        frame.add(PodajDane);
        frame.add(ZmianaStatusu);
        frame.add(Wspolrzedne);
        frame.add(ScenaVIP);
        frame.add(ScenaMiejsca);
        frame.add(Scena);
        frame.add(WolneMiejsca);
        frame.add(WolneMiejscaVIP);
        frame.add(SpektaklInfo);
        frame.add(Error);
        frame.add(UsunSpektakl);
        frame.add(scroll);
        frame.add(DodajSpektakl);
        frame.add(OpisVIP);
        frame.add(LiczbaMiejscVIP);
        frame.add(LiczbaRzedow);
        frame.add(LiczbaMiejscWRzedzie);
        frame.add(OpisRzedy);
        frame.add(OpisMiejsca);
        frame.add(Opis2);
        frame.add(TytulSpektaklu);
        frame.add(Dodawanie);
        frame.add(Opis);
        frame.add(Tytul);
        /////////////////////////////////////////
        frame.setLayout(null);
        frame.setVisible(true);//pokazujemy okno

    }
}
