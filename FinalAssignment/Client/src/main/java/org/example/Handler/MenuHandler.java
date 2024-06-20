package org.example.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public interface MenuHandler {
    void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) throws IOException;
}
