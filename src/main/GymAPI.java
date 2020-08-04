import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Arrays;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * This class operates between the model classes and the menu driver class. It stores
 * an ArrayList of member and an ArrayList of trainer.
 * It also contains the store and load methods for XStream.
 * There are other methods which query the members and trainers arraylists.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class GymAPI {
    private ArrayList<Member> members;
    private ArrayList<Trainer> trainers;
    private HashMap<String, Assessment> assessments;

    /**
     * Constructor for GymAPI class. ArrayLists declared for members and trainers.
     *
     * @author John Dennehy (Student Number: 20091408)
     */

    public GymAPI() {
        members = new ArrayList<Member>();
        trainers = new ArrayList<Trainer>();
    }

    /**
     * Getter to obtain the contents of the members ArrayList.
     *
     * @return The members ArrayList which contains all Member objects.
     */

    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Getter to obtain the contents of the trainers ArrayList.
     *
     * @return The trainers ArrayList which contains all Trainer objects.
     */
    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }

    /**
     * Add a member to the members ArrayList.
     *
     * @param member The member to be added to the members ArrayList.
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * Add a trainer to the trainers ArrayList.
     *
     * @param trainer The trainer to be added to the trainers ArrayList.
     */

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    /**
     * Method to get the number of members in the members ArrayList.
     *
     * @return The size of the members ArrayList.
     */
    public int numberOfMembers() {
        return members.size();
    }

    /**
     * Method to get the number of trainers in the trainers ArrayList.
     *
     * @return The size of the trainers ArrayList.
     */
    public int numberOfTrainers() {
        return trainers.size();
    }

    /**
     * List all members that are in the members ArrayList.
     *
     * @return The listOfMembers ArrayList which contains all members in the members ArrayList.
     */
    public ArrayList<Member> listMembers() {
        ArrayList<Member> listOfMembers = new ArrayList<Member>();
        if (members.size() == 0) {
            return listOfMembers;
        } else {
            //String listOfMembers = "";
            for (int i = 0; i < members.size(); i++) {
                // listOfMembers = listOfMembers + i + ": " + members.get(i) + "\n";
                listOfMembers.add(members.get(i));
            }
            return listOfMembers;
        }
    }

    /**
     * Method to determine if index provided by the user is valid for the members ArrayList.
     *
     * @param index The member index inputted by the user
     * @return boolean value : true if valid index, false if invalid index
     */

    public boolean isValidMemberIndex(int index) {
        if ((index >= 0) && (index < members.size())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to determine if index provided by the user is valid for the trainers ArrayList.
     *
     * @param index The trainer index inputted by the user
     * @return boolean value : true if valid index, false if invalid index
     */

    public boolean isValidTrainerIndex(int index) {
        if ((index >= 0) && (index < trainers.size())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Allows trainers to search for members by inputting an email.
     * Loops through the members ArrayList to check if the email entered matches any of the
     * registered member emails.
     *
     * @param emailEntered The email entered by the trainer.
     * @return Null if the email entered does not equal any of the existing member emails.
     * Returns relevant member if exact match found with email inputted.
     */

    public Member searchMembersByEmail(String emailEntered) {

        if (members != null) {
            for (Member individualMember : members) {
                if (emailEntered.toLowerCase().equals(individualMember.getEmail().toLowerCase()))
                    return individualMember;
            }
        }
        return null;
    }

    /**
     * Allows members to search for trainers by inputting an email.
     * Loops through the trainers ArrayList to check if the email entered matches any of the
     * registered trainer emails.
     *
     * @param emailEntered The email entered by the member.
     * @return Null if the email entered does not equal any of the existing trainer emails.
     * Returns relevant trainer if exact match found with email inputted.
     */

    public Trainer searchTrainersByEmail(String emailEntered) {
        if (trainers != null) {
            for (Trainer individualTrainer : trainers) {
                if (emailEntered.toLowerCase().equals(individualTrainer.getEmail().toLowerCase()))
                    return individualTrainer;
            }
        }
        return null;
    }

    /**
     * Allows trainers to search for members by inputting a name.
     * Loops through the members ArrayList to check if the name entered partially or fully matches
     * any of the member names. If a partial or full match is found, the member is added to the memberMatches ArrayList.
     *
     * @param nameEntered The name entered by the trainer.
     * @return If partial or full name match is found, the member is added to the memberMatches ArrayList.
     * Once the members ArrayList has been looped through, the memberMatches ArrayList is returned.
     * Empty ArrayList returned if no partial or full matches are found in the members ArrayList.
     */

    public ArrayList<String> searchMembersByName(String nameEntered) {
        ArrayList<String> memberMatches = new ArrayList<String>();
        if (members != null) {
            for (Member individualMember : members) {
                if (individualMember.getName().toLowerCase().contains(nameEntered.toLowerCase()) || nameEntered.equals(individualMember.getName())) {
                    memberMatches.add(individualMember.getName());
                }
            }
        }
        //return new ArrayList<String>();
        return memberMatches;
    }

    /**
     * Allows members to search for trainers by inputting a name.
     * Loops through the trainers ArrayList to check if the name entered partially or fully matches
     * any of the trainer names. If a partial or full match is found, the trainer is added to the trainerMatches ArrayList.
     *
     * @param nameEntered The name entered by the member.
     * @return If partial or full name match is found, the trainer is added to the trainerMatches ArrayList.
     * Once the trainers ArrayList has been looped through, the trainerMatches ArrayList is returned.
     * Empty ArrayList returned if no partial or full matches are found in the trainers ArrayList.
     */
    public ArrayList<String> searchTrainersByName(String nameEntered) {
        ArrayList<String> trainerMatches = new ArrayList<String>();
        if (trainers != null) {
            for (Trainer individualTrainer : trainers) {
                if (individualTrainer.getName().toLowerCase().contains(nameEntered.toLowerCase()) || nameEntered.equals(individualTrainer.getName())) {
                    trainerMatches.add(individualTrainer.getName());
                    //return memberMatches;
                }

            }
        }
        return trainerMatches;
    }

    /**
     * Checks if any members are of ideal weight. Loops through the members ArrayList.
     * If members are in the ideal weight range, they are added to the membersWithIdealWeight ArrayList.
     * This is determined by using the isIdealBodyWeight method in the GymUtility class.
     * Each member and the members latest assessment are passed to this method.
     * That method returns a boolean value: true if ideal weight, false if not.
     * If GymUtility.isIdealBodyWeight is true, the member is added to the membersWithIdealWeight ArrayList.
     *
     * @return ArrayList of members with ideal weight. If no members are of ideal weight, an empty ArrayList is returned.
     */

    public ArrayList<Member> listMembersWithIdealWeight() {
        ArrayList<Member> membersWithIdealWeight = new ArrayList<Member>();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getAssessments().size() != 0) {
                if (GymUtility.isIdealBodyWeight(members.get(i), members.get(i).latestAssessment())) {
                    membersWithIdealWeight.add(members.get(i));
                }

            }
        }
        //if (members.)
        return membersWithIdealWeight;
    }

    /**
     * Checks if any members have the BMI category entered by the trainer.
     * If there are members with the specified BMI category, they are added to the membersSpecificBMICategory ArrayList.
     * This is determined by using the determineBMICategory method with the calculateBMI method from the GymUtility class.
     * Each member and the members latest assessment are passed to the calculateBMI method.
     * That method calculates the membersBMI. This is then passed to the determineBMICategory method which determines
     * the BMI category of each member.
     * If the member BMI category matches the BMI category entered by the trainer, the member is added to the
     * membersSpecificBMICategory ArrayList.
     *
     * @param category The BMI category entered by the trainer.
     * @return ArrayList of members with the BMI category entered by the trainer. If no members with the entered
     * BMI category, empty ArrayList is returned.
     */

    public ArrayList<Member> listMembersBySpecificBMICategory(String category) {
        ArrayList<Member> membersSpecificBMICategory = new ArrayList<Member>();
        if (members != null) {
            for (int i = 0; i < members.size(); i++) {
                {
                    if (members.get(i).getAssessments().size()!=0) {
                        if (GymUtility.determineBMICategory(GymUtility.calculateBMI(members.get(i), members.get(i).latestAssessment())).toLowerCase().contains(category.toLowerCase())) {
                            membersSpecificBMICategory.add(members.get(i));
                        }
                    }
                }
            }

        }
        return membersSpecificBMICategory;
    }

    /**
     * This method converts member details from the metric format to imperial format and adds both formats to
     * a String which is then returned.
     * Calculations:
     * Kilos - Pounds (kgs * 2.20462)
     * Centimetres - Inches (Centimetres * 0.393701)
     * <p>
     * If an assessment has been completed for the user, the latest assessment weight is used.
     * If no assessments have been completed, the weight entered at registration is used.
     *
     * @return A String that contains all members with details in both the imperial and metric format.
     * If no members are registered, "No Registered Members" is returned.
     */

    public String listMemberDetailsImperialAndMetric() {
        String memberDetails = "";


        if (members.size() != 0) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).latestAssessment() != null) {

                    if (members.get(i).latestAssessment() != null) {
                        String formattedHeight = String.format("%.1f", members.get(i).getHeight());
                        memberDetails += i + ") " + members.get(i).getName() + /*": "*/  " : " + (int) Math.round(members.get(i).latestAssessment().weight) +
                                " kg (" + (int) Math.round(members.get(i).latestAssessment().weight * 2.205)
                                + " lbs) " + formattedHeight + " metres (" + (int) Math.ceil(((members.get(i).getHeight() * 100) * 0.393701)) + " inches)." + "\n";
                    }

                } else if (members.get(i).latestAssessment() == null) {
                    String formattedHeight = String.format("%.1f", members.get(i).getHeight());
                    memberDetails += i + ") " +  members.get(i).getName() + " " + (int) members.get(i).getStartWeight() +
                            " kg (" + (int) Math.round(members.get(i).getStartWeight() * 2.20462)
                            + " lbs) " + formattedHeight + " metres (" + (int) Math.floor(((members.get(i).getHeight() * 100) * 0.393701)) + " inches)." + "\n";
                }
            }

        } else {
            memberDetails = "No registered members";
        }
        return memberDetails;
    }

    /**
     * This method is used to load the data from the xml file "userData.xml" (xStream).
     * All relevant classes are added to the classes class to enable xStream to allow these to be processed without
     * running an error.
     * The trainers and members are loaded upon application start.
     *
     * @throws Exception
     */


    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        XStream xstream = new XStream(new DomDriver());

        // ------------------ PREVENT SECURITY WARNINGS-----------------------------
        // The Product class is what we are reading in.
        // Modify to include others if needed by modifying the next line,
        // add additional classes inside the braces, comma separated

        Class<?>[] classes = new Class[]{Member.class, Trainer.class, Assessment.class, PremiumMember.class, StudentMember.class};

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("userData.xml"));
        trainers = (ArrayList<Trainer>) is.readObject();
        members = (ArrayList<Member>) is.readObject();
        // assessments = (HashMap<String, Assessment>) is.readObject();
        is.close();
    }

    /**
     * This method is used to save the data to the xml file "userData.xml" (xStream).
     * All relevant classes are added to the classes class to enable xStream to allow these to be processed without
     * running an error.
     * The trainers and members are saved upon application exit and when an existing trainer or member has been updated.
     *
     * @throws Exception
     */

    public void store() throws Exception {
        XStream xstream = new XStream(new DomDriver());

        // ------------------ PREVENT SECURITY WARNINGS-----------------------------
        // The Product class is what we are reading in.
        // Modify to include others if needed by modifying the next line,
        // add additional classes inside the braces, comma separated

        Class<?>[] classes = new Class[]{Member.class, Trainer.class, Assessment.class, PremiumMember.class, StudentMember.class};

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("userData.xml"));
        out.writeObject(trainers);
        out.writeObject(members);
        //out.writeObject(assessments);
        out.close();
    }

}
