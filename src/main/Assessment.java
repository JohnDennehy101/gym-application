import java.util.ArrayList;

/**
 * This class stores weight, thigh, waist, comment and the Trainer that entered the Member's Assessment.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @verion 1.0
 */

public class Assessment {
    public float weight;

    public float thigh;

    public float waist;

    private ArrayList<String> comments;
    private String trainerName;
    public String comment;

    /**
     * Overloaded Constructor for objects of class Assessment (with trainer name also available as an additional
     * parameter to the standard constructor).
     *
     * @param weight      The weight entered by the trainer when adding an assessment for a member
     * @param thigh       The thigh measurement value entered by the trainer when adding an assessment for a member
     * @param waist       The waist measurement value entered by the trainer when adding an assessment for a member
     * @param comment     The comment entered by the trainer when adding an assessment for a member
     * @param trainerName The name of the trainer that added the assessment for the member
     */
    public Assessment(float weight, float thigh, float waist, String comment, String trainerName) {
        this.waist = waist;
        this.thigh = thigh;
        this.weight = weight;
        this.comment = comment;
        this.trainerName = trainerName;

    }

    /**
     * Constructor for objects of class Assessment
     *
     * @param weight  The weight entered by the trainer when adding an assessment for a member
     * @param thigh   The thigh measurement value entered by the trainer when adding an assessment for a member
     * @param waist   The waist measurement value entered by the trainer when adding an assessment for a member
     * @param comment The comment entered by the trainer when adding an assessment for a member
     */
    public Assessment(float weight, float thigh, float waist, String comment) {
        this.waist = waist;
        this.thigh = thigh;
        this.weight = weight;
        this.comment = comment;
    }

    /**
     * Getter to obtain the weight value for an assessment.
     *
     * @return the weight value for an assessment
     */

    public double getWeight() {
        return weight;
    }

    /**
     * Setter to set the weight value for an assessment
     *
     * @param weight set weight to value passed as a parameter in the method call
     */

    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Getter to obtain the thigh value for an assessment.
     *
     * @return the thigh value for an assessment.
     */
    public double getThigh() {
        return thigh;
    }

    /**
     * Getter to obtain the waist value for an assessment.
     *
     * @return the waist value for an assessment.
     */

    public double getWaist() {
        return waist;
    }

    /**
     * Getter to obtain the comment for an assessment.
     *
     * @return the comment for an assessment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Update the comment on an assessment
     *
     * @param comment The updated comment entered by the trainer.
     */

    public void setComments(String comment) {
        this.comment = comment;
    }


    /**
     * The toString method returns the assessments in a String format.
     *
     * @return the String representation of the Assessment object.
     */
    public String toString() {
        return
                "Weight: " + weight
                        + ", thigh " + thigh
                        + ", waist: " + waist
                        + ", comment: " + comment
                        + ", trainer: " + trainerName;


    }
}
