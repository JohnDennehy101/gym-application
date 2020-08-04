/**
 * This class is an analytics class that only has static methods.
 * It stores the calculateBMI, determineBMICategory and isIdealBodyWeight methods.
 *
 * @author John Dennehy (Student Number 20091408)
 * @version 1.0
 */

public class GymUtility {
    private static String bmiCategory;
    private static double height;
    private static double heightSquared;
    public static double bmi;
    public static double optimalMaleWeight;
    public static double optimalFemaleWeight;
    public static double oldbmi;

    private static double weight;

    /**
     * Method used to calculate the member's Body Mass Index (BMI).
     * Calculation: BMI = Weight / (Height * Height)
     *
     * @param member     The specific member object that is passed to the method. BMI will be calculated for this member.
     *                   The member's height is used in this method.
     * @param assessment The assessment for the member that is passed to the method.
     *                   The assessment weight is used in this method.
     * @return The member BMI's value.
     */
    public static double calculateBMI(Member member, Assessment assessment) {
        height = member.getHeight();
        heightSquared = height * height;
        if (assessment != null) {
            weight = assessment.getWeight();
        } else {
            weight = member.getStartWeight();
        }
        bmi = weight / heightSquared;
        bmi = (double) ((double) Math.round(bmi * 100.0) / 100.0);
        determineBMICategory(bmi);
        return bmi;
    }

    /**
     * Method used to determine the Body Mass Index (BMI) category.
     * Calculation:
     * BMI Less than 16: SEVERELY UNDERWEIGHT
     * BMI between 16 and 18.49: UNDERWEIGHT
     * BMI between 18.5 and 24.99: NORMAL
     * BMI between 25 and 29.99: OVERWEIGHT
     * BMI between 30 and 34.99: MODERATELY OBESE
     * BMI greater than or equal to 35: SEVERELY OBESE
     *
     * @param bmiValue The bmiValue that is passed to the method. Used to determine the BMI category.
     * @return The correct BMI category for the BMI value passed to the method.
     */

    public static String determineBMICategory(double bmiValue) {
        if (bmiValue < 16) {
            bmiCategory = "SEVERELY UNDERWEIGHT";
        } else if (bmiValue >= 16 && bmiValue < 18.5) {
            bmiCategory = "UNDERWEIGHT";
        } else if (bmiValue >= 18.5 && bmiValue < 25) {
            bmiCategory = "NORMAL";
        } else if (bmiValue >= 25 && bmiValue < 30) {
            bmiCategory = "OVERWEIGHT";
        } else if (bmiValue >= 30 && bmiValue < 35) {
            bmiCategory = "MODERATELY OBESE";
        } else {
            bmiCategory = "SEVERELY OBESE";
        }
        return bmiCategory;
    }

    /**
     * Used to determine if the member is at the ideal weight, considering their gender and their height and weight
     * <p>
     * Calculation: Devine Formula
     * Males: Ideal body weight is 50kg + 2.3kg for each inch over 5 feet.
     * Females: Ideal body weight is 45.5kg + 2.3kg for each inch over 5 feet.
     * <p>
     * NOTE: If no gender is specified, the female calculation for ideal body weight is used
     * NOTE: If the member is less than 5 feet tall, 50kg is used as ideal body weight for males, 45.5kg for females.
     * NOTE: To allow for different calculations and rounding, if the member weight falls between -0.2 to +0.2
     * of the ideal weight, the member is determined to have an ideal body weight.
     * <p>
     * Member height is multiplied * 100 to get height in centimetres.
     * Calculation:
     * Centimetres to inches = Height in Centimetres * 0.393701
     * <p>
     * The member height in feet is calculated
     * Calculation:
     * Inches to feet = inches / 12
     * <p>
     * Excess inches for the calculation (i.e. inches over 5 feet) are determined.
     * Calculation:
     * Inches over 5 feet = total height in inches - 60 (5 * 12 inches)
     * <p>
     * The number of inches over 5 feet is then multiplied to determine additional weight for the ideal weight calculation.
     * Calculation:
     * Additional weight for ideal weight calculation = Inches over 5 feet * 2.3
     * <p>
     * The ideal weight is then calculated (dependent on gender as outlined above)
     * <p>
     * A comparison is then made between the ideal weight (dependent on the member's gender)
     * and the members weight (last assessment weight).
     * <p>
     * If the weight falls within the specified range (+/- 0.2 of the ideal weight), the boolean value is true.
     * Else it is false.
     *
     * @param member     The specific member object that is passed to the method.
     * @param assessment The assessment for the method that is passed to the method.
     *                   The assessment weight is used in this method.
     * @return boolean value: true if the member is at the ideal weight, false if not.
     */

    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        boolean idealWeight = false;
        if (assessment != null) {
            weight = assessment.getWeight();
        } else {
            weight = member.getStartWeight();
        }
        height = member.getHeight();
        double heightInCentimetres = height * 100;
        double inches = heightInCentimetres * 0.393701;
        double feet = inches / 12;
        double excessInches = inches - 60;
        double inchesWeight = excessInches * 2.3;
        optimalMaleWeight = 50 + inchesWeight;
        optimalFemaleWeight = 45.5 + inchesWeight;

        if (feet < 5 && member.getGender().toUpperCase().charAt(0) == 'M') {
            optimalMaleWeight = 50;
        } else if (feet < 5 && member.getGender().toUpperCase().charAt(0) == 'F') {
            optimalFemaleWeight = 45.5;
        }


        if (member.getGender().toUpperCase().charAt(0) == 'M' && feet >= 5 && weight >= (optimalMaleWeight - 0.2) && weight <= (optimalMaleWeight + 0.2)) {

            idealWeight = true;
        } else if (member.getGender().toUpperCase().charAt(0) == 'F' && feet >= 5 && weight >= (optimalFemaleWeight - 0.2) && weight <= (optimalFemaleWeight + 0.2)) {

            idealWeight = true;

        } else if (member.getGender().toUpperCase().charAt(0) == 'U' && feet >= 5 && weight >= (optimalFemaleWeight - 0.2) && weight <= (optimalFemaleWeight + 0.2)) {


            if (weight >= (optimalFemaleWeight - 0.2) && weight <= (optimalFemaleWeight + 0.2)) {
                idealWeight = true;
            }
        } else if (height < 5 && member.getGender().toUpperCase().charAt(0) == 'F') {
            optimalFemaleWeight = 45;
        }

        return idealWeight;
    }

}
