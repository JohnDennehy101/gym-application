/**
 * This class stores the email, name, address and gender values.
 * The email is used to uniquely identify a person in the system.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class Person {
    private String name;
    private String email;
    private String address;
    private String gender;
    private Member member;

    /**
     * Overloaded constructor for objects of class Person
     *
     * @param email   The email entered by the person at registration
     * @param name    The name entered by the person at registration
     * @param address The address entered by the person at registration
     * @param gender  The gender entered by the person at registration
     */
    public Person(String email, String name, String address, String gender) {
        setName(name);
        this.email = email;
        setAddress(address);
        setGender(gender);
    }

    /**
     * Constructor for objects of class Person. Allows Person object to be declared without any parameters passed.
     */
    public Person() {

    }

    /**
     * Setter to set the Person's name.
     * If the length of the name entered is 31 or greater, it is truncated to a length of 30.
     *
     * @param name Name entered by the person.
     */
    public void setName(String name) {
        this.name = name;

        if (name.length() <= 30) {
            this.name = name;
        } else {
            this.name = name.substring(0, 30);
        }
    }

    /**
     * Setter to set the Person's address.
     *
     * @param address address entered by the person.
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Setter to set the Person's gender.
     * If the first character entered is not 'M' or 'F', gender is set to 'Unspecified'.
     *
     * @param gender
     */
    public void setGender(String gender) {
        if (gender.charAt(0) == 'M' || gender.charAt(0) == 'F') {
            this.gender = gender;
        } else {
            this.gender = "Unspecified";
        }
    }

    /**
     * Getter to get the Person's name
     *
     * @return the person's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter to get the Person's email
     *
     * @return the person's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter to get the Person's address
     *
     * @return the person's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter to get the Person's gender.
     *
     * @return the person's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * The toString method returns the Person objects in a String format.
     *
     * @return the String representation of the Person object.
     */

    public String toString() {
        return "Person name: " + name
                + ", email: " + email
                + ", address: " + address
                + ", gender: " + gender;


    }
}
