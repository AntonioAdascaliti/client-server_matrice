package com.company;

import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) {

        try
        {
            // Apro una connessione alla porta 7778 del server 127.0.0.1 (localhost)
            System.out.println("Mi sto connettendo...");
            Socket s = new Socket ("127.0.0.1", 7778);
            System.out.println("Connesso!\n");

            // Definisco l'input dell'utente da tastiera
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));

            // Stream in input (lettura dei dati in arrivo dal server)
            InputStreamReader input_stream = new InputStreamReader(s.getInputStream());
            BufferedReader reader = new BufferedReader(input_stream);

            // Stream in output (invio dati al server)
            PrintWriter writer = new PrintWriter(s.getOutputStream());

            // Visualizzo la matrice originale
            System.out.println(">> " + reader.readLine() + "\n");
            for(int i = 0; i < 4; i++)
                System.out.println("\t  " + reader.readLine());

            // Inserimento parametro delle righe
            System.out.println("\nInserire la riga (0-3) dove si vuole spostare il segnalino: ");
            String row = tastiera.readLine();
            // Eseguo i controlli sul parametro
            while(Integer.parseInt(row) < 0 || Integer.parseInt(row) > 3){
                System.out.println("È stato inserito un valore errato. Inserire un valore tra 0 e 3");
                row = tastiera.readLine();
            }

            // Inserimento parametro delle colonne
            System.out.println("Inserire la colonna (0-3) dove si vuole spostare il segnalino: ");
            String column = tastiera.readLine();
            // Eseguo i controlli sul parametro
            while(Integer.parseInt(column) < 0 || Integer.parseInt(column) > 3){
                System.out.println("È stato inserito un valore errato. Inserire un valore tra 0 e 3");
                column = tastiera.readLine();
            }

            // Invio i parametri al server
            writer.println(row + "," + column + "\n");
            writer.flush();

            // Visualizzo la matrice aggiornata
            System.out.println("\n>> " + reader.readLine() + "\n");
            for(int i = 0; i < 4; i++)
                System.out.println("\t  " + reader.readLine());

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
