package org.example.Handler;

import java.io.IOException;
import java.util.Scanner;

public interface MenuHandler {
    void showMenu(Scanner scanner, ProtocolHandler protocolHandler) throws IOException;
}
