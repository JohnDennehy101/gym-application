/**
 * This class is a subclass of (extends) Person.
 * This class stores the trainer's specialty.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class Trainer extends Person {
    private String specialty;


    /**
     * Overloaded Constructor for objects of class Trainer
     *
     * @param email     The trainer's email (inherited)
     * @param name      The trainer's name (inherited)
     * @param address   The trainer's address (inherited)
     * @param gender    The trainer's gender (inherited)
     * @param specialty The trainer's specialty
     */
    public Trainer(String email, String name, String address,
                   String gender, String specialty) {
        super(email, name, address, gender);
        setSpecialty(specialty);
    }

    /**
     * Constructor for objects of class Trainer
     */
    public Trainer() {

    }

    /**
     * Getter to obtain the trainer's specialty
     *
     * @return the trainer's specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Setter to set the trainer's specialty
     *
     * @param specialty set the trainer's specialty to the String inputted in the method call.
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * The toString method returns the trainer objects in a String format.
     * A call is made to super.toString() to display the member properties inherited from the Person toString() method.
     * The trainer's specialty is appended to this.
     *
     * @return the String representation of the Trainer object.
     */
    public String toString() {
        return super.toString() + " Trainer Specialty: " + specialty;
    }

}
