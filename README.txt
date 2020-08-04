Name: John Dennehy

Student Number: 20091408

Which level unit tests succeed completely: All tests from all levels are completely successful (Outstanding grade)

Which level unit tests succeed partially: Not applicable as all unit tests from all levels are successful (Outstanding grade)

Self Reflection - Grading Spectrum Level
----------------------------------------

Data Model: (Outstanding Grade)

Implemented Person, Member and Trainer classes (inheritance between Member and Person, Trainer and Person).
Student and Premium Member support implemented (Student and Premium member classes extend Member class).
Premium Members are added in the MenuController Class (by member registering - Line 915, by trainer - Line 1699).
Student Members are added in the MenuController Class (by member registering - Line 954 & 962, by trainer - Line 1739 & 1748).

Inheritance and polymorphism implemented for chosenPackage() & toString() methods:

chosenPackage() method in PremiumMember class (Line 35) and chosenPackage() method in StudentMember class (Line 35)
override the chosenPackage() method in the Member class (Line 120).

toString() method in Person class (Lines 120-127) is called in Member (Lines 182-186) and Trainer (Line 60)
classes and relevant fields in subclasses are added to super.toString().
Inheritance is applied to toString() method in Member class (Lines 182-186) as it is called in both the
PremiumMember and StudentMember classes using super.toString() (and then adding relevant fields from subclasses).

Assessment class support is available (HashMap successfully implemented: Key - Assessment Date, Value - Assessment object).
Assessments added by logged in trainers in the MenuController Class (addAssessment() method - Lines 2147-2241).
Trainers can update the comment on an existing assessment in the MenuController Class (updateAssessmentComment() method
Lines: 2051 - 2135).
Member assessment query support is available - all assessment categories (weight, thigh, waist).
Member assessment query by weight implemented in MenuController Class (viewProgressByWeight() method - Lines 1105 - 1152).
Metric is sorted by date and indication is provided if the measurement was above or below the previous measurement.
Member assessment query by waist implemented in MenuController Class (viewProgressByWaist() method - Lines 1182 - 1232).
Metric is sorted by date and indication is provided if the measurement was above or below the previous measurement.
Member assessment query by thigh implemented in MenuController Class (viewProgressByThigh() method - Lines 1262 - 1312).
Metric is sorted by date and indication is provided if the measurement was above or below the previous measurement.


API: (Outstanding Grade)

Add members/trainer, numberOfMembers/Trainers methods, isValidMember/TrainerIndex methods, basic addMember/Trainer methods implemented
(in the GymAPI class).
It is possible to search for trainers and members by both email (exact match) and name (partial and exact matches).
Fully featured API available.

getMembers() method: Line - 45
getTrainers() method: Line - 54
addMember() method: Line - 63
addTrainer() method: Line - 73
numberOfMembers method: Line - 82
numberOfTrainers method: Line - 91
listMembers() method: Lines 101 - 112
isValidMemberIndex method: Lines 121 - 127
isValidTrainerIndex method: Lines 136 - 142
searchMembersByEmail() method: Lines 154-163
searchTrainersByEmail() method: Lines 175 - 183
searchMembersByName() method: Lines 196 - 207
searchTrainersByName() method: Lines 219 - 231
listMembersWithIdealWeight() method: Lines 244-256
listMembersBySpecificBMICategory() method: Lines 273-288
listMemberDetailsImperialAndMetric() method: Lines 304-331

XML persistence (load() method: Lines 344-363 & store() method: Lines 374-393) successfully implemented.

Fully developed Utility class (GymUtility) included with fully featured calculateBMI, determineBMICategory and isIdealBodyWeight methods.

calculateBMI method: Lines 30 - 42
determineBMICategory method: Lines 58 - 73
isIdealBodyWeight method: Lines 117 - 158



Menu: (Outstanding Grade):

