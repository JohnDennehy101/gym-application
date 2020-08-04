import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class provides a console-based user interface to the application's feature set.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */
public class MenuController {
    private Scanner input = new Scanner(System.in);
    private HashMap<String, String> gymPackages;

    String emailInputted = "";
    float height;
    float startWeight;
    String gender;
    boolean emailAlreadyRegistered = true;
    private String strDate;
    private GymAPI gymAPI;
    private Member member;
    private Member loggedInMember;
    private Assessment assessments;
    private Trainer trainer;
    private Trainer loggedInTrainer;
    boolean goodInput = false;

    /**
     * The main method is called by JVM to execute the programme.
     * A menuController instance is declared in this method
     *
     * @param args Contains the command-line arguments as an array of String objects.
     */
    public static void main(String[] args) {
        MenuController c = new MenuController();

    }

    /**
     * Constructor for objects of type MenuController.
     * GymAPI, Member, Trainer instances created.
     * Hashmap created to store gym packages.
     * <p>
     * fillGymResponses() method is used to populate the hashmap using .put().
     * <p>
     * The loadData() method is used to load data (using xstream) from the 'userData.xml' file.
     * <p>
     * The startUpMenu() is called which loads the menu asking the user if they want to login or register.
     */
    public MenuController() {
        gymAPI = new GymAPI();
        member = new Member();
        loggedInMember = new Member();
        //trainer = new Trainer();
        loggedInTrainer = new Trainer();
        //assessments = new Assessment();
        gymPackages = new HashMap<String, String>();
        fillGymResponses();
        try {
            loadData();
        } catch (Exception e) {
            System.out.println("Error loading data from xStream: " + e);
        }
        //runMenu();
        try {
            startUpMenu();
        } catch (Exception e) {
            System.out.println("Error loading start menu" + e);
        }
    }

    /**
     * The first menu that is displayed to the user.
     * The userInput() method is called. This is repeatedly called
     * until the users inputs a valid selection ('l' to login || 'r' to register).
     */

    //User chooses to login or register
    private void startUpMenu() {
        System.out.println("Play Gym Application");
        System.out.println("---------");
        System.out.println("Do you want to login or register?");
        System.out.println(" l) Login");
        System.out.println(" r) Register");

        userInput();

        while (!goodInput) {
            userInput();
        }
    }

    /**
     * If the user inputs 'l', the loginUser() method is called.
     * <p>
     * If the user inputs 'r', a submenu is displayed (Register as Member || Register as Trainer).
     * If the user chooses to register as a member, the addMember() method is called.
     * If the user chooses to register as a trainer, the addTrainer() method is called.
     */

    private void userInput() {
        String optionChosen = "";
        char firstCharacter = 0;
        char choice2 = 0;
        String memberOrTrainer = "";
        System.out.print("==>> ");
        //input.nextLine();
        optionChosen = input.next().toUpperCase();
        firstCharacter = optionChosen.charAt(0);
        boolean correctInput = false;
        if (firstCharacter == 'L' || firstCharacter == 'R') {
            /*if ("L".equals(firstCharacter)) {
                System.out.println(" Are you a member (m) or a trainer (t)?");
                char choice2 = input.next().charAt(0);
            } */
            goodInput = true;
            if (firstCharacter == 'L') {


                try {
                    loginUser();
                } catch (Exception e) {
                    System.out.println("Login Member Method Failed:" + e);
                }
            } else if (firstCharacter == 'R') {
                System.out.println(" m) Register as Member");
                System.out.println(" t) Register as Trainer");
                while (!correctInput) {
                    System.out.print("==>> ");
                    memberOrTrainer = input.next().toUpperCase();
                    choice2 = memberOrTrainer.charAt(0);
                    if (choice2 == 'M' || choice2 == 'T') {
                        correctInput = true;
                        if (choice2 == 'M') {
                            addMember();
                        } else if (choice2 == 'T') {
                            addTrainer();
                        }
                    } else {
                        System.out.println("Invalid input. Please enter m if you want to register as a member or r if you want to register as a trainer.");
                    }
                }
            }
        } else {
            System.out.println("Invalid input. Please input l to login or r to register");

        }

    }

    /**
     * This method prompts the user to enter the email associated with their account.
     * <p>
     * The searchMembersByEmail and searchTrainersByEmail methods in the GymAPI class are used to
     * determine if the emailInputted matches an email with a registered account.
     * If the input matches the email with a trainer's account, the loggedInTrainerMenu() is called.
     * If the input matches the email with a member's account, the loggedInMemberMenu() is called.
     * <p>
     * If there is no match for the emailInputted, 'Access Denied' is printed to the console and the programme
     * terminates.
     */

    private void loginUser() {
        System.out.print("\nEnter the email linked to your account: ");
        emailInputted = input.next();

        if (gymAPI.searchMembersByEmail(emailInputted) != null) {
            loggedInMember = gymAPI.searchMembersByEmail(emailInputted);
            try {
                System.out.println("\nSuccessfully logged in - Member Account \n");
                loggedInMemberMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in member menu: " + e);
            }
        } else if (gymAPI.searchTrainersByEmail(emailInputted) != null) {
            loggedInTrainer = gymAPI.searchTrainersByEmail(emailInputted);
            try {
                System.out.println("\nSuccessfully logged in - Trainer Account");
                loggedInTrainerMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in trainer menu" + e);
            }
        } else {
            System.out.println("Access Denied");
            System.exit(0);
        }

    }

    /**
     * The loggedInMemberMenu() method prints out the options available for the logged in member.
     * The user is prompted to enter an option.
     * <p>
     * If they enter 1 (View Profile), the logged in member's details are printed to the console.
     * <p>
     * If they enter 2 (Update Profile), the updateProfileMenu() method is called (which displays a sub-menu).
     * <p>
     * If they enter 3 (Progress Sub Menu), the progressSubMenu() method is called (which displays a sub-menu).
     * <p>
     * <p>
     * If they enter 4 (Search for a trainer by email), the searchTrainersByEmail() method is called.
     * <p>
     * <p>
     * If they enter 5 (Search for a trainer by name), the searchTrainersByName() method is called (which displays a sub-menu).
     * <p>
     * <p>
     * If they enter 0 (Return to Main Menu), the saveData() method is called (to save the data to 'userData.xml') and the
     * exitProgramme() method is called which exits the programme
     */

