package view;

import controller.UserController;
import model.dto.CreateUserDto;
import model.dto.UpdateRequestDto;
import model.dto.UserResponseDto;
import utils.APIResponseTemplate;

import java.util.List;
import java.util.Scanner;

public class UI {
    public final static UserController userController = new UserController();
    private static final Scanner SCANNER = new Scanner(System.in);

    private static void thumbnail(){
        System.out.println("""
                ===============|WelCome to Our SyStem|===============
                1. Create User
                2. Search User by UUID
                3. Search User by Name
                4. Delete User by UUID
                5. Update User by UUID
                6. List All Users
                0. Exit
                """);
    }

    private static int insertOption(){
        System.out.print("[+] Insert Your Option: ");
        String value = SCANNER.nextLine();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private static String prompt(String label) {
        System.out.print(label);
        return SCANNER.nextLine();
    }

    private static void printUserTable(List<UserResponseDto> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        String[] headers = {"UUID", "Name", "Email", "Profile"};
        int uuidWidth = headers[0].length();
        int nameWidth = headers[1].length();
        int emailWidth = headers[2].length();
        int profileWidth = headers[3].length();

        for (UserResponseDto user : users) {
            uuidWidth = Math.max(uuidWidth, safeValue(user.uuid()).length());
            nameWidth = Math.max(nameWidth, safeValue(user.name()).length());
            emailWidth = Math.max(emailWidth, safeValue(user.email()).length());
            profileWidth = Math.max(profileWidth, safeValue(user.profile()).length());
        }

        String line = "+" + "-".repeat(uuidWidth + 2)
                + "+" + "-".repeat(nameWidth + 2)
                + "+" + "-".repeat(emailWidth + 2)
                + "+" + "-".repeat(profileWidth + 2) + "+";

        System.out.println(line);
        System.out.printf("| %-" + uuidWidth + "s | %-" + nameWidth + "s | %-" + emailWidth + "s | %-" + profileWidth + "s |%n",
                headers[0], headers[1], headers[2], headers[3]);
        System.out.println(line);

        for (UserResponseDto user : users) {
            System.out.printf("| %-" + uuidWidth + "s | %-" + nameWidth + "s | %-" + emailWidth + "s | %-" + profileWidth + "s |%n",
                    safeValue(user.uuid()), safeValue(user.name()), safeValue(user.email()), safeValue(user.profile()));
        }
        System.out.println(line);
    }

    private static String safeValue(String value) {
        return value == null ? "" : value;
    }

    private static void printUserTable(UserResponseDto user) {
        if (user == null) {
            System.out.println("No user found.");
            return;
        }
        printUserTable(List.of(user));
    }

    public static void getRendered(){
        boolean isRunning = true;
        while (isRunning){
            thumbnail();
            System.out.print("---");
            try {
                switch (insertOption()){
                    case 1 -> {
                        System.out.println("Create user");
                        String name = prompt("[+] Insert name: ");
                        String email = prompt("[+] Insert email: ");
                        String password = prompt("[+] Insert password: ");
                        CreateUserDto createUserDto = new CreateUserDto(name, email, password);
                        APIResponseTemplate<UserResponseDto> createdUser = userController.createUser(createUserDto);
                        printUserTable(createdUser.data());
                    }
                    case 2 -> {
                        System.out.println("Search User by UUID");
                        String uuid = prompt("[+] Insert UUID: ");
                        APIResponseTemplate<UserResponseDto> user = userController.getUserByUuid(uuid);
                        printUserTable(user.data());
                    }
                    case 3 -> {
                        System.out.println("Search User by Name");
                        String name = prompt("[+] Insert name: ");
                        APIResponseTemplate<List<UserResponseDto>> users = userController.searchUserByName(name);
                        printUserTable(users.data());
                    }
                    case 4 -> {
                        System.out.println("Delete User by UUID");
                        String uuid = prompt("[+] Insert UUID: ");
                        APIResponseTemplate<UserResponseDto> user = userController.getUserByUuid(uuid);
                        printUserTable(user.data());
                        APIResponseTemplate<Integer> deletedUser = userController.deleteUserByUuid(uuid);
                        System.out.println("Deleted rows: " + deletedUser.data());
                    }
                    case 5 -> {
                        System.out.println("Update User by UUID");
                        String uuid = prompt("[+] Insert UUID: ");
                        String name = prompt("[+] Insert new name: ");
                        String email = prompt("[+] Insert new email: ");
                        String password = prompt("[+] Insert new password: ");
                        String profile = prompt("[+] Insert new profile URL: ");
                        UpdateRequestDto updateRequestDto = new UpdateRequestDto(name, email, password, profile);
                        APIResponseTemplate<UserResponseDto> updateUser = userController.updateUserByUuid(uuid, updateRequestDto);
                        printUserTable(updateUser.data());
                    }
                    case 6 -> {
                        APIResponseTemplate<List<UserResponseDto>> users = userController.getAllUser();
                        printUserTable(users.data());
                    }
                    case 0 -> {
                        System.out.println("System closed...");
                        isRunning = false;
                    }
                    default -> System.out.println("No Invalid option");
                }
            } catch (Exception exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
            System.out.println();
        }
    }
}
