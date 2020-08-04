/**
 * This class is a subclass of (extends) member.
 * This class stores no additional information.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class PremiumMember extends Member {
    private String packageChoice;

    /**
     * Constructor for objects of class PremiumMember
     *
     * @param email         The premium member's email (inherited)
     * @param name          The premium member's name (inherited)
     * @param address       The premium member's address (inherited)
     * @param gender        The premium member's gender (inherited)
     * @param height        The premium member's height (inherited)
     * @param startWeight   The premium member's starting weight (inherited)
     * @param chosenPackage The premium member's chosen package
     */
    public PremiumMember(String email, String name, String address,
                         String gender, float height, float startWeight, String chosenPackage) {
        super(email, name, address, gender, height, startWeight, chosenPackage);
        chosenPackage(chosenPackage);
    }

    /**
     * This method implements inheritance from the Member class (chosenPackage) method.
     * Used to set the chosen package for the premium member.
     *
     * @param packageChoice the premium package chosen by the member.
     */
    public void chosenPackage(String packageChoice) {
        this.packageChoice = packageChoice;
    }

    /**
     * The toString method returns the premium member objects in a String format.
     * A call is made to super.toString() to display the member properties inherited from the member toString() method.
     * The package choice is appended to this.
     *
     * @return the String representation of the student member object.
     */
    public String toString() {
        return super.toString() + " Chosen Package: " + packageChoice;
    }
}