    //sub menu for logged in members
    private void loggedInMemberMenu() {
        boolean goodInput = false;
        int menuChoice = 0;
        System.out.println("Logged In");
        System.out.println("---------");
        System.out.println("  1) View Profile");
        System.out.println("  2) Update Profile");
        System.out.println("  3) Progress Sub Menu");
        System.out.println("  4) Search for a trainer by email");
        System.out.println("  5) Search for a trainer by name");
        System.out.println("  0) Exit Programme ");
        while (!goodInput) {
            try {
                System.out.print("==>> ");
                menuChoice = input.nextInt();
                goodInput = true;
                //return menuChoice;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input - please enter an integer value.");
            }
        }


        while (menuChoice != 0) {

            switch (menuChoice) {
                case 1:
                    System.out.println("\n" + loggedInMember + " BMI: " + GymUtility.calculateBMI(loggedInMember, loggedInMember.latestAssessment()) +
                            " BMI Category: " + GymUtility.determineBMICategory(GymUtility.calculateBMI(loggedInMember, loggedInMember.latestAssessment())) +
                            " Ideal Body Weight: " + GymUtility.isIdealBodyWeight(loggedInMember, loggedInMember.latestAssessment()) + "\n");
                    try {
                        loggedInMemberMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading logged in member menu: " + e);
                    }
                    break;
                case 2:
                    try {
                        updateProfileMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading update Member profile menu: " + e);
                    }
                    break;
                case 3:
                    try {
                        progressSubMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading member progress sub menu: " + e);
                    }
                    break;
                case 4:
                    searchTrainersByEmail();
                    break;
                case 5:
                    searchTrainersByName();
                    break;
                default:
                    System.out.println("Invalid option entered: " + menuChoice);
                    break;
            }

            //pause the program so that the user can read what we just printed to the terminal window
            System.out.println("\nPress any key to continue...");
            input.nextLine();
            input.nextLine();  //this second read is required - bug in Scanner class; a String read is ignored straight after reading an int.
            try {
                loggedInMemberMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in member menu: " + e);
            }
            //exit the programme

        }
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data to xml file : " + e);
        }
        try {
            exitProgramme();
        } catch (Exception e) {
            System.out.println("Error exiting programme: " + e);
        }

    }

    /**
     * The updateProfileMenu() displays a number of options for the logged in member on what they can update
     * on their profile.
     * <p>
     * If they select 1 (Edit Name), the editName() method is called.
     * <p>
     * If they select 2 (Edit Address, the editAddress() method is called.
     * <p>
     * If they select 3, (Edit Gender), the editGender() method is called.
     * <p>
     * If they select 4 (Edit Height), the editHeight() method is called.
     * <p>
     * If they select 5 (Edit Weight), the editWeight() method is called.
     * <p>
     * If they select 6 (Edit Chosen Package), the editChosenPackgae() method is called.
     * <p>
     * If they select 7 (Edit All Details), the editMember() method is called.
     * <p>
     * If they select 0 (Return to Main Menu), the loggedInMemberMenu() method is called.
     */

    private void updateProfileMenu() {
        boolean validOption = false;
        int profileUpdateChosen = 0;
        System.out.println("\nUpdate Profile Menu");
        System.out.println("---------");
        System.out.println("  1) Edit Name");
        System.out.println("  2) Edit Address");
        System.out.println("  3) Edit Gender");
        System.out.println("  4) Edit Height");
        System.out.println("  5) Edit Weight");
        System.out.println("  6) Edit Chosen Package");
        System.out.println("  7) Edit All Details");
        System.out.println("  0) Return to Main Menu");
        while (!validOption) {
            System.out.print("==>> ");
            profileUpdateChosen = input.nextInt();
            if (profileUpdateChosen >= 0 && profileUpdateChosen <= 7) {
                validOption = true;
            } else {
                System.out.println("\nInvalid input. Please select a valid option.");
            }
        }

        while (profileUpdateChosen != 0) {
            switch (profileUpdateChosen) {
                case 1:
                    try {
                        editName();
                    } catch (Exception e) {
                        System.out.println("Error running editName method: " + e);
                    }
                    try {
                        updateProfileMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading update profile menu: " + e);
                    }
                    break;
                case 2:
                    try {
                        editAddress();
                    } catch (Exception e) {
                        System.out.println("Error running editAddress method: " + e);
                    }
                    try {
                        updateProfileMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading update profile menu: " + e);
                    }
                    break;
                case 3:
                    try {
                        editGender();
                    } catch (Exception e) {
                        System.out.println("Error running editGender method: " + e);
                    }
                    try {
                        updateProfileMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading update profile menu: " + e);
                    }
                    break;
                case 4:
                    try {
                        editHeight();
                    } catch (Exception e) {
                        System.out.println("Error running editHeight method: " + e);
                    }
                    try {
                        updateProfileMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading update profile menu: " + e);
                    }
                    break;
                case 5:
                    try {
                        editWeight();
                    } catch (Exception e) {
                        System.out.println("Error running editWeight method: " + e);
                    }
                    try {
                        updateProfileMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading update profile menu: " + e);
                    }
                    break;
                case 6:
                    try {
                        editChosenPackage();
                    } catch (Exception e) {
                        System.out.println("Error running editChosenPackage method: " + e);
                    }
                    try {
                        updateProfileMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading update profile menu: " + e);
                    }
                    break;
                case 7:
                    try {
                        editMember();
                    } catch (Exception e) {
                        System.out.println("Error running editMember method: " + e);
                    }
                    break;
            }

        }
        try {
            loggedInMemberMenu();
        } catch (Exception e) {
            System.out.println("Error loading logged in member menu: " + e);
        }
    }

    /**
     * The editName() method allows the member to update their name.
     * The member's current name is printed to the console.
     * The user is prompted to enter a new name.
     * If the length of the new name is greater than 30 characters, it is truncated at index position 30.
     * The setName() method is used to update the logged in member's name (determined from the email inputted on login).
     * <p>
     * The saveData() method is called to save this updated information to the xml file.
     * The loadData() method is then called to load this updated information to the programme (if the user
     * checks their profile after updating the name, the updated name will be displayed).
     */

    private void editName() {
        input.nextLine();
        System.out.println("Current Name: " + loggedInMember.getName());
        System.out.print("Update Name: ");
        String name = input.nextLine();
        if (name.length() > 30) {
            name = name.substring(0, 30);
        }
        loggedInMember.setName(name);
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e);
        }
        try {
            loadData();
        } catch (Exception e) {
            System.out.println("Error loading data: " + e);
        }
        System.out.println("Successfully updated name to " + name);
    }

    /**
     * The editAddress() method allows the member to update their address.
     * The member's current address is printed to the console.
     * The user is prompted to enter a new address.
     * The setAddress() method is used to update the logged in member's address (determined from
     * the email inputted on login).
     * <p>
     * The saveData() method is called ot save this updated information to the xml file.
     * The loadData() method is then called to load this updated information to the programme (if the
     * user checks their profile after updating the address, the updated address will be displayed).
     */

    private void editAddress() {
        input.nextLine();
        System.out.println("Current Address: " + loggedInMember.getAddress());
        System.out.print("Update Address:  ");
        String address = input.nextLine();
        loggedInMember.setAddress(address);
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e);
        }
        try {
            loadData();
        } catch (Exception e) {
            System.out.println("Error loading data from file: " + e);
        }
        System.out.println("Successfully updated address to " + address);
    }

    /**
     * The editGender() method allows the member to update their gender.
     * The member's current gender is printed to the console.
     * The user is prompted to update their gender.
     * <p>
     * If the user enters 'm' or 'M' or 'f' or 'F', the setGender() method updates
     * the member's gender to the selected option.
     * <p>
     * If anything else is inputted, the setGender() method updates the member's gender to 'Unspecified'.
     */


    private void editGender() {
        input.nextLine();
        String genderPrefix = "";
        System.out.println("Current Gender: " + loggedInMember.getGender());
        System.out.print("Update Gender:  ");
        gender = input.nextLine().toUpperCase();
        if (gender.charAt(0) == 'M' || gender.charAt(0) == 'F') {
            genderPrefix = gender.substring(0, 1).toUpperCase();
        } else {
            genderPrefix = "Unspecified";
        }
        loggedInMember.setGender(genderPrefix);
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e);
        }
        try {
            loadData();
        } catch (Exception e) {
            System.out.println("Error loading data from file");
        }
        System.out.println("Successfully updated gender to " + genderPrefix);
    }

    /**
     * The editHeight() method allows the member to update their height.
     * The member's current height is printed to the console.
     * The user is prompted to update their height.
     * <p>
     * If the user enters a value between 1 and 3, the setHeight() method is used to update the
     * member's height to the value inputted.
     * <p>
     * If the value inputted is not between 1 and 3, the user is prompted again to enter a valid height.
     * This continues until a correct value is inputted. The setHeight() method is used to update the member's
     * height once a correct value has been inputted.
     */

    private void editHeight() {
        boolean correctHeight = false;
        boolean goodInputHeight = false;
        System.out.println("Current Height: " + loggedInMember.getHeight());


        while (!correctHeight == true) {
            while (!goodInputHeight) {
                try {
                    System.out.print("Height:  ");
                    height = input.nextFloat();
                    goodInputHeight = true;
                    //return menuChoice;
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Invalid input - please enter a numeric value.");
                }
            }
            if (height >= 1 && height <= 3) {
                correctHeight = true;
            } else {
                System.out.println("Invalid Height. Height must be between 1 and 3.");
                goodInputHeight = false;
            }
        }
        loggedInMember.setHeight(height);
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e);
        }
        try {
            loadData();
        } catch (Exception e) {
            System.out.println("Error loading data from file: " + e);
        }
        System.out.println("Successfully updated height to " + height);
    }

    /**
     * The editWeight() method allows the member to update their weight.
     * The member's weight at registration is printed to the console.
     * The user is prompted to update their weight.
     * <p>
     * If the value inputted is between 35 and 250, the setWeight() method is called
     * to update the member's weight to the value inputted.
     * <p>
     * If the value inputted is not in this range, an error message is printed to the console.
     * The user is then repeatedly prompted to enter a valid weight until they provide an input within the
     * range (35-250). The setWeight() method is used to update the member's weight once a correct value
     * has been inputted.
     */

    private void editWeight() {
        boolean correctWeight = false;
        boolean goodInputWeight = false;
        System.out.println("Current Weight: " + loggedInMember.getStartWeight());
        while (!correctWeight) {
            while (!goodInputWeight) {
                try {
                    System.out.print("Weight:  ");
                    startWeight = input.nextFloat();
                    goodInputWeight = true;
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Invalid input - please enter a numeric value.");
                }
            }
            if (startWeight >= 35 && startWeight <= 250) {
                correctWeight = true;
            } else {
                System.out.println("Invalid starting weight. Weight must be between 35 and 250.");
                goodInputWeight = false;
            }
        }

        loggedInMember.setStartWeight(startWeight);
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e);
        }
        try {
            loadData();
        } catch (Exception e) {
            System.out.println("Error loading data from file: " + e);
        }
        System.out.println("Successfully updated weight to " + startWeight);
    }

    /**
     * The editChosenPackage() method allows the member to update their chosen package.
     * The member's current package is printed to the console.
     * <p>
     * The user is prompted to select if they want a premium or student package.
     * If the user does not enter 'p' or 'P' for Premium or 's' or 'S' for Student, an error message is printed
     * to the console. The user is then prompted again until they provide valid input.
     * <p>
     * If the user chose a premium package, a message is printed to the console informing the user what packages are
     * available with the benefits in each package.
     * The user is then prompted to select a package ('Package 1 || 'Package 2').
     * If they do not provide valid input, an error message is printed to the console and they are prompted
     * to provide valid input (repeated until correct option is chosen).
     * <p>
     * Once a valid premium package has been chosen, the user is prompted to confirm that they are happy to proceed.
     * If they enter 'y' or 'Y', the chosenPackage() method from the member class is used to update the member's chosen
     * package.
     * If they enter 'n' or 'N', the package update is abandoned and the updateProfileMenu() is loaded.
     * <p>
     * If the user chose a student package, a message is printed to the console informing the user what packages
     * are available with the benefits in each package.
     * The user is then prompted to select a package ('WIT' || 'Package 3' (for students from other colleges)).
     * If they do not provide valid input, an error message is printed to the console and they are prompted to
     * provide valid input (repeated until correct option is chosen).
     * <p>
     * Once a valid student package has been chosen, the user is prompted to confirm that they are happy to proceed.
     * If they enter 'y' or 'Y', the chosenPackage() method from the member class is used to update the member's chosen
     * package.
     * If they enter 'n' or 'N', the package update is abandoned and the updateProfileMenu() is loaded.
     */


    private void editChosenPackage() {
        boolean correctMemberType = false;
        boolean proceedOrAbandon = false;
        String memberType = "";
        String chosenPackage = "";
        String proceedWithRegistration = "";
        boolean correctPremiumPackage = false;
        boolean correctStudentPackage = false;
        input.nextLine();
        System.out.println("Current Package: " + loggedInMember.getChosenPackage());
        System.out.println("Do you want a premium member or a student member package (student ID number required)?");
        while (!correctMemberType) {

            System.out.print("==>>  ");

            memberType = input.nextLine();
            if ((memberType.toUpperCase().charAt(0) == 'P') || (memberType.toUpperCase().charAt(0) == 'S')) {
                correctMemberType = true;
            } else {
                System.out.println("Invalid Input. Please input Premium or Student to proceed.");

            }
        }
        if (memberType.toUpperCase().charAt(0) == 'P') {
            System.out.println("\nPackage 1: Free access to the gym and free classes.\n" +
                    "Package 2: Free access to the gym with €3 fees for classes.\n");
            while (!correctPremiumPackage) {
                System.out.print("Enter your chosen package:  ");
                chosenPackage = input.nextLine();
                String chosenPremiumPackage = gymPackages.get(chosenPackage);
                if (chosenPackage.toUpperCase().equals("PACKAGE 1") || chosenPackage.toUpperCase().equals("PACKAGE 1")) {
                    System.out.println("\n" + chosenPremiumPackage + "\n");
                    correctPremiumPackage = true;
                } else {
                    System.out.println("Incorrect input. Please enter Package 1 or Package 2 to proceed.");
                }
            }

            while (!proceedOrAbandon) {
                System.out.println("Are you happy with this package (Y/N)?");
                proceedWithRegistration = input.nextLine();

                if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y' || proceedWithRegistration.toUpperCase().charAt(0) == 'N') {
                    proceedOrAbandon = true;
                } else {
                    System.out.println("\nInvalid option. Please input Yes or No to proceed.");
                }
            }


            if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y') {
                System.out.println("\nSuccessfully updated member profile!\n");
                loggedInMember.chosenPackage(chosenPackage);
            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'N') {
                System.out.print("\nMember Update Abandoned. Please try again with correct details.\n");
                System.out.println("Please press any key to go back to the logged in menu.");
                System.out.print("==>> ");
                emailAlreadyRegistered = true;
                try {
                    updateProfileMenu();
                } catch (Exception e) {
                    System.out.println("Error loading update profile menu: " + e);
                }
            }


        } else if (memberType.toUpperCase().charAt(0) == 'S') {
            System.out.println("\nWIT: Choose the WIT package if you are a student at the college. It allows access to the gym during term time. €4 fee per class.\n" +
                    "Package 3: Choose package 3 if you are a student at a college other than WIT. Access to the gym off-peak with €5 fee per class.\n");

            while (!correctStudentPackage) {
                System.out.print("Enter your chosen package:  ");
                chosenPackage = input.nextLine();
                String chosenStudentPackage = gymPackages.get(chosenPackage);
                if (chosenPackage.equals("WIT") || chosenPackage.equals("Package 3")) {
                    System.out.println("\n" + chosenStudentPackage + "\n");
                    correctStudentPackage = true;
                } else {
                    System.out.println("Incorrect input. Please enter WIT or Package 3 to proceed.");
                }
            }
            while (!proceedOrAbandon) {
                System.out.println("Are you happy with this package (Y/N)?");
                proceedWithRegistration = input.nextLine();

                if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y' || proceedWithRegistration.toUpperCase().charAt(0) == 'N') {
                    proceedOrAbandon = true;
                } else {
                    System.out.println("\nInvalid option. Please input Yes or No to proceed.");
                }
            }

            if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y' && chosenPackage.toUpperCase().equals("WIT") || proceedWithRegistration.toUpperCase().charAt(0) == 'Y'
                    && chosenPackage.toUpperCase().equals("PACKAGE 3")) {

                loggedInMember.chosenPackage(chosenPackage);
                System.out.println("\nSuccessfully updated member profile!\n");

            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'N') {

                System.out.print("\nMember Update Abandoned. Please try again with correct details.\n");
                System.out.println("Please press any key to go back to the logged in menu.");
                System.out.print("==>> ");
                emailAlreadyRegistered = true;
                try {
                    loggedInMemberMenu();
                } catch (Exception e) {
                    System.out.println("Error loading logged in member menu: " + e);
                }
            }
        }
    }


    /**
     * The editMember() method allows the member to update all editable details at once.
     * It prompts the user to update the following:
     * Name
     * Address
     * Gender
     * Height
     * Weight
     * Chosen Package
     * <p>
     * Once all these details have been updated the saveData() method is called to save the update info
     * to the xml file (using xstream).
     * The loadData() method loads this updated info from the xml file using xstream.
     * <p>
     * The loggedInMemberMenu() method is is then called to display the logged in member view to the user.
     */

    //edit the existing member data
    private void editMember() {
        boolean correctGender = false;
        boolean correctHeight = false;
        boolean correctWeight = false;
        boolean correctMemberType = false;
        boolean correctPremiumPackage = false;
        boolean correctStudentPackage = false;
        boolean goodInputWeight = false;
        boolean goodInputHeight = false;
        String chosenPackage = "";
        String genderPrefix = "";
        String memberType = "";
        String proceedWithRegistration = "";
        emailAlreadyRegistered = true;
        System.out.println(gymAPI.searchMembersByEmail(emailInputted));


        input.nextLine();
        System.out.print("Name: ");
        String name = input.nextLine();
        if (name.length() > 30) {
            name = name.substring(0, 30);
        }
        System.out.print("Address:  ");
        String address = input.nextLine();

        while (!correctGender) {
            System.out.print("Gender:  ");
            gender = input.nextLine().toUpperCase();
            if (gender.charAt(0) == 'M' || gender.charAt(0) == 'F') {
                correctGender = true;
                genderPrefix = gender.substring(0, 1).toUpperCase();
            } else {
                System.out.println("Invalid input. Input for gender must be M or F.");
            }
        }

        while (!correctHeight == true) {
            while (!goodInputHeight) {
                try {
                    System.out.print("Height:  ");
                    height = input.nextFloat();
                    goodInputHeight = true;
                    //return menuChoice;
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Invalid input - please enter a numeric value.");
                }
            }
            if (height >= 1 && height <= 3) {
                correctHeight = true;
            } else {
                System.out.println("Invalid Height. Height must be between 1 and 3.");
                goodInputHeight = false;
            }
        }
        while (!correctWeight) {
            while (!goodInputWeight) {
                try {
                    System.out.print("Weight:  ");
                    startWeight = input.nextFloat();
                    goodInputWeight = true;
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Invalid input - please enter a numeric value.");
                }
            }
            if (startWeight >= 35 && startWeight <= 250) {
                correctWeight = true;
            } else {
                System.out.println("Invalid starting weight. Weight must be between 35 and 250.");
                goodInputWeight = false;
            }
        }
        input.nextLine();

        System.out.println("Do you want to register as a premium member or a student member (student ID number required)?");
        while (!correctMemberType) {

            System.out.print("==>>  ");

            memberType = input.nextLine();
            if ((memberType.toUpperCase().charAt(0) == 'P') || (memberType.toUpperCase().charAt(0) == 'S')) {
                correctMemberType = true;
            } else {
                System.out.println("Invalid Input. Please input Premium or Student to proceed.");

            }
        }
        if (memberType.toUpperCase().charAt(0) == 'P') {
            System.out.println("\nPackage 1: Free access to the gym and free classes.\n" +
                    "Package 2: Free access to the gym with €3 fees for classes.\n");
            while (!correctPremiumPackage) {
                System.out.print("Enter your chosen package:  ");
                chosenPackage = input.nextLine();
                String chosenPremiumPackage = gymPackages.get(chosenPackage);
                if (chosenPackage.toUpperCase().equals("PACKAGE 1") || chosenPackage.toUpperCase().equals("PACKAGE 1")) {
                    System.out.println("\n" + chosenPremiumPackage + "\n");
                    correctPremiumPackage = true;
                } else {
                    System.out.println("Incorrect input. Please enter Package 1 or Package 2 to proceed.");
                }
            }


            System.out.println("Are you happy with this package (Y/N)?");
            proceedWithRegistration = input.nextLine();

            if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y') {
                gymAPI.addMember(new PremiumMember(emailInputted, name, address,
                        genderPrefix, height, startWeight, chosenPackage));
                System.out.println("\nSuccessfully updated member profile!\n");
            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'N') {
                System.out.print("\nMember Update Abandoned. Please try again with correct details.\n");
                System.out.println("Please press any key to go back to the logged in menu.");
                System.out.print("==>> ");
                emailAlreadyRegistered = true;
                try {
                    loggedInMemberMenu();
                } catch (Exception e) {
                    System.out.println("Error loading logged in member menu: " + e);
                }
            }


        } else if (memberType.toUpperCase().charAt(0) == 'S') {
            System.out.println("\nWIT: Choose the WIT package if you are a student at the college. It allows access to the gym during term time. €4 fee per class.\n" +
                    "Package 3: Choose package 3 if you are a student at a college other than WIT. Access to the gym off-peak with €5 fee per class.\n");

            while (!correctStudentPackage) {
                System.out.print("Enter your chosen package:  ");
                chosenPackage = input.nextLine();
                String chosenStudentPackage = gymPackages.get(chosenPackage);
                if (chosenPackage.equals("WIT") || chosenPackage.equals("Package 3")) {
                    System.out.println("\n" + chosenStudentPackage + "\n");
                    correctStudentPackage = true;
                } else {
                    System.out.println("Incorrect input. Please enter WIT or Package 3 to proceed.");
                }
            }

            System.out.println("Are you happy with this package (Y/N)?");
            proceedWithRegistration = input.nextLine();

            if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y' && chosenPackage.toUpperCase().equals("WIT")) {
                String collegeName = "WIT";
                System.out.print("Enter your Student Number:  ");
                String studentNumber = input.nextLine();
                gymAPI.addMember(new StudentMember(emailInputted, name, address,
                        genderPrefix, height, startWeight, chosenPackage, studentNumber, collegeName));
                System.out.println("\nSuccessfully updated member profile!\n");
            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y' && chosenPackage.toUpperCase().equals("PACKAGE 3")) {
                System.out.print("Enter your College Name:  ");
                String collegeName = input.nextLine();
                System.out.print("Enter your Student Number:  ");
                String studentNumber = input.nextLine();
                gymAPI.addMember(new StudentMember(emailInputted, name, address,
                        genderPrefix, height, startWeight, chosenPackage, studentNumber, collegeName));
                System.out.println("\nSuccessfully updated member profile.!\n");
            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'N') {
                System.out.print("\nMember Update Abandoned. Please try again with correct details.\n");
                System.out.println("Please press any key to go back to the logged in menu.");
                System.out.print("==>> ");
                emailAlreadyRegistered = true;
                try {
                    addMember();
                } catch (Exception e) {
                    System.out.println("Error running addMember() method: " + e);
                }
            }
        }

        Member member = loggedInMember;
        member.setName(name);
        member.setAddress(address);
        member.setGender(genderPrefix);
        member.setHeight(height);
        member.setStartWeight(startWeight);
        member.chosenPackage(chosenPackage);
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data to 'userData.xml': " + e);
        }
        try {
            loadData();
        } catch (Exception e) {
            System.out.println("Error loading data from 'userData.xml': " + e);
        }
        try {
            loggedInMemberMenu();
        } catch (Exception e) {
            System.out.println("Error loading logged in member menu: " + e);
        }
    }

    /**
     * The progressSubMenu() method displays the member progress menu to the member.
     * <p>
     * If they enter a valid option (0-2), the selected option is executed (if invalid input, prompted repeatedly
     * until correct input is provided):
     * <p>
     * 0 - loggedInMemberMenu() method is called : displays the logged in member menu to the user
     * 1 - viewProgressByWeight() method is called: displays the member's progress in weight (by comparing
     * the start weight and the last assessment weight).
     * 2 - viewProgressByWaist() method is called: displays the member's progress in waist (by comparing the
     * waist measurement of the second last and last assessments completed.
     * 3 - viewProgressByThigh() method is called: displays the member's progress in thigh size (by comparing the
     * thigh measurement of the second last and last assessments completed).
     * <p>
     * I
     */

    private void progressSubMenu() {
        boolean validInput = false;
        int menuChoice = 0;
        System.out.println("Progress Sub-Menu");
        System.out.println("---------");

        System.out.println("  1) View Progress by Weight");
        System.out.println("  2) View Progress by waist measurement");
        System.out.println("  3) View Progress by thigh measurement");
        System.out.println("  0) Return to Main Menu");
        //input.nextLine();
        while (!validInput) {
            System.out.print("==>> ");
            menuChoice = input.nextInt();
            if (menuChoice >= 0 && menuChoice <= 3) {
                validInput = true;
            } else {
                System.out.println("\nInvalid input. Please select an option by inputting a valid index.");
            }
        }
        if (menuChoice == 1) {
            try {
                viewProgressByWeight();
                validInput = false;
            } catch (Exception e) {
                System.out.println("Error running viewProgressByWeight method: " + e);
            }
        } else if (menuChoice == 2) {
            try {
                viewProgressByWaist();
                validInput = false;
            } catch (Exception e) {
                System.out.println("Error running viewProgressByWaist method: " + e);
            }
        } else if (menuChoice == 3) {
            try {
                viewProgressByThigh();
                validInput = false;
            } catch (Exception e) {
                System.out.println("Error running viewProgressByThigh method: " + e);
            }
        } else {
            try {
                loggedInMemberMenu();
                validInput = false;
            } catch (Exception e) {
                System.out.println("Error loading logged in member menu: " + e);
            }
        }
    }

    /**
     * The viewProgressByWeight() method allows the member to see their change in weight over time.
     * <p>
     * If no assessments have been completed for the member, 'No assessments have been completed yet' is printed
     * to the console and the loggedInMemberMenu() method is called which displays the logged in member menu.
     * <p>
     * If assessments have been completed, a for loop is used to loop through the sorted assessment dates (using the
     * sortedAssessmentDates() method from the member class).
     * The member's assessment weights are printed to the console in order (by using the getAssessments() method
     * from the member class to get the hashmap with the assessments with each sorted assessment date passed
     * (key search using the .get() call). These are added to the memberAssessmentDates ArrayList (in order).
     * <p>
     * If the size of the memberAssessmentDates ArrayList is greater than 1 (More than 1 assessment has been completed),
     * a comparison is made by getting the latest assessment
     * (using the sortedAssessmentDates().last() call from the SortedSet) with the second last assessment (using the
     * valid index from the memberAssessmentDates ArrayList (.size() - 2).
     * <p>
     * If the result is greater than 0, the member has gained weight.
     * A message is displayed to the console with the weight gain.
     * <p>
     * If the result is less than 0, the member has lost weight.
     * A message is displayed to the console with the weight loss.
     * <p>
     * If the size of the memberAssessmentDates ArrayList is equal to 1 (1 assessment has been completed),
     * a comparison is made by getting the assessment completed
     * (using the sortedAssessmentDates().last() call from the SortedSet) with the member start weight (obtained
     * by using the getWeight() method from the member class).
     * <p>
     * If the result is greater than 0, the member has gained weight.
     * A message is displayed to the console with the weight gain.
     * <p>
     * If the result is less than 0, the member has lost weight.
     * A message is displayed to the console with the weight loss.
     */

    private void viewProgressByWeight() {

        double changeInWeight = 0;
        Member member = loggedInMember;
        ArrayList<String> memberAssessmentDates = new ArrayList<String>();
        if (member.getAssessments().size() != 0) {
            for (String assessmentDates : member.sortedAssessmentDates()) {
                System.out.println("\nAssessment Date: " + assessmentDates + " " + String.format("%.2f", member.getAssessments().get(assessmentDates).getWeight()) + "kgs");
                memberAssessmentDates.add(assessmentDates);
            }
            if (memberAssessmentDates.size() > 1) {
                changeInWeight = member.getAssessments().get(member.sortedAssessmentDates().last()).getWeight() -
                        member.getAssessments().get(memberAssessmentDates.get(memberAssessmentDates.size() - 2)).getWeight();

                if (changeInWeight > 0) {
                    System.out.println("\nUnfortunately, you have gained " + String.format("%.2f", changeInWeight) + "kgs since the last assessment.\n");
                } else if (changeInWeight < 0) {
                    System.out.println("\nWell done! You have lost " + String.format("%.2f", changeInWeight * -1) + "kgs since the last assessment.\n");
                }
            } else if (memberAssessmentDates.size() == 1) {
                changeInWeight = member.getAssessments().get(member.sortedAssessmentDates().last()).getWeight() - member.getStartWeight();
                if (changeInWeight > 0) {
                    System.out.println("Start Weight: " + String.format("%.2f", member.getStartWeight()) + "kgs");
                    System.out.println("\nUnfortunately, you have gained " + String.format("%.2f", changeInWeight) + "kgs since registering.\n");
                } else if (changeInWeight < 0) {
                    System.out.println("Start Weight: " + String.format("%.2f", member.getStartWeight()) + "kgs");
                    System.out.println("\nWell done! You have lost " + String.format("%.2f", (changeInWeight * -1)) + "kgs since registering.\n");
                }
            }
            System.out.println("\nPress any key to continue...");
            input.nextLine();
            input.nextLine();
            try {
                progressSubMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in progress sub menu: " + e);
            }

        } else {
            System.out.println("\nNo assessments have been completed yet.\n");
            try {
                progressSubMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in progress sub menu: " + e);
            }
        }

    }

    /**
     * The viewProgressByWaist() method allows the member to see their change in waist measurement over time.
     * <p>
     * If no assessments have been completed for the member, 'No assessments have been completed yet' is printed
     * to the console and the loggedInMemberMenu() method is called which displays the logged in member menu.
     * <p>
     * If assessments have been completed, a for loop is used to loop through the sorted assessment dates (using the
     * sortedAssessmentDates() method from the member class).
     * The member's assessment waist measurements are printed to the console in order (by using the getAssessments() method
     * from the member class to get the hashmap with the assessments with each sorted assessment date passed
     * (key search using the .get() call). These are added to the memberAssessmentDates ArrayList (in order).
     * <p>
     * If the size of the memberAssessmentDates ArrayList is greater than 1 (More than 1 assessment has been completed),
     * a comparison is made by getting the latest assessment
     * (using the sortedAssessmentDates().last() call from the SortedSet) with the second last assessment (using the
     * valid index from the memberAssessmentDates ArrayList (.size() - 2).
     * <p>
     * If the result is greater than 0, the member's waist has expanded.
     * A message is displayed to the console with the size of the expansion.
     * <p>
     * If the result is less than 0, the member's waist size has reduced.
     * A message is displayed to the console with the size of the reduction.
     * <p>
     * If the size of the memberAssessmentDates ArrayList is equal to 1 (1 assessment has been completed),
     * the assessment waist measurement is displayed with a message informing the user that only 1 assessment
     * has been completed (so a comparison cannot be undertaken).
     */

    private void viewProgressByWaist() {
        Member member = loggedInMember;
        ArrayList<String> memberAssessmentDates = new ArrayList<String>();
        double changeInWaist = 0;

        if (member.getAssessments().size() != 0) {
            for (String assessmentDates : member.sortedAssessmentDates()) {
                System.out.println("\nAssessment Date: " + assessmentDates + " " + String.format("%.2f", member.getAssessments().get(assessmentDates).getWaist()) + " inches.");
                memberAssessmentDates.add(assessmentDates);
            }
            if (memberAssessmentDates.size() > 1) {
                changeInWaist = member.getAssessments().get(member.sortedAssessmentDates().last()).getWaist() -
                        member.getAssessments().get(memberAssessmentDates.get(memberAssessmentDates.size() - 2)).getWaist();

                if (changeInWaist > 0) {
                    System.out.println("\nUnfortunately, your waist has increased by " + String.format("%.2f", changeInWaist) + " inches since the last assessment.\n");
                } else if (changeInWaist < 0) {
                    System.out.println("\nWell done! Your waist size has reduced by " + String.format("%.2f", (changeInWaist * -1)) + " inches since the last assessment.\n");
                }
                System.out.println("\nPress any key to continue...");
                input.nextLine();
                input.nextLine();
                try {
                    progressSubMenu();
                } catch (Exception e) {
                    System.out.println("Error loading logged in progress sub menu: " + e);
                }
            } else if (memberAssessmentDates.size() == 1) {
                System.out.println("Only 1 assessment has been completed. Check back later when another assessment has " +
                        "been completed to see any change in your waist size.");

                System.out.println("\nPress any key to continue...");
                input.nextLine();
                input.nextLine();
                try {
                    progressSubMenu();
                } catch (Exception e) {
                    System.out.println("Error loading logged in progress sub menu: " + e);
                }
            }


        } else {
            System.out.println("\nNo assessments have been completed yet.\n");
            try {
                loggedInMemberMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in member menu: " + e);
            }
        }
    }

    /**
     * The viewProgressByThigh() method allows the member to see their change in thigh size over time.
     * <p>
     * If no assessments have been completed for the member, 'No assessments have been completed yet' is printed
     * to the console and the loggedInMemberMenu() method is called which displays the logged in member menu.
     * <p>
     * If assessments have been completed, a for loop is used to loop through the sorted assessment dates (using the
     * sortedAssessmentDates() method from the member class).
     * The member's assessment thigh measurements are printed to the console in order (by using the getAssessments() method
     * from the member class to get the hashmap with the assessments with each sorted assessment date passed
     * (key search using the .get() call). These are added to the memberAssessmentDates ArrayList (in order).
     * <p>
     * If the size of the memberAssessmentDates ArrayList is greater than 1 (More than 1 assessment has been completed),
     * a comparison is made by getting the latest assessment
     * (using the sortedAssessmentDates().last() call from the SortedSet) with the second last assessment (using the
     * valid index from the memberAssessmentDates ArrayList (.size() - 2).
     * <p>
     * If the result is greater than 0, the member's thigh size has increased.
     * A message is displayed to the console with the size of the expansion.
     * <p>
     * If the result is less than 0, the member's thigh size has reduced.
     * A message is displayed to the console with the size of the reduction.
     * <p>
     * If the size of the memberAssessmentDates ArrayList is equal to 1 (1 assessment has been completed),
     * the assessment thigh measurement is displayed with a message informing the user that only 1 assessment
     * has been completed (so a comparison cannot be undertaken).
     */

    private void viewProgressByThigh() {
        Member member = loggedInMember;
        ArrayList<String> memberAssessmentDates = new ArrayList<String>();
        double changeInThigh = 0;

        if (member.getAssessments().size() != 0) {
            for (String assessmentDates : member.sortedAssessmentDates()) {
                System.out.println("\nAssessment Date: " + assessmentDates + " " + String.format("%.2f", member.getAssessments().get(assessmentDates).getThigh()) + " inches");
                memberAssessmentDates.add(assessmentDates);
            }
            if (memberAssessmentDates.size() > 1) {
                changeInThigh = member.getAssessments().get(member.sortedAssessmentDates().last()).getThigh() -
                        member.getAssessments().get(memberAssessmentDates.get(memberAssessmentDates.size() - 2)).getThigh();

                if (changeInThigh > 0) {
                    System.out.println("\nUnfortunately, your thigh measurement has increased by " + String.format("%.2f", changeInThigh) + " inches since the last assessment.\n");
                } else if (changeInThigh < 0) {
                    System.out.println("\nWell done! Your thigh size has reduced by " + String.format("%.2f", (changeInThigh * -1)) + " inches since the last assessment.\n");
                }
                System.out.println("\nPress any key to continue...");
                input.nextLine();
                input.nextLine();
                try {
                    progressSubMenu();
                } catch (Exception e) {
                    System.out.println("Error loading logged in progress sub menu " + e);
                }
            } else if (memberAssessmentDates.size() == 1) {
                System.out.println("Only 1 assessment has been completed. Check back later when another assessment has" +
                        "been completed to see any change in your thigh size.");

                System.out.println("\nPress any key to continue...");
                input.nextLine();
                input.nextLine();
                try {
                    progressSubMenu();
                } catch (Exception e) {
                    System.out.println("Error loading logged in progress sub menu: " + e);
                }
            }


        } else {
            System.out.println("\nNo assessments have been completed yet.\n");
            try {
                loggedInMemberMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in member menu: " + e);
            }
        }
    }

    /**
     * Allows members to search for trainers by inputting an email.
     * Uses the searchTrainersByEmail() method from the GymAPI class.
     * If this call does not return null, the member returned from this method is outputted to the console.
     * <p>
     * If this call does return null, a message is outputted to the console indicating that there are no matches
     * for the entered email.
     */


    private void searchTrainersByEmail() {

        //dummy read of String to clear the buffer - bug in Scanner class.
        input.nextLine();
        System.out.print("\nSearch By Email:  ");
        String emailEntered = input.nextLine();
        if (gymAPI.searchTrainersByEmail(emailEntered) != null) {
            System.out.println("\n" + gymAPI.searchTrainersByEmail(emailEntered) + "\n");
        } else {
            System.out.println("\nNo trainers found for the inputted email.\n");
        }
        try {
            loggedInMemberMenu();
        } catch (Exception e) {
            System.out.println("Error loading logged in member menu: " + e);
        }

    }


    /**
     * Allows members to search for trainers by inputting a name.
     * Uses the searchTrainersByName() method from the GymAPI class.
     * If this call does not return an empty ArrayList, the matches (partial or full) are looped through and added to the
     * printNameMatches String which is outputted to the console.
     * <p>
     * If this call returns an empty ArrayList, a message is outputted to the console indicating that there are no matches
     * for the entered name.
     */

    private void searchTrainersByName() {

        //dummy read of String to clear the buffer - bug in Scanner class.
        input.nextLine();
        String printNameMatches = "";
        System.out.print("\nSearch By Name:  ");
        String nameEntered = input.nextLine();
        ArrayList<String> nameMatches = gymAPI.searchTrainersByName(nameEntered);

        if (nameMatches.size() != 0) {
            System.out.println("\nMatches found for name entered\n");
            for (int i = 0; i < nameMatches.size(); i++) {
                printNameMatches += nameMatches.get(i) + "\n";
            }
            System.out.println(printNameMatches);
        } else {
            System.out.println("\nNo matches found for name entered\n");

        }
        try {
            loggedInMemberMenu();
        } catch (Exception e) {
            System.out.println("Error loading logged in member menu: " + e);
        }
    }

    /**
     * The loggedInTrainerMenu() method prints out the options available for the logged in trainer.
     * The user is prompted to enter an option.
     * <p>
     * If they enter 1 (Add a new member), the addMember() method is called.
     * <p>
     * If they enter 2 (List all members (Full Account Details)), all details of registered members are printed to the console.
     * All members are obtained by calling the listMembers() method in the GymAPI class, looping through the
     * contents (ArrayList of type Member) and adding each member to the String that is outputted to the console.
     * <p>
     * If they enter 3 (List member height and latest assessment weight (Metric & Imperial Format)), the listMemberDetailsImperialAndMetric()
     * method is called from the GymAPI class.
     * <p>
     * If they enter 4 (List members with ideal weight), the listMembersWithIdealWeight() method is called.
     * <p>
     * If they enter 5 (Search for a member by Email), the searchMemberByEmail() method is called.
     * <p>
     * If they enter 6 (Search for a member by Name), the searchMemberByName() method is called.
     * <p>
     * If they enter 7 (Progress sub-menu), the assessmentSubMenu() method is called.
     * <p>
     * <p>
     * If they enter 0 (Return to Main Menu), the saveData() method (saves data to 'userData.xml') and
     * the exitProgramme() method is called which exits the programme.
     */


    //sub menu for logged in trainers
    private void loggedInTrainerMenu() {
        boolean goodInput = false;
        int menuChoice = 0;
        System.out.println("\nLogged In");
        System.out.println("---------");
        System.out.println("  1) Add a new member");
        System.out.println("  2) List all members (Full Account Details)");
        System.out.println("  3) List member height and latest assessment weight (Metric & Imperial Format)");
        System.out.println("  4) List members with ideal weight");
        System.out.println("  5) Search for a member by Email");
        System.out.println("  6) Search for a member by Name");
        System.out.println("  7) Assessment sub-menu");
        System.out.println("  0) Exit Programme");

        while (!goodInput) {
            try {
                System.out.print("==>> ");
                menuChoice = input.nextInt();
                goodInput = true;
                //return menuChoice;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input - please enter an integer value.");
            }
        }


        while (menuChoice != 0) {

            switch (menuChoice) {
                case 1:
                    try {
                        addMember();
                    } catch (Exception e) {
                        System.out.println("Error loading add member method: " + e);
                    }
                    try {
                        loggedInTrainerMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading logged in trainer menu: " + e);
                    }
                    break;
                case 2:
                    String listAllMembers = "";
                    for (int i = 0; i < gymAPI.listMembers().size(); i++) {
                        listAllMembers += "\n" + i + ") " + gymAPI.listMembers().get(i);
                    }
                    System.out.println(listAllMembers);
                    break;
                case 3:
                    System.out.println(gymAPI.listMemberDetailsImperialAndMetric());
                    break;
                case 4:
                    try {
                        listMembersWithIdealWeight();
                    } catch (Exception e) {
                        System.out.println("Error running listMembersWithIdealWeight method: " + e);
                    }
                    break;
                case 5:
                    try {
                        searchMemberByEmail();
                    } catch (Exception e) {
                        System.out.println("Error running searchMemberByEmail method: " + e);
                    }
                    break;
                case 6:
                    try {
                        searchMemberByName();
                    } catch (Exception e) {
                        System.out.println("Error running searchMemberByName method: " + e);
                    }
                    break;
                case 7:
                    try {
                        assessmentSubMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading assessment subMenu (Trainers): " + e);
                    }
                    break;
                default:
                    System.out.println("Invalid option entered: " + menuChoice);
                    break;
            }

            //pause the program so that the user can read what we just printed to the terminal window
            System.out.println("\nPress any key to continue...");
            input.nextLine();
            input.nextLine();  //this second read is required - bug in Scanner class; a String read is ignored straight after reading an int.

            //display the main menu again
            try {
                loggedInTrainerMenu();
            } catch (Exception e) {
                System.out.println("Error loading logged in trainer menu: " + e);
                input.nextLine();
                System.out.print("==>> ");
                menuChoice = input.nextInt();

            }
        }
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data to xml file: " + e);
        }
        try {
            exitProgramme();
        } catch (Exception e) {
            System.out.println("Error exiting programme: " + e);
        }
    }

    /**
     * The addMember() method is used to register a new member.
     * <p>
     * The user is prompted to enter the email address of the new member.
     * If the email has already been used with another account, an error message is outputted to the console and the
     * user is prompted to enter another email (until they provide an email that has not been registered with another
     * account).
     * <p>
     * The user is prompted to enter a new name.
     * If the length of the new name is greater than 30 characters, it is truncated at index position 30.
     * <p>
     * The user is prompted to enter their address.
     * <p>
     * The user is prompted to enter their gender. If the first character inputted is 'f' or 'F' or 'm' or 'M',
     * the genderPrefix is set to the value inputted. If something else is inputted, genderPrefix is set to
     * 'Unspecified'.
     * <p>
     * The user is prompted to enter their height. If the height inputted is between 1 and 3, it is valid.
     * Otherwise, the user is prompted until they provide an input value between 1 and 3.
     * <p>
     * The user is prompted to enter their weight. If they weight inputted is between 35 and 250, it is valid.
     * Otherwise, the user is prompted until they provide an input value between 35 and 250.
     * <p>
     * The user is prompted to select if they want a premium or student package.
     * <p>
     * If the user does not enter 'p' or 'P' for Premium or 's' or 'S' for Student, an error message is printed
     * to the console. The user is then prompted again until they provide valid input.
     * <p>
     * If the user chose a premium package, a message is printed to the console informing the user what packages are
     * available with the benefits in each package.
     * The user is then prompted to select a package ('Package 1 || 'Package 2').
     * If they do not provide valid input, an error message is printed to the console and they are prompted
     * to provide valid input (repeated until correct option is chosen).
     * <p>
     * Once a valid premium package has been chosen, the user is prompted to confirm that they are happy to proceed.
     * If they enter 'y' or 'Y', the addMember() method from the GymAPI class is used to add a new member of type
     * premium member.
     * If they enter 'n' or 'N', the registration is abandoned and the addMember() method is called (to restart the
     * registration).
     * <p>
     * If the user chose a student package, a message is printed to the console informing the user what packages
     * are available with the benefits in each package.
     * The user is then prompted to select a package ('WIT' || 'Package 3' (for students from other colleges)).
     * If they do not provide valid input, an error message is printed to the console and they are prompted to
     * provide valid input (repeated until correct option is chosen).
     * <p>
     * If the user inputs 'WIT' (and they indicate that they are happy to proceed (by entering 'y' or 'Y')
     * they are then prompted to enter their student number. The addMember() method from the GymAPI class is used
     * to add a new member of type student.
     * <p>
     * If the user inputs 'Package 3' (and indicates that they are happy to proceed (by entered 'y' or 'Y')
     * they are prompted to enter their college name and student number. The addMember() method from the GymAPI class
     * is used to add a new member of type student.
     * <p>
     * If they enter 'n' or 'N' (not happy to proceed with chosen package), the registration is abandoned
     * and the addMember() method is called (to restart the registration.
     * <p>
     * The saveData() method is called to save the data (using xStream) to the 'userData.xml' file.
     * <p>
     * The startUpMenu() method is called to display the main menu (INVESTIGATE FURTHER).
     */

    private void addMember() {
        boolean correctHeight = false;
        boolean correctWeight = false;
        boolean correctMemberType = false;
        boolean correctPremiumPackage = false;
        boolean correctStudentPackage = false;
        boolean goodInputHeight = false;
        boolean goodInputWeight = false;
        String memberType = "";
        String chosenPackage = "";
        String proceedWithRegistration = "";
        String genderPrefix = "";

        //dummy read of String to clear the buffer - bug in Scanner class.
        input.nextLine();
        while (emailAlreadyRegistered == true) {
            System.out.print("\nEmail:  ");
            emailInputted = input.nextLine();
            if ((gymAPI.searchMembersByEmail(emailInputted) == null) && gymAPI.searchTrainersByEmail(emailInputted) == null) {
                emailAlreadyRegistered = false;
            } else {
                System.out.println("\nInvalid Email. Account already exists with this email");
            }
        }

        System.out.print("Name: ");
        String name = input.nextLine();
        if (name.length() > 30) {
            name = name.substring(0, 30);
        }
        System.out.print("Address:  ");
        String address = input.nextLine();


        System.out.print("Gender:  ");
        gender = input.nextLine().toUpperCase();
        if (gender.charAt(0) == 'M' || gender.charAt(0) == 'F') {

            genderPrefix = gender.substring(0, 1).toUpperCase();
        } else {
            genderPrefix = "Unspecified";
        }


        while (!correctHeight == true) {
            while (!goodInputHeight) {
                try {
                    System.out.print("Height:  ");
                    height = input.nextFloat();
                    goodInputHeight = true;
                    //return menuChoice;
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Invalid input - please enter a numeric value.");
                }
            }
            if (height >= 1 && height <= 3) {
                correctHeight = true;
            } else {
                System.out.println("Invalid Height. Height must be between 1 and 3.");
                goodInputHeight = false;
            }
        }
        while (!correctWeight) {
            while (!goodInputWeight) {
                try {
                    System.out.print("Weight:  ");
                    startWeight = input.nextFloat();
                    goodInputWeight = true;
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Invalid input - please enter a numeric value.");
                }
            }
            if (startWeight >= 35 && startWeight <= 250) {
                correctWeight = true;
            } else {
                System.out.println("Invalid starting weight. Weight must be between 35 and 250.");
                goodInputWeight = false;
            }
        }
        input.nextLine();
        System.out.println("Do you want to register a premium member or a student member (student ID number required)?");
        while (!correctMemberType) {

            System.out.print("==>>  ");

            memberType = input.nextLine();
            if ((memberType.toUpperCase().charAt(0) == 'P') || (memberType.toUpperCase().charAt(0) == 'S')) {
                correctMemberType = true;
            } else {
                System.out.println("Invalid Input. Please input Premium or Student to proceed.");

            }
        }

        if (memberType.toUpperCase().charAt(0) == 'P') {
            System.out.println("\nPackage 1: Free access to the gym and free classes.\n" +
                    "Package 2: Free access to the gym with €3 fees for classes.\n");
            while (!correctPremiumPackage) {
                System.out.print("Enter the chosen package:  ");
                chosenPackage = input.nextLine();
                String chosenPremiumPackage = gymPackages.get(chosenPackage);
                if (chosenPackage.toUpperCase().equals("PACKAGE 1") || chosenPackage.toUpperCase().equals("PACKAGE 2")) {
                    System.out.println("\n" + chosenPremiumPackage + "\n");
                    correctPremiumPackage = true;
                } else {
                    System.out.println("Incorrect input. Please enter Package 1 or Package 2 to proceed.");
                }
            }


            System.out.println("Are you happy with this package (Y/N)?");
            proceedWithRegistration = input.nextLine();

            if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y') {
                gymAPI.addMember(new PremiumMember(emailInputted, name, address,
                        genderPrefix, height, startWeight, chosenPackage));
                System.out.println("\nSuccessfully registered a premium member!\n");
                emailAlreadyRegistered = true;
            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'N') {
                System.out.print("\nRegistration Abandoned. Please enter your details again to register as a member.\n");
                System.out.println("Please press any key to recommence the member registration process.");
                System.out.print("==>> ");
                emailAlreadyRegistered = true;
                try {
                    addMember();
                } catch (Exception e) {
                    System.out.println("Error loading addMember method: " + e);
                }
            }


        } else if (memberType.toUpperCase().charAt(0) == 'S') {
            System.out.println("\nWIT: Choose the WIT package if you are a student at the college. It allows access to the gym during term time. €4 fee per class.\n" +
                    "Package 3: Choose package 3 if you are a student at a college other than WIT. Access to the gym off-peak with €5 fee per class.\n");

            while (!correctStudentPackage) {
                System.out.print("Enter the chosen package:  ");
                chosenPackage = input.nextLine();
                String chosenStudentPackage = gymPackages.get(chosenPackage);
                if (chosenPackage.equals("WIT") || chosenPackage.equals("Package 3")) {
                    System.out.println("\n" + chosenStudentPackage + "\n");
                    correctStudentPackage = true;
                } else {
                    System.out.println("Incorrect input. Please enter WIT or Package 3 to proceed.");
                }
            }

            System.out.println("Are you happy with this package (Y/N)?");
            proceedWithRegistration = input.nextLine();

            if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y' && chosenPackage.toUpperCase().equals("WIT")) {
                String collegeName = "WIT";
                System.out.print("Enter the Student Number:  ");
                String studentNumber = input.nextLine();
                gymAPI.addMember(new StudentMember(emailInputted, name, address,
                        genderPrefix, height, startWeight, chosenPackage, studentNumber, collegeName));
                System.out.println("\nSuccessfully registered a WIT student member!\n");
                emailAlreadyRegistered = true;
            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'Y' && chosenPackage.toUpperCase().equals("PACKAGE 3")) {
                System.out.print("Enter the College Name:  ");
                String collegeName = input.nextLine();
                System.out.print("Enter the Student Number:  ");
                String studentNumber = input.nextLine();
                gymAPI.addMember(new StudentMember(emailInputted, name, address,
                        genderPrefix, height, startWeight, chosenPackage, studentNumber, collegeName));
                System.out.println("\nSuccessfully registered a student member!\n");
                emailAlreadyRegistered = true;
            } else if (proceedWithRegistration.toUpperCase().charAt(0) == 'N') {
                System.out.print("\nRegistration Abandoned. Please enter your details again to register as a member.\n");
                System.out.println("Please press any key to recommence the member registration process.");
                System.out.print("==>> ");
                emailAlreadyRegistered = true;
                try {
                    addMember();
                } catch (Exception e) {
                    System.out.println("Error running addMember method: " + e);
                }
            }
        }


        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data to file: " + e);
        }
        try {
            goodInput = false;
            startUpMenu();
        } catch (Exception e) {
            System.out.println("Error loading startupMenu: " + e);
        }

    }

    /**
     * The addTrainer() method allows users to register as a trainer.
     * <p>
     * If the email entered by the user has already been registered, the user is prompted to enter a different email.
     * <p>
     * The trainer provides a name, gender (if first character entered is 'm' or 'M' || 'f' or 'F' gender is set to
     * this input. If something else is inputted, gender is set to 'Unspecified'.
     * <p>
     * The trainer provides their specialty.
     * <p>
     * The addTrainer() method is used from the GymAPI class to add a new Trainer.
     * <p>
     * The saveData() method is used to save the new information to the 'userData.xml' file (using xstream).
     */


    //allow the user to register
    private void addTrainer() {
        String email = "";
        String genderPrefix = "";
        //dummy read of String to clear the buffer - bug in Scanner class.
        input.nextLine();
        while (emailAlreadyRegistered == true) {
            System.out.print("Enter your email:  ");
            email = input.nextLine();
            if ((gymAPI.searchTrainersByEmail(email) == null) && (gymAPI.searchMembersByEmail(email) == null)) {
                emailAlreadyRegistered = false;
            } else {
                System.out.println("Invalid Email. Account already exists with this email");
            }
        }
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter your address:  ");
        String address = input.nextLine();

        System.out.print("Enter your gender:  ");
        gender = input.nextLine().toUpperCase();
        if (gender.charAt(0) == 'M' || gender.charAt(0) == 'F') {

            genderPrefix = gender.substring(0, 1).toUpperCase();
        } else {
            genderPrefix = "Unspecified";
        }

        System.out.print("Enter your specialty:  ");
        String specialty = input.nextLine();

        gymAPI.addTrainer(new Trainer(email, name, address,
                genderPrefix, specialty));
        System.out.println("\nSuccessfully registered as Trainer!\n");
        emailAlreadyRegistered = true;
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data to file: " + e);
        }
        try {
            startUpMenu();
        } catch (Exception e) {
            System.out.println("Error loading start up menu: " + e);
        }
    }

    /**
     * Allows trainers to list all members with the ideal weight.
     * Uses the listMembersWithIdealWeight() method from the GymAPI class.
     * If this call does not return an empty ArrayList, it is looped through and each member with an ideal weight
     * is added to the printMembersWithIdealWeight String which is then outputted to the console.
     * <p>
     * If this call returns an empty ArrayList (no members are at the ideal weight),
     * a message is outputted to the console indicating that there are no members at the ideal weight.
     */

    private void listMembersWithIdealWeight() {
        //dummy read of String to clear the buffer - bug in Scanner class.
        input.nextLine();
        String printMembersWithIdealWeight = "";
        ArrayList<Member> membersWithIdealWeight = gymAPI.listMembersWithIdealWeight();

        if (membersWithIdealWeight.size() != 0) {
            System.out.println("\nMembers with ideal weight\n");
            for (int i = 0; i < membersWithIdealWeight.size(); i++) {
                printMembersWithIdealWeight += membersWithIdealWeight.get(i) + "\n";
            }
            System.out.println(printMembersWithIdealWeight);
        } else {
            System.out.println("\nNo members are at their respective ideal weights.\n");

        }

        //loggedInTrainerMenu();
    }

    /**
     * Allows trainers to search for members by inputting an email.
     * Uses the searchMembersByEmail() method from the GymAPI class.
     * If this call does not return null, the member returned from this method is outputted to the console.
     * <p>
     * If this call does return null, a message is outputted to the console indicating that there are no matches
     * for the entered email.
     */
    private void searchMemberByEmail() {
        //dummy read of String to clear the buffer - bug in Scanner class.
        input.nextLine();
        System.out.print("Search By Email:  ");
        String emailEntered = input.nextLine();
        if (gymAPI.searchMembersByEmail(emailEntered) != null) {
            System.out.println("\n" + gymAPI.searchMembersByEmail(emailEntered) + "\n");
        } else {
            System.out.println("\nNo members found for the inputted email.");
        }
        try {
            loggedInTrainerMenu();
        } catch (Exception e) {
            System.out.println("Error loading logged in trainer menu: " + e);
        }
    }

    /**
     * Allows trainers to search for members by inputting a name.
     * Uses the searchMembersByName() method from the GymAPI class.
     * If this call does not return an empty ArrayList, the matches (partial or full) are looped through and added to the
     * printNameMatches String which is outputted to the console.
     * <p>
     * If this call returns an empty ArrayList, a message is outputted to the console indicating that there are no matches
     * for the entered name.
     */


    private void searchMemberByName() {
        //dummy read of String to clear the buffer - bug in Scanner class.
        input.nextLine();
        String printNameMatches = "";
        System.out.print("Search By Name:  ");
        String nameEntered = input.nextLine();
        ArrayList<String> nameMatches = gymAPI.searchMembersByName(nameEntered);

        if (nameMatches.size() != 0) {
            System.out.println("\nMatches found for name entered\n");
            for (int i = 0; i < nameMatches.size(); i++) {
                printNameMatches += nameMatches.get(i) + "\n";
            }
            System.out.println(printNameMatches);
        } else {
            System.out.println("\nNo matches found for name entered\n");

        }

        //loggedInTrainerMenu();
    }

    /**
     * The assessmentSubMenu() method is used to display a sub menu for trainers.
     * Options:
     * 1 - (Add an assessment for a member) addAssessment() method called.
     * 2 - (Update Comment on User Assessment) updateAssessmentComment() method called.
     * 3 - (Search for all members with a specified BMI category) listMembersBySpecificBMICategory() method called.
     * 0 - (Return to Main Menu) loggedInTrainerMenu() method called
     */


    private void assessmentSubMenu() {
        boolean validInput = false;
        int menuChoice = 0;
        System.out.println("\nProgress Sub-Menu");
        System.out.println("---------");
        System.out.println("  1) Add an assessment for a member");
        System.out.println("  2) Update Comment On User Assessment");
        System.out.println("  3) Search for all members with a specified BMI category.");
        System.out.println("  0) Return to Main Menu");
        while (!validInput) {
            //input.nextLine();
            System.out.print("==>> ");
            menuChoice = input.nextInt();

            if (menuChoice >= 0 && menuChoice <= 3) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please input a valid option.");
            }
        }

        while (menuChoice != 0) {

            switch (menuChoice) {
                case 1:
                    try {
                        addAssessment();
                        validInput = false;
                    } catch (Exception e) {
                        System.out.println("Error loading add Assessment method: " + e);
                    }
                    try {
                        assessmentSubMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading assessment sub menu: " + e);
                    }
                    break;
                case 2:
                    try {
                        updateAssessmentComment();
                        validInput = false;
                    } catch (Exception e) {
                        System.out.println("Error loading method to update assessment comment: " + e);
                    }
                    break;
                case 3:
                    try {
                        listMembersBySpecificBMICategory();
                        validInput = false;
                    } catch (Exception e) {
                        System.out.println("Error running listMembersBySpecificBMICategory method: " + e);
                    }
                    break;
            }
        }
        try {
            loggedInTrainerMenu();
        } catch (Exception e) {
            System.out.println("Error loading logged in trainer menu: " + e);
        }
    }

    /**
     * This method allows trainers to add an assessment for a member.
     * <p>
     * All registered members are printed to the console (with their index position) using a for loop with
     * the getMembers() method from the GymAPI class.
     * <p>
     * The trainer is prompted to enter the index position of the member for which they want to add an
     * assessment.
     * <p>
     * If the index provided is not in range, an error message is printed to the console and the trainer is
     * prompted again to enter a valid index (until they provide a valid index).
     * <p>
     * Once a valid index is provided, the trainer is prompted to enter the following:
     * Assessment weight
     * Assessment thigh size
     * Assessment waist size
     * Assessment comment
     * <p>
     * The getMembers() method from the GymAPI class is then used to get the relevant member (using the index
     * position provided by the trainer.
     * <p>
     * The assessment date is captured and correctly formatted (yy/MM/dd).
     * <p>
     * An object of type Assessment is then declared with the weight, thigh, waist, comment and trainer name values
     * passed as parameters.
     * <p>
     * The getAssessments() method from the Member class is then called to obtain the assessment hashmap.
     * The formatted date is passed as the key and the Assessment object is passed as the value (using .put()).
     * <p>
     * All assessments completed for the member are then printed to the console (obtained using a for loop
     * with the sortedAssessmentDates() method (sortedSet) from the Member class to loop through the assessments hashmap,
     * passing each assessment date as a key search using the .get() method).
     */

    private void addAssessment() {
        boolean validInput = false;
        String listAllMembers = "";
        for (int i = 0; i < gymAPI.getMembers().size(); i++) {
            listAllMembers += "\n" + i + ") " + gymAPI.getMembers().get(i);
        }
        //System.out.println(gymAPI.listMembers());

        if (gymAPI.getMembers().size() > 0) {
            System.out.println("Current Members");
            System.out.println("---------");
            System.out.println(listAllMembers);
            input.nextLine();
            int index = 0;
            while (!validInput) {
                System.out.print("\nEnter the index of the member account to update ==> ");
                index = input.nextInt();

                if ((index >= 0) && (index < gymAPI.getMembers().size())) {
                    validInput = true;
                } else {
                    System.out.println("\nInvalid input. Please select an index that is in range.");
                }
            }
            //dummy read of String to clear the buffer - bug in Scanner class.
            input.nextLine();
            System.out.print("Enter the member's weight:  ");
            float weight = input.nextFloat();

            System.out.print("Enter the thigh dimension:  ");
            float thigh = input.nextFloat();

            System.out.print("Enter the waist dimensions:  ");
            float waist = input.nextFloat();
            input.nextLine();
            System.out.print("Enter the comment that you want to add:  ");
            String comment = input.nextLine();

            Member member = gymAPI.getMembers().get(index);

            Format dateFormat = new SimpleDateFormat("yy/MM/dd");
            strDate = dateFormat.format(new Date());
            Assessment assessment = new Assessment(weight, thigh, waist, comment, loggedInTrainer.getName());
            member.getAssessments().put(strDate, assessment);

            for (String assessmentDates : member.sortedAssessmentDates()) {
                System.out.println("\nAssessment Date: " + assessmentDates + " " + member.getAssessments().get(assessmentDates));
            }
            try {
                saveData();
            } catch (Exception e) {
                System.out.println("Error saving data: " + e);
            }
            try {
                loadData();
            } catch (Exception e) {
                System.out.println("Error loading data from file: " + e);
            }
        } else {
            System.out.println("\nThere are no registered members.");
        }
    }

    /**
     * This method allows trainers to update the comment on a member assessment.
     * <p>
     * All registered members are printed to the console (with their index position) using a for loop with
     * the getMembers() method from the GymAPI class.
     * <p>
     * The trainer is prompted to enter the index position of the member for which they want to update an assessment
     * comment.
     * <p>
     * If the index provided is not in range, an error message is printed to the console and the trainer is
     * prompted again to enter a valid index (until they provide a valid index).
     * <p>
     * Once a valid index is provided, the getMembers() method from the GymAPI class is then used
     * to get the relevant member using the index position provided by the trainer.
     * <p>
     * A check is undertaken to see if any assessments have been recorded for the member (by using
     * the getAssessments() method from the Member class, checking the size).
     * <p>
     * If the size is 0, a message is outputted to the console informing the trainer that no assessments
     * have been completed for the chosen member.
     * <p>
     * If the size is greater than 0, all assessments completed for the member are then printed to the console
     * (obtained using a for loop with the sortedAssessmentDates() method (sortedSet) from the Member class
     * to loop through the assessments hashmap, passing each assessment date as a key search using the .get() method).
     * <p>
     * The trainer is then prompted to enter the date of the assessment that they want to update.
     * If the date inputted is not valid, they are prompted again to provide a valid date (until they provide a
     * valid date). This check is completed using the getAssessment() method and the .get() call, passing
     * the String inputted by the user as a key search. If null is not returned, a valid date has been
     * inputted.
     * <p>
     * An object of Assessment type is then declared that is set to the specified assessment (as returned from
     * the hashmap using the getAssesments() method from the member class, passing the assessment date as a key
     * search.
     * <p>
     * The trainer is prompted to enter the updated comment.
     * The Assessment object is updated using the setComments() method.
     * <p>
     * The updated assessment is then logged to the console.
     * The saveData() method is called to save the data to the 'userData.xml' file (using xstream).
     * The loadData() method is called to load the data from the 'userData.xml' file (using xstream) so that
     * update info is available during programme runtime.
     * <p>
     * The assessmentSubMenu() method is then called to display the sub menu to the Trainer.
     */

    private void updateAssessmentComment() {
        boolean validInput = false;
        boolean validInputInteger = false;
        boolean validAssessmentDate = false;
        String assessmentChosen = "";

        String listAllMembers = "";
        if (gymAPI.getMembers().size() > 0) {
            for (int i = 0; i < gymAPI.getMembers().size(); i++) {
                listAllMembers += "\n" + i + ") " + gymAPI.getMembers().get(i);
            }
            System.out.println("Current Members");
            System.out.println("---------");
            System.out.println(listAllMembers);
            System.out.println("Enter the index of the member account to update ==> ");
            int index = 0;
            while (!validInput) {
                while (!validInputInteger) {
                    try {
                        System.out.print("\nEnter the index of the member account to update ==> ");
                        index = input.nextInt();
                        validInputInteger = true;
                    } catch (Exception e) {
                        input.nextLine();
                        System.out.println("Invalid input: Please enter a number to proceed.");
                    }
                }

                if ((index >= 0) && (index < gymAPI.getMembers().size())) {
                    validInput = true;
                } else {
                    System.out.println("\nInvalid input. Please select an index that is in range.");
                    validInputInteger = false;
                }
            }


            if ((index >= 0) && (index < gymAPI.getMembers().size())) {
                Member member = gymAPI.getMembers().get(index);
                int indexPosition = 0;
                if (member.getAssessments().size() != 0) {
                    for (String assessmentDates : member.sortedAssessmentDates()) {
                        System.out.println(indexPosition + ") Assessment Date: " + assessmentDates + " " + member.getAssessments().get(assessmentDates));

                        indexPosition++;
                    }

                    input.nextLine();
                    while (!validAssessmentDate) {
                        System.out.print("\nEnter the date of the assessment that is to be updated ==> ");
                        assessmentChosen = input.nextLine();

                        if (member.getAssessments().get(assessmentChosen) != null) {
                            validAssessmentDate = true;
                        } else {
                            System.out.println("\nInvalid Date Inputted. Please input a correct assessment date.");
                        }
                    }
                    Assessment assessment = member.getAssessments().get(assessmentChosen);

                    System.out.print("Enter the updated comment ==> ");

                    String updatedComment = input.nextLine();
                    assessment.setComments(updatedComment);
                    System.out.println("\nSuccessfully updated assessment comment!");
                    System.out.println("\n" + assessment);
                    try {
                        saveData();
                    } catch (Exception e) {
                        System.out.println("Error saving data: " + e);
                    }
                    try {
                        loadData();
                    } catch (Exception e) {
                        System.out.println("Error loading data from file: " + e);
                    }
                    try {
                        assessmentSubMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading asssessment sub menu: " + e);
                    }
                } else {
                    System.out.println("\nThere are no assessments registered for the chosen member.\n");
                    try {
                        assessmentSubMenu();
                    } catch (Exception e) {
                        System.out.println("Error loading assessment sub menu" + e);
                    }

                }
            }
        } else {
            System.out.println("There are no registered members.");
        }
    }

    /**
     * This method allows trainers to search for members by BMI category.
     * <p>
     * If an invalid category is inputted, the trainer is prompted to enter a valid category (until they provide a
     * valid category).
     * <p>
     * BMI categories: SEVERELY UNDERWEIGHT, UNDERWEIGHT, NORMAL, OVERWEIGHT, MODERATELY OBESE, SEVERELY OBESE
     * <p>
     * A call is made to the listMembersBySpecificBMICategory() method in the GymAPI class.
     * This returns an ArrayList of Members that match the entered BMI category.
     * <p>
     * If the size of the ArrayList is 0, a message is outputted to the console informing the trainer that no members
     * are at the specified BMI category.
     * <p>
     * If the size of the ArrayList is greater than 0, a loop takes place through the ArrayList to list all members
     * at the specified BMI category.
     */

    public void listMembersBySpecificBMICategory() {
        input.nextLine();
        boolean validBMICategory = false;
        String bmiCategoryMatches = "";
        String bmiCategory = "";

        System.out.println("\nBMI Categories: SEVERELY UNDERWEIGHT, UNDERWEIGHT, NORMAL, OVERWEIGHT, MODERATELY OBESE, SEVERELY OBESE\n");
        while (!validBMICategory) {
            System.out.print("Search By BMI Category:  ");
            bmiCategory = input.nextLine();


            if (bmiCategory.toUpperCase().equals("SEVERELY UNDERWEIGHT") || bmiCategory.toUpperCase().equals("UNDERWEIGHT")
                    || bmiCategory.toUpperCase().equals("NORMAL") || bmiCategory.toUpperCase().equals("OVERWEIGHT") ||
                    bmiCategory.toUpperCase().equals("MODERATELY OBESE") || bmiCategory.toUpperCase().equals("SEVERELY OBESE")) {
                validBMICategory = true;
            } else {
                System.out.println("Invalid BMI category inputted. Please provide a valid BMI category.");
            }
        }
        ArrayList<Member> membersWithSpecifiedBMICategory = gymAPI.listMembersBySpecificBMICategory(bmiCategory.toUpperCase());


        if (membersWithSpecifiedBMICategory.size() != 0) {
            for (int i = 0; i < membersWithSpecifiedBMICategory.size(); i++) {

                bmiCategoryMatches += membersWithSpecifiedBMICategory.get(i) + "\n";

            }
            System.out.println("\nMembers with the " + bmiCategory + " BMI category.");
            System.out.println("--------------------------");
            System.out.println(bmiCategoryMatches);
        } else {
            System.out.println("\nThere are no members at the " + bmiCategory.toUpperCase() + " BMI category.");
        }

        try {
            assessmentSubMenu();
        } catch (Exception e) {
            System.out.println("Error loading assessment sub menu: " + e);
        }


    }

    /**
     * Method to exit the programme (used on logged in member and logged in trainer menus).
     * The saveData() method is called to save data on exiting the programme.
     */

    private void exitProgramme() {

        System.out.println("Exiting... bye");
        try {
            saveData();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e);
        }
        System.exit(0);
    }

    /**
     * Method used to loadData from the 'userData.xml' file (using xstream) on application load and after
     * updating members or trainers in the programme.
     */
    //load data from XML file on application load
    private void loadData() {
        try {
            gymAPI.load();
        } catch (Exception e) {
            System.err.println("Error loading from file: " + e);
        }
    }

    /**
     * Method used to save dat to the 'userData.xml' file (using xstream) on application exit and after
     * updating members or trainers in the programme.
     */

    public void saveData() {
        try {
            gymAPI.store();
        } catch (Exception e) {
            System.err.println("Error saving to file: " + e);
        }
    }


    /**
     * The fillGymResponses() method is used to add all gym packages to the gymPackages hashmap using .put()
     * Key: Package Name
     * Value: Package Contents
     */

    private void fillGymResponses() {
        gymPackages.put("Package 1", "Allowed access anytime to gym.\nFree access to all classes.\nAccess to all changing areas including deluxe changing rooms.");
        gymPackages.put("Package 2", "Allowed access anytime to gym.\n€3 fee for all classes.\nAccess to all changing areas including deluxe changing rooms.");
        gymPackages.put("Package 3", "Allowed access to gym at off-peak times.\n" +
                "€5 fee for all classes. \nNo access to deluxe changing rooms.");
        gymPackages.put("WIT", "Allowed access to gym during term time.\n" +
                "€4 fee for all classes.  \nNo access to deluxe changing rooms.");
    }


}
