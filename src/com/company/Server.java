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

    private static final String[][] default_matrix= {
        {"0", "0", "0", "0"},
        {"0", "1", "0", "0"},
        {"0", "0", "0", "0"},
        {"0", "0", "0", "0"},
    };

    //TODO dichiarazione matrice

    public Server (int port)
    {
        this.counter=0;

        this.port = port;
        if(!startServer())
        {
            System.err.println("Errore durante la creazione del Server");
        }
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

                // Stream in input (lettura dei dati in arrivo dal client)
                InputStream s_in = s.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(s_in));

                //Stream in output (invio dati al client)
                OutputStream s_out = s.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s_out));

                //Invio al client la matrice originale
                writer.write("Matrice di default:\n");
                for (String[] row : default_matrix)
                    writer.write(Arrays.toString(row) + "\n");

                String dati = reader.readLine();
                System.out.println(dati);
                //Chiusura della connessione una volta terminata la richiesta
                writer.close();
                s_out.close();
                s_in.close();
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

    public static void main(String[] args) {
        /*
        -- Crea un oggetto di tipo SimpleServer in ascolto
        -- sulla porta 7778
         */
        Server ss = new Server(7778);
        ss.runServer();
    }

    public String printMatrix(String[][] matrix){
        String output = "";

        for (String[] row : matrix)
            output += Arrays.toString(row) +"\n";

        return output;
    }
}
