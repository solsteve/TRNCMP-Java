package org.trncmp.test;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
public class jtest_simplehttp {
 
  private static final int fNumberOfThreads = 100;
  private static final Executor fThreadPool = Executors.newFixedThreadPool(fNumberOfThreads);
 
  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(8181);
    while (true) {
      final Socket connection = socket.accept();
      Runnable task = new Runnable() {
          @Override
          public void run() {
            HandleRequest(connection);
          }
        };
      fThreadPool.execute(task);
    }
  }
 
  private static void HandleRequest(Socket s) {
    BufferedReader in;
    PrintWriter out;
    String request;
 
    try {
      String webServerAddress = s.getInetAddress().toString();
      System.out.println("New Connection:" + webServerAddress);
      in = new BufferedReader(new InputStreamReader(s.getInputStream()));
 
      request = in.readLine();
      System.out.println("--- Client request: " + request);
 
      out = new PrintWriter(s.getOutputStream(), true);
      out.println("HTTP/1.0 200");
      out.println("Content-type: application/json; charset=utf-8");
      out.println("Server-name: ephsim");
      String response = "\n{"
          + "\"satno\" : 12345,"
          + "\"pos\" : [1,2,3],"
           + "\"vel\" : [4,5,6]"
          + "}\n\n";
      out.println("Content-length: " + response.getBytes().length);
      out.println("");
      out.println(response);
      out.flush();
      out.close();
      s.close();
    } catch (IOException e)         {
      System.out.println("Failed respond to client request: " + e.getMessage());
    } finally {
      if (s != null) {
        try {
          s.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return;
  }
 
}

