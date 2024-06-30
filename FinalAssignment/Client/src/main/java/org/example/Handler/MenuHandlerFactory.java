package org.example.Handler;

public class MenuHandlerFactory {
    public static MenuHandler createHandler(String roleType, String sessionToken) {
        switch (roleType) {
            case "Admin":
                return new AdminMenuHandler();
            case "Chef":
                return new ChefMenuHandler();
            case "Employee":
                return new EmployeeMenuHandler(sessionToken);
            default:
                return null;
        }
    }

    public static void showInitialMenu() {
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter choice: ");
    }
}
