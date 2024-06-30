package org.example.Handler;

public class RoleHandlerFactory {
    public static RoleHandler createHandler(String roleType) {
        switch (roleType) {
            case "Admin":
                return new AdminHandler();
            case "Chef":
                return new ChefHandler();
            case "Employee":
                return new EmployeeHandler();
            default:
                return null;
        }
    }
}