On application start, the gym data (trainers and members) is loaded from the 'userData.xml' file.
The first menu asks the user if they want to login or register.
If the user selected to login, the user is asked to enter the email associated with their account.
If the email matches either a trainer/member account that is registered, the user is logged in as either a trainer or member
(depending on the user's account) and the relevant menu is displayed (trainer menu for trainers, member menu for members).
If the email does not match any of the emails in any of the registered accounts, 'Access Denied' is printed to the console.
If the user selected to register, the user is prompted to enter if they are a member or a trainer. The user then enters the
relevant details for the account. If the user enters an email that is already in use in the system (for either trainers or members),
the user is informed that the email is already registered with another account and they are prompted to provide a different email address.

If the user is a trainer and they have successfully logged in, the following options are available:
- Add a new member
- List all members (Full Account Details)
- List member height and latest assessment weight (Metric & Imperial Format)
- List members with ideal weight
- Search for a member by Email
- Search for a member by Name
- Assessment sub-menu
    - Add an assessment for a member
    - Update comment on an assessment for a member
    - Search for all members with a specified BMI category
    - Return to Main Menu
- Exit Programme

If the user is a member and they have successfully logged in, the following options are available:
- View Profile
- Update Profile
    - Edit Name
    - Edit Address
    - Edit Gender
    - Edit Height
    - Edit Weight
    - Edit Chosen Package
    - Edit All Details
    - Return to Main Menu
- Progress sub-menu
    - View Progress by Weight
    - View Progress by Waist
    - View Progress by Thigh Measurement
    - Return to Main Menu
- Search for a trainer by email (exact matches returned)
- Search for a trainer by name
- Exit Programme

On application exit, the gym data is automatically saved (trainers and members) to the 'userData.xml' file.


Input Validation: (Outstanding Grade)

All user input is validated to ensure that the programme functions correctly.
Numerous examples of while loops for input validation can be found in the MenuController Class:

userInput() method: Lines 104-152 (Validates that the user chooses 'L' to login or 'R' to register (and if user chooses to
register, validates that option chosen is either 'M' for member or 'T' for trainer).

The loginUser() method (Lines 166-191) method checks if the user has entered an email that matches an account email.
If it does that account is retrieved and the user is logged in (logged in member menu if member, logged in trainer menu
if trainer).

If an invalid option is chosen from the loggedInMember() (Lines 215-300) or loggedInTrainer() menus (Lines 1408 - 1519),
the user is informed that an invalid option has been selected and the main menu is displayed once again.

Input validation is also used when adding a member (Lines 1583 - 1778) to ensure that all input is correct (name, gender,
height, starting weight, chosen package).

Validation is also used on the sub-menus to ensure that a valid index is selected:

progressSubMenu() (Members) :  Lines 1019-1068 - If member inputs invalid input, they are prompted to enter a valid input
(repeatedly until they select a valid option).

assessmentSubMenu() (Trainers) : Lines 1942 - 2002 - If trainer inputs invalid input, they are prompted to enter a valid input
(repeatedly until they select a valid option).

try{} catch{Exception e} blocks used (inside of while loops) throughout the programme to
ensure that errors are handled gracefully:

Lines 62-66
Lines 246-250
Lines 1627 - 1645
Lines 1646-1663


User Experience: (Excellent)

Output is formatted to improve the user experience in a number of different places on the menuController class:
Member Progress By Waist: (Lines 1182 - 1232)) - output is formatted to 2 decimal places using String.format()
Member Progress By Weight: (Lines 1105 - 1152) - output is formatted to 2 decimal places using String.format()
Member Progress By Thigh: (Lines 1262 - 1312) - output is formatted to 2 decimal places using String.format()

When the member is editing their details, the current information stored is displayed so that the user is aware
of current profile settings, improving the user experience:

editName()
editGender()
editHeight()

After successfully updating their details, the updated information is outputted to the console:
editName()
editGender()
editHeight()

Output is formatted in the listMemberDetailsImperialAndMetric() method in the GymAPI class (Lines 304-331) using
the String.format() method.

Menu options are clearly described, eliminating any potential user confusion around options:
loggedInMemberMenu() options - Lines 220-225
loggedInTrainerMenu() options - Lines 1413-1420
progressSubMenu() options - Lines 1025-1028
assessmentSubMenu() options - Lines 1947-1950


Tests: (Outstanding Grade)

Fully-featured tests with all unit tests for all levels successfully passing.


A statement of how much of the application specification you implemented.
----------------------------------------
All features of the application outlined in the brief are successfully implemented.


Extra Features
----------------------------------------

JavaDocs generated for all classes and all methods (can be found in the JavaDocs folder).

The member's BMI, BMI category and ideal weight indicator are displayed to the logged in member
when they view their profile details.

Also added member progress view for thigh measurements (on Progress sub-menu for logged in members).

Included the following additional features for Trainers (as menu options)
- 'Search for a member by Email'
- 'Search for a member by Name'
- 'List member height and latest assessment weight (Metric & Imperial Format)'
- 'List members with ideal weight'
- 'Search for all members with a specified BMI category'

Included the following additional features for Members (as menu options)
- 'View Progress by Thigh Measurement'
- 'Search for a trainer by email (exact matches returned)'
- 'Search for a trainer by name'


Java syntax not covered in the lectures
----------------------------------------

TreeSet used in conjunction with a sorted set to order the assessment dates (Line 143 of Member class).
HashMap keys passed as parameter so that assessment dates can be sorted.

Java.text.Format used to format the assessment date to the correct format
Java.text.SimpleDateFormat used to format the assessment date to the correct format.


Known bugs/problems:
----------------------------------------
Some input.nextLine() bugs at the end of methods slightly hinder the user experience.
I have attempted to resolve the majority of these.

Some members chosen packages from earlier registrations are shown as null when viewing members (Trainer menu).
This has now been resolved so any new members will have their chosen package displayed.


Sources
----------------------------------------
1. Constructor Overloading in Java with examples [ONLINE] https://beginnersbook.com/2013/05/constructor-overloading/ [Accessed 07 May 2020]
2. Java String equals() Method [ONLINE] https://www.w3schools.com/java/ref_string_equals.asp [Accessed 14 May 2020]
3. BMI calculator [ONLINE] https://www.wcrf-uk.org/uk/here-help/health-tools/bmi-calculator [Accessed 15 May 2020]
4. BMI Calculator >> Ideal Body Weight - B.J.Devine Formula (1974) [ONLINE] https://www.bmi-calculator.net/ideal-weight-calculator/devine-formula/ [Accessed 17 May 2020]
5. cm to inches (Centimetres to Inches) [ONLINE] https://www.metric-conversions.org/length/centimeters-to-inches.htm [Accessed 17 May 2020]
6. String contains() Method in Java with Example [ONLINE] https://www.guru99.com/string-contains-method-java.html [Accessed 17 May 2020]
7. Partial string match [ONLINE] https://stackoverflow.com/questions/42162532/partial-string-match [Accessed 18 May 2020]
8. Class HashMap<K,V> [ONLINE] https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html [Accessed 22 May 2020]
9. HashMap in Java with Examples [ONLINE] https://www.geeksforgeeks.org/java-util-hashmap-in-java-with-examples/ [Accessed 23 May 2020]
10. Java HashMap [ONLINE] https://www.w3schools.com/java/java_hashmap.asp [Accessed 23 May 2020]
11. Java HashMap Tutorial with Examples [ONLINE] https://www.callicoder.com/java-hashmap/ [Accessed 24 May 2020]
12. Comparing Dates in java when used as keys in HashMap [ONLINE] https://stackoverflow.com/questions/20315359/comparing-dates-in-java-when-used-as-keys-in-hashmap [Accessed 24 May 2020]
13. Class SimpleDateFormat [ONLINE] https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html [Accessed 24 May 2020]
14. Java SimpleDateFormat [ONLINE] http://tutorials.jenkov.com/java-internationalization/simpledateformat.html [Accessed 24 May 2020]
15. SortedSet<E> [ONLINE] https://docs.oracle.com/javase/8/docs/api/java/util/SortedSet.html [Accessed 24 May 2020]
16. Java SortedSet [ONLINE] http://tutorials.jenkov.com/java-collections/sortedset.html [Accessed 24 May 2020]
17. A Guide to TreeSet in Java [ONLINE] Available at https://www.baeldung.com/java-tree-set [Accessed 25 May 2020]
18. Java SortedSet and TreeSet Tutorial and Examples [ONLINE] https://www.codejava.net/java-core/collections/java-sortedset-and-treeset-tutorial-and-examples [Accessed 25 May 2020]
19. SortedSet last() method in Java [ONLINE] https://www.geeksforgeeks.org/sortedset-last-method-in-java/ [Accessed 26 May 2020]
20. How to loop over TreeSet in Java [ONLINE] https://www.geeksforgeeks.org/how-to-loop-over-treeset-in-java/ [Accessed 28 May 2020]
21. Sorted Set - Java Example Program [ONLINE] http://java.meritcampus.com/java-example-programs/173/Sorted-Set [Accessed 29 May 2020]
22. How to loop over TreeSet in Java with example [ONLINE] https://www.java67.com/2016/02/how-to-loop-over-treeset-in-java-with.html [Accessed 30 May 2020]
23. How to print a float with 2 decimal places in Java? [ONLINE] https://stackoverflow.com/questions/2538787/how-to-print-a-float-with-2-decimal-places-in-java [Accessed 30 May 2020]
24. Various ways to iterate through TreeSet - 3 ways [ONLINE] https://www.benchresources.net/java-various-ways-to-iterate-through-treeset-3-ways/ [Accessed 30 May 2020]
25. Kg to Pounds conversion [ONLINE] https://www.rapidtables.com/convert/weight/kg-to-pound.html [Accessed 01 June 2020]
26. Java Inheritance [ONLINE] https://www.w3schools.com/java/java_inheritance.asp [Accessed 02 June 2020]
27. Inheritance in Java [ONLINE] https://www.javatpoint.com/inheritance-in-java [Accessed 02 June 2020]
28. Java Inheritance [ONLINE] http://tutorials.jenkov.com/java/inheritance.html [Accessed 03 June 2020]
29. Java Polymorphism [ONLINE] https://www.w3schools.com/java/java_polymorphism.asp [Accessed 04 June 2020]
30. Java Polymorphism [ONLINE] https://www.programiz.com/java-programming/polymorphism [Accessed 04 June 2020]









