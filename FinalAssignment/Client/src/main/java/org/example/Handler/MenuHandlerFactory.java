package org.example.Handler;

public class MenuHandlerFactory {
    public static MenuHandler createHandler(String roleType) {
        switch (roleType) {
            case "Admin":
                return new AdminMenuHandler();
            case "Chef":
                return new ChefMenuHandler();
            case "Employee":
                return new EmployeeMenuHandler();
            default:
                return null;
        }
    }
}
