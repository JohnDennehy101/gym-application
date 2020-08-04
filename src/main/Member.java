import java.util.*;
import java.util.TreeSet;

/**
 * This class is a subclass of (extends) Person.
 * This class stores the member's height, starting weight, chosenPackage and a hashmap to record the member's progress.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class Member extends Person {
    private float height;
    private float startWeight;
    private String chosenPackage;
    private ArrayList<Member> member;
    private ArrayList<Assessment> assessments;
    HashMap<String, Assessment> memberAssessments = new HashMap<String, Assessment>();


    /**
     * Overloaded constructor for objects of class Member
     *
     * @param email         The member's email (inherited)
     * @param name          The member's name (inherited)
     * @param address       The member's address (inherited)
     * @param gender        The member's gender (inherited)
     * @param height        The member's height
     * @param startWeight   The member's start weight
     * @param chosenPackage The member's chosen package
     */
    public Member(String email, String name, String address,
                  String gender, float height, float startWeight, String chosenPackage) {
        super(email, name, address, gender);
        setHeight(height);
        setStartWeight(startWeight);
        chosenPackage(chosenPackage);


    }

    /**
     * Constructor for objects of class Member (contains the getAssessments() method which returns
     * the memberAssessments hashmap.
     */

    public Member() {

        getAssessments();

    }

    /**
     * Getter to obtain the member's height.
     *
     * @return the member's height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Setter to set the member's height
     * Height entered must be between 1 and 3.
     * Else the user is presented with an error message which informs them that an incorrect value was entered.
     *
     * @param height set the member's height to the value passed as a parameter when the method is called.
     */

    public void setHeight(float height) {
        if (height >= 1 && height <= 3) {
            this.height = height;
        } else {
            System.out.println("Invalid height. Height must be between 1 and 3 metres.");
        }
    }

    /**
     * Getter to get the member's starting weight
     *
     * @return the member's starting weight (entered at registration).
     */

    public float getStartWeight() {
        return startWeight;
    }

    /**
     * Setter to set the member's weight
     * Weight entered must be between 35 and 250.
     * Else the user is presented with an error message which informs them that an incorrect value was entered.
     *
     * @param startWeight set the member's weight to the value passed as a parameter when the method is called.
     */

    public void setStartWeight(float startWeight) {
        if (startWeight >= 35 & startWeight <= 250) {
            this.startWeight = startWeight;
        } else {
            System.out.println("Invalid weight. Starting weight must be between 35 and 250kgs.");
        }
    }

    /**
     * Getter to get the member's chosen package.
     *
     * @return the member's chosen package.
     */

    public String getChosenPackage() {
        return chosenPackage;
    }

    /**
     * Setter to set the member's chosen package.
     *
     * @param chosenPackage set the member's chosen package to the String passed as a parameter when the method is called.
     */

    public void chosenPackage(String chosenPackage) {
        this.chosenPackage = chosenPackage;
    }

    /**
     * This method returns the memberAssessments hashmap which contains the member assessments.
     * memberAssessments hashmap: Key = Date converted to String, Value = Assessment
     *
     * @return the memberAssessments hashmap (contains the member assessments).
     */

    public HashMap<String, Assessment> getAssessments() {
        return memberAssessments;
    }

    /**
     * This method sorts the assessment dates by passing the keys of the
     * memberAssessments hashmap (assessment dates) to a Treeset. The sorted keys are
     * then available in the orderedAssessments SortedSet.
     *
     * @return the orderedAssessments SortedSet (sorted assessment dates)
     */

    public SortedSet<String> sortedAssessmentDates() {
        SortedSet<String> orderedAssessments = new TreeSet<>(memberAssessments.keySet());
        return orderedAssessments;

    }

    /**
     * This method checks if the memberAssessments hashmap is empty.
     * If it is, null is returned.
     * If it is not empty, the assessment returned is the latest one.
     * This is determined by passing the last value in the sortedAssessmentDates() set (the latest date)
     * to the memberAssessments hashmap (using the .get call).
     * This searches for the key value (the last assessment date) and returns the associated object.
     * This is then returned.
     *
     * @return the latest assessment for a member (if available). If no assessments are available, return null.
     */

    public Assessment latestAssessment() {
        Assessment assessment = null;

        if (memberAssessments == null) {
            assessment = null;
            return assessment;
        } else if (!memberAssessments.isEmpty()) {
            assessment = memberAssessments.get(sortedAssessmentDates().last());
        }

        return assessment;
    }

    /**
     * The toString method returns the member objects in a String format.
     * A call is made to super.toString() to display the member properties inherited from the Person toString() method.
     * The member's height and start weight are appended to this.
     *
     * @return the String representation of the member object.
     */

    public String toString() {
        return super.toString() + ", Height " + height
                + ", Start Weight: " + startWeight;
        // + ", Chosen Package: " + chosenPackage;
    }

}

