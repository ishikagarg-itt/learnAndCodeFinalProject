package org.example.Handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class EmployeeMenuHandler implements MenuHandler{

    private final String sessionToken;

    public EmployeeMenuHandler(String sessionToken){
        this.sessionToken = sessionToken;
    }
    @Override
    public void showMenu(Scanner scanner, BufferedReader in, PrintWriter out) {

    }
}
