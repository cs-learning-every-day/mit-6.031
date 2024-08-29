package minesweeper.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
  private final Socket socket;
  private final MinesweeperServer server;

  public ClientHandler(Socket socket, MinesweeperServer server) {
    this.socket = socket;
    this.server = server;
  }

  @Override
  public void run() {
    // handle the client
    try {
      server.handleConnection(socket);
    } catch (IOException ioe) {
      ioe.printStackTrace(); // but don't terminate serve()
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
