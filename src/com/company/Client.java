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

            int row = Integer.parseInt(reader.readLine());
            int column = Integer.parseInt(reader.readLine());
            // Inserimento parametro
            System.out.println("\nInserire la direzione in cui spostare il segnalino (n-e-w-s): ");
            String direction = tastiera.readLine();

            // Eseguo i controlli sul parametro
            switch(direction.toLowerCase()){
                case "n":
                    if(row == 0){
                        System.out.println("Non è possibile eseguire la mossa");
                        return;
                    }
                break;

                case "e":
                    if(column == 3){
                        System.out.println("Non è possibile eseguire la mossa");
                        return;
                    }
                break;

                case "w":
                    if(column == 0){
                        System.out.println("Non è possibile eseguire la mossa");
                        return;
                    }
                    break;

                case "s":
                    if(row == 3){
                        System.out.println("Non è possibile eseguire la mossa");
                        return;
                    }
                break;
            }

            // Invio il parametro al server
            writer.println(direction);
            writer.flush();

            // Visualizzo la matrice aggiornata
            System.out.println(">> " + reader.readLine() + "\n");
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
