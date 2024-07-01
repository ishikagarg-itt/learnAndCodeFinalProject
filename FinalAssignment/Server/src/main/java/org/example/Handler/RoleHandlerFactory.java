package org.example.Handler;

public class RoleHandlerFactory {
    public static RoleHandler createHandler(String roleType, String sessionToken) {
        switch (roleType) {
            case "Admin":
                return new AdminHandler(sessionToken);
            case "Chef":
                return new ChefHandler(sessionToken);
            case "Employee":
                return new EmployeeHandler(sessionToken);
            default:
                return null;
        }
    }
}
