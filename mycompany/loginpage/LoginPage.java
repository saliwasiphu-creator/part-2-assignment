/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginpage;

/**
 *
 * @author Student
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class LoginPage {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Registration
        System.out.println("  ===============================");
        System.out.println("    |        TEXT app          |    ");
        System.out.println("  ===============================");

        System.out.print("Enter name: ");
        String name = input.nextLine();

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cell number: ");
        String cell = input.nextLine();

        // Validation
        boolean valid = true;

        // Username validation
        if (!username.contains("_") || username.length() > 5) {

            System.out.println("Incorrect username(missing _ and max 5 characters)");
            valid = false;

        }

        // Password validation
        if (password.length() < 8
                || !password.matches(".*[A-Z].*")
                || !password.matches(".*[^a-zA-Z0-9].*")) {

            System.out.println("Incorrect password(min 8 chars, 1 capital, 1 special char)");
            valid = false;

        }

        // Cell number validation
        if (!cell.matches("\\+27\\d{9}")) {

            System.out.println("Incorrect cell number (must start with +27 and 9 digits)");
            valid = false;

        }

        // Stop if registration fails
        if (!valid) {

            System.out.println("Registration unsuccessful.");
            return;

        }

        System.out.println("\nRegistration Successful!\n");

        // LOGIN
        System.out.println("+==== LOGIN ====+");

        System.out.print("Enter username: ");
        String loginUser = input.nextLine();

        System.out.print("Enter password: ");
        String loginPass = input.nextLine();

        // Login check
        if (loginUser.equals(username) && loginPass.equals(password)) {

            System.out.println("WELCOME: " + username);

            // ================= TEXT APP =================

            System.out.println("\nWelcomE To Text App");

            boolean running = true;

            while (running) {

                System.out.println("\n===== TEXT APP MENU =====");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");

                System.out.print("Choose option: ");

                String menuChoice = input.nextLine();

                switch (menuChoice) {

                    case "1":

                        System.out.print("How many messages do you want to send? ");

                        int numMessages = Integer.parseInt(input.nextLine());

                        for (int i = 1; i <= numMessages; i++) {

                            System.out.println("\n===== MESSAGE " + i + " =====");

                            Message msg = new Message(i);

                            // Recipient number
                            String recipient;

                            while (true) {

                                System.out.print("Enter recipient number: ");

                                recipient = input.nextLine();

                                if (msg.checkRecipientCell(recipient)) {

                                    System.out.println("Cell number accepted.");
                                    break;

                                } else {

                                    System.out.println("Invalid cell number.");

                                }
                            }

                            msg.setRecipient(recipient);

                            // Message text
                            String text;

                            while (true) {

                                System.out.print("Enter your message: ");

                                text = input.nextLine();

                                if (text.length() <= 300) {

                                    System.out.println("Message stored.");
                                    break;

                                } else {

                                    System.out.println("Message is over 300 characters.");

                                }
                            }

                            msg.setMessageText(text);

                            // Create hash
                            msg.createMessageHash();

                            // Display details
                            System.out.println("\nMESSAGE DETAILS");

                            System.out.println("Message ID: " + msg.getMessageID());

                            System.out.println("Message Hash: " + msg.getMessageHash());

                            System.out.println("Recipient: " + msg.getRecipient());

                            System.out.println("Message: " + msg.getMessageText());

                            // Send/store/discard
                            msg.sentMessage(input);

                        }

                        break;

                    case "2":

                        System.out.println("Coming Soon.");

                        break;

                    case "3":

                        System.out.println("Thank You!");

                        running = false;

                        break;

                    default:

                        System.out.println("Invalid option.");

                }
            }

        } else {

            System.out.println("Unsuccessful Login.");

        }

        input.close();
    }
}

// ================= MESSAGE CLASS =================

class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static int totalMessages = 0;

    // Constructor
    public Message(int number) {

        this.messageNumber = number;
        this.messageID = generateID();

    }

    // Generate random ID
    private String generateID() {

        long num = (long)(Math.random() * 9000000000L) + 1000000000L;

        return String.valueOf(num);

    }

    // Validate recipient number
    public boolean checkRecipientCell(String cell) {

        return cell.startsWith("+") && cell.length() <= 12;

    }

    // Create message hash
    public String createMessageHash() {

        String[] words = messageText.split(" ");

        String firstWord = words[0];

        String lastWord = words[words.length - 1];

        messageHash = messageID.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord + lastWord;

        return messageHash.toUpperCase();

    }

    // Store message in JSON file
    public void storeMessage() {

        String json = "{\n"
                + "\"MessageID\":\"" + messageID + "\",\n"
                + "\"Recipient\":\"" + recipient + "\",\n"
                + "\"Message\":\"" + messageText + "\"\n"
                + "}\n";

        try {

            FileWriter file = new FileWriter("stored_messages.json", true);

            file.write(json);

            file.close();

            System.out.println("Message stored successfully.");

        } catch (IOException e) {

            System.out.println("Error storing message.");

        }
    }

    // Send / discard / store
    public void sentMessage(Scanner input) {

        System.out.println("\n1) Send Message");
        System.out.println("2) Discard Message");
        System.out.println("3) Store Message");

        System.out.print("Choose option: ");

        String choice = input.nextLine();

        switch (choice) {

            case "1":

                totalMessages++;

                sentMessages.add(
                        "ID: " + messageID
                        + " | Hash: " + messageHash
                        + " | To: " + recipient
                        + " | Message: " + messageText
                );

                System.out.println("Message successfully sent.");

                break;

            case "2":

                System.out.println("Message deleted.");

                break;

            case "3":

                storeMessage();

                break;

            default:

                System.out.println("Invalid option.");

        }
    }

    // Print all sent messages
    public static void printMessages() {

        System.out.println("\n==== SENT MESSAGES ====");

        if (sentMessages.isEmpty()) {

            System.out.println("No messages sent.");

            return;

        }

        for (String msg : sentMessages) {

            System.out.println(msg);

        }
    }

    // Return total messages
    public static int returnTotalMessages() {

        return totalMessages;

    }

    // Setters
    public void setRecipient(String recipient) {

        this.recipient = recipient;

    }

    public void setMessageText(String text) {

        this.messageText = text;

    }

    // Getters
    public String getMessageID() {

        return messageID;

    }

    public String getMessageHash() {

        return messageHash;

    }

    public String getRecipient() {

        return recipient;

    }

    public String getMessageText() {

        return messageText;

    }
}