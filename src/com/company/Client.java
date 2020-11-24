package com.company;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try
        {
            //Apro una connessione alla porta 7778 del server 127.0.0.1 (localhost)
            System.out.println("Mi sto connettendo...");
            Socket s = new Socket ("127.0.0.1", 7778);
            System.out.println("Connesso!\n");


            // Crea l'oggetto tastiera per l'input da client
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));

            // Creo il buffer per l'invio dati al server
            //DataOutputStream writer = new DataOutputStream(s.getOutputStream());
            OutputStream s_out = s.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s_out));

            /*
                Ricava lo stream di input dal socket s1
                ed utilizza un oggetto wrapper di classe BufferedReader
                per semplificare le operazioni di lettura
            */
            InputStream s_in = s.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(s_in));

            // Legge l'input e lo visualizza sullo schermo
            String tmp;
            System.out.println(">> " + reader.readLine() + "\n");

            //Visualizzo la matrice base
            while ((tmp = reader.readLine()) != null)
                System.out.println("\t  " + tmp);

            //Inserimento valore riga
            System.out.println("\nInserire la riga (0-3) dove si vuole spostare il segnalino: ");
            String row = tastiera.readLine();

            while(Integer.parseInt(row) < 0 || Integer.parseInt(row) > 3){
                System.out.println("È stato inserito un valore errato. Inserire un valore tra 0 e 3");
                row = tastiera.readLine();
            }

            //Inserimento valore colonna
            System.out.println("Inserire la colonna (0-3) dove si vuole spostare il segnalino: ");
            String column = tastiera.readLine();

            while(Integer.parseInt(column) < 0 || Integer.parseInt(column) > 3){
                System.out.println("È stato inserito un valore errato. Inserire un valore tra 0 e 3");
                column = tastiera.readLine();
            }

            //writer.write(row + "," + column + "\n");

            // Al termine, chiude lo stream di comunicazione e il socket.
            reader.close();
            writer.close();
            s.close();
            System.out.println("\nChiusura connessione effettuata");
        }
        catch(ConnectException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
