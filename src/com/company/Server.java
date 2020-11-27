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
    private Matrix matrix;

    public Server (int port)
    {
        this.counter=0;
        this.port = port;

        if(!startServer())
        {
            System.err.println("Errore durante la creazione del Server");
        }

        matrix = new Matrix();
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

                // Stream in input (lettura dei dati in arrivo dal client)
                InputStreamReader input_stream = new InputStreamReader(s.getInputStream());
                BufferedReader reader = new BufferedReader(input_stream);

                //Stream in output (invio dati al client)
                PrintWriter writer = new PrintWriter(s.getOutputStream());

                //Invio al client la matrice originale
                writer.println("Matrice iniziale:");
                writer.flush();

                for (String[] r : matrix.getMatrix())
                    writer.println(Arrays.toString(r));

                writer.flush();

                writer.println(matrix.getCurrent_row_position()+"");
                writer.flush();
                writer.println(matrix.getCurrent_column_position()+"");
                writer.flush();

                //Ricevo i parametri dal client
                String param = reader.readLine().toLowerCase();

                int row = matrix.getCurrent_row_position();
                int column = matrix.getCurrent_column_position();
                switch(param){
                    case "n":
                        row--;
                        matrix.setMatrix(row, matrix.getCurrent_column_position());
                    break;

                    case "e":
                        column++;
                        matrix.setMatrix(matrix.getCurrent_row_position(), column);
                    break;

                    case "s":
                        row++;
                        matrix.setMatrix(row, matrix.getCurrent_column_position());
                    break;

                    case "w":
                        column--;
                        matrix.setMatrix(matrix.getCurrent_row_position(), column);
                    break;

                }

                //Invio al client la matrice modificata
                writer.println("Matrice aggiornata:");
                writer.flush();

                for (String[] r : matrix.getMatrix())
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
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        System.out.println("Server creato con successo!");
        return true;
    }

}
