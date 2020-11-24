package com.company;

//Import dei package per la connessione client-server
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    private int port;
    private ServerSocket server;
    private int counter;

    public Server (int port)
    {
        this.counter=0;
        this.port = port;

        if(!startServer())
        {
            System.err.println("Errore durante la creazione del Server");
        }
    }

    public static void main(String[] args) {
        /*
        -- Crea un oggetto di tipo SimpleServer in ascolto
        -- sulla porta 7778
         */
        Server ss = new Server(7778);
        ss.runServer();
    }

    public void runServer()
    {
        while (true)
        {
            try
            {
                // Il server resta in attesa di una richiesta
                System.out.println("Server in attesa di richieste");
                Socket s = server.accept();
                this.counter++;
                System.out.println("Un client si e' connesso: utente "+this.counter);

                //Dichiaro la matrice di default
                final String[][] default_matrix = {
                        {"0", "0", "0", "0"},
                        {"0", "1", "0", "0"},
                        {"0", "0", "0", "0"},
                        {"0", "0", "0", "0"},
                };

                // Stream in input (lettura dei dati in arrivo dal client)
                InputStreamReader input_stream = new InputStreamReader(s.getInputStream());
                BufferedReader reader = new BufferedReader(input_stream);

                //Stream in output (invio dati al client)
                PrintWriter writer = new PrintWriter(s.getOutputStream());

                //Invio al client la matrice originale
                writer.println("Matrice iniziale:");
                writer.flush();

                for (String[] r : default_matrix)
                    writer.println(Arrays.toString(r));

                writer.flush();

                //Ricevo i parametri dal client
                String[] parametri = reader.readLine().split(",");
                int row = Integer.parseInt(parametri[0]);
                int col = Integer.parseInt(parametri[1]);

                String[][] matrix = default_matrix;
                matrix[1][1] = "0";
                matrix[row][col] = "1";

                //Invio al client la matrice modificata
                writer.println("Matrice aggiornata:");
                writer.flush();
                for (String[] r : matrix)
                    writer.println(Arrays.toString(r));

                writer.flush();

                //Chiusura della connessione una volta terminata la richiesta
                writer.close();
                reader.close();
                System.out.println("Chiusura connessione effettuata\n");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private boolean startServer()
    {
        try
        {
            server = new ServerSocket(port);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
        System.out.println("Server creato con successo!");
        return true;
    }

}
