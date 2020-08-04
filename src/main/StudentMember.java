/**
 * This class is a subclass of (extends) member.
 * This class stores the studentId and collegeName.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class StudentMember extends Member {
    private String studentId;
    private String collegeName;
    private String packageChoice;

    /**
     * Constructor for objects of class StudentMember
     *
     * @param email         The student member's email (inherited)
     * @param name          The student member's name (inherited)
     * @param address       The student member's address (inherited)
     * @param gender        The student member's gender (inherited)
     * @param height        The student member's height (inherited)
     * @param startWeight   The student member's start weight (inherited)
     * @param chosenPackage The student member's chosen package (WIT if WIT entered, Package 3 if any other college entered)
     * @param studentId     The student member's student number
     * @param collegeName   The student member's college name
     */
    public StudentMember(String email, String name, String address,
                         String gender, float height, float startWeight, String chosenPackage, String studentId, String collegeName) {
        super(email, name, address, gender, height, startWeight, chosenPackage);
        this.collegeName = collegeName;
        this.studentId = studentId;
        chosenPackage(chosenPackage);
    }

    public void chosenPackage(String packageChoice) {
        this.packageChoice = packageChoice;
    }

    /**
     * Getter to get the student member's college name
     *
     * @return the student member's college name
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * The toString method returns the student member objects in a String format.
     * A call is made to super.toString() to display the member properties inherited from the member toString() method.
     * The studentid number, college name and package choice are appended to this.
     *
     * @return the String representation of the student member object.
     */

    public String toString() {
        return super.toString() + " Chosen Package: " + packageChoice +  " Student ID: " + studentId + " College Name: " + collegeName;
    }
}
