

package com.mycompany.practicalassignment;
import java.util.Scanner;

public class PracticalAssignment {




    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        HospitalSystem hospital = new HospitalSystem();

        int choice;

        do {

            System.out.println("\n====================================");
            System.out.println(" MEDICARE HOSPITAL ADMISSION SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Ward Layout");
            System.out.println("9. Display Available Beds");
            System.out.println("10. Display Occupied Beds");
            System.out.println("11. Display Report");
            System.out.println("12. Sort Patients");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("\n--- REGISTER PATIENT ---");

                    System.out.print("Patient ID: ");
                    String id = input.nextLine();

                    System.out.print("First Name: ");
                    String firstName = input.nextLine();

                    System.out.print("Last Name: ");
                    String lastName = input.nextLine();

                    System.out.print("Age: ");
                    int age = input.nextInt();
                    input.nextLine();

                    System.out.print("Gender: ");
                    String gender = input.nextLine();

                    System.out.print("Medical Condition: ");
                    String condition = input.nextLine();

                    System.out.println("1. Inpatient");
                    System.out.println("2. Outpatient");
                    System.out.println("3. Emergency");

                    System.out.print("Choose Category: ");
                    int categoryChoice = input.nextInt();
                    input.nextLine();

                    PatientCategory category;

                    if (categoryChoice == 1) {
                        category = PatientCategory.INPATIENT;
                    } else if (categoryChoice == 2) {
                        category = PatientCategory.OUTPATIENT;
                    } else {
                        category = PatientCategory.EMERGENCY;
                    }

                    Patient patient;

                    if (category == PatientCategory.INPATIENT) {

                        System.out.print("Ward Number: ");
                        String wardNumber = input.nextLine();

                        patient = new Inpatient(
                                id,
                                firstName,
                                lastName,
                                age,
                                gender,
                                condition,
                                wardNumber,
                                null
                        );

                    } else {

                        patient = new Patient(
                                id,
                                firstName,
                                lastName,
                                age,
                                gender,
                                condition,
                                category
                        );
                    }

                    if (hospital.registerPatient(patient)) {
                        System.out.println("Patient registered successfully.");
                    } else {
                        System.out.println("Patient ID already exists.");
                    }

                    break;

                case 2:

                    System.out.print("Enter Patient ID: ");
                    id = input.nextLine();

                    Patient foundPatient = hospital.searchPatient(id);

                    if (foundPatient != null) {
                        foundPatient.displayDetails();
                    } else {
                        System.out.println("Patient not found.");
                    }

                    break;

                case 3:

                    System.out.print("Enter Patient ID: ");
                    id = input.nextLine();

                    System.out.print("New First Name: ");
                    firstName = input.nextLine();

                    System.out.print("New Last Name: ");
                    lastName = input.nextLine();

                    System.out.print("New Age: ");
                    age = input.nextInt();
                    input.nextLine();

                    System.out.print("New Gender: ");
                    gender = input.nextLine();

                    System.out.print("New Medical Condition: ");
                    condition = input.nextLine();

                    if (hospital.updatePatient(
                            id,
                            firstName,
                            lastName,
                            age,
                            gender,
                            condition)) {

                        System.out.println("Patient updated successfully.");

                    } else {
                        System.out.println("Patient not found.");
                    }

                    break;

                case 4:

                    System.out.print("Enter Patient ID: ");
                    id = input.nextLine();

                    if (hospital.deletePatient(id)) {
                        System.out.println("Patient deleted successfully.");
                    } else {
                        System.out.println("Patient not found.");
                    }

                    break;

                case 5:

                    hospital.displayAllPatients();

                    break;

                case 6:

                    System.out.print("Enter Patient ID: ");
                    id = input.nextLine();

                    System.out.print("Enter Bed Number: ");
                    String bedNumber = input.nextLine();

                    if (hospital.allocateBed(id, bedNumber)) {
                        System.out.println("Bed allocated successfully.");
                    } else {
                        System.out.println("Bed allocation failed.");
                        System.out.println("Check the patient, category or bed.");
                    }

                    break;

                case 7:

                    System.out.print("Enter Bed Number: ");
                    bedNumber = input.nextLine();

                    if (hospital.releaseBed(bedNumber)) {
                        System.out.println("Bed released successfully.");
                    } else {
                        System.out.println("Bed could not be released.");
                    }

                    break;

                case 8:

                    hospital.displayWardLayout();

                    break;

                case 9:

                    hospital.displayAvailableBeds();

                    break;

                case 10:

                    hospital.displayOccupiedBeds();

                    break;

                case 11:

                    hospital.displayReport();

                    break;

                case 12:

                    System.out.println("1. Sort by Surname");
                    System.out.println("2. Sort by Patient ID");

                    System.out.print("Choose option: ");
                    int sortChoice = input.nextInt();
                    input.nextLine();

                    if (sortChoice == 1) {

                        hospital.sortBySurname();
                        System.out.println("Patients sorted by surname.");
                        hospital.displayAllPatients();

                    } else if (sortChoice == 2) {

                        hospital.sortByPatientId();
                        System.out.println("Patients sorted by Patient ID.");
                        hospital.displayAllPatients();

                    } else {

                        System.out.println("Invalid option.");
                    }

                    break;

                case 0:

                    System.out.println("Goodbye!");

                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        input.close();
    }
}