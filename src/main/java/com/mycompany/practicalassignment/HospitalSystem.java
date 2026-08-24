
package com.mycompany.practicalassignment;
import java.util.ArrayList;

public class HospitalSystem {

    private ArrayList<Patient> patients;
    private ArrayList<Bed> beds;

    public HospitalSystem() {

        patients = new ArrayList<>();
        beds = new ArrayList<>();

        // Create 20 beds
        for (int i = 1; i <= 20; i++) {

            String bedNumber;

            if (i < 10) {
                bedNumber = "B0" + i;
            } else {
                bedNumber = "B" + i;
            }

            beds.add(new Bed(bedNumber));
        }
    }

    // Register patient
    public boolean registerPatient(Patient patient) {

        Patient existingPatient = searchPatient(patient.getPatientId());

        if (existingPatient != null) {
            return false;
        }

        patients.add(patient);

        return true;
    }

    // Search patient
    public Patient searchPatient(String patientId) {

        for (int i = 0; i < patients.size(); i++) {

            if (patients.get(i).getPatientId().equalsIgnoreCase(patientId)) {
                return patients.get(i);
            }
        }

        return null;
    }

    // Update patient
    public boolean updatePatient(String patientId, String firstName,
            String lastName, int age, String gender,
            String medicalCondition) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);

        return true;
    }

    // Delete patient
    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        // Release the patient's bed if they have one
        for (int i = 0; i < beds.size(); i++) {

            if (beds.get(i).getPatientId() != null
                    && beds.get(i).getPatientId().equalsIgnoreCase(patientId)) {

                beds.get(i).releaseBed();
            }
        }

        patients.remove(patient);

        return true;
    }

    // Display all patients
    public void displayAllPatients() {

        if (patients.size() == 0) {
            System.out.println("No patients registered.");
            return;
        }

        for (int i = 0; i < patients.size(); i++) {

            System.out.println("-------------------------");
            patients.get(i).displayDetails();
        }
    }

    // Find a bed
    public Bed findBed(String bedNumber) {

        for (int i = 0; i < beds.size(); i++) {

            if (beds.get(i).getBedNumber().equalsIgnoreCase(bedNumber)) {
                return beds.get(i);
            }
        }

        return null;
    }

    // Allocate bed
    public boolean allocateBed(String patientId, String bedNumber) {

        Patient patient = searchPatient(patientId);

        // Check if patient exists
        if (patient == null) {
            return false;
        }

        // Only inpatients can get beds
        if (patient.getCategory() != PatientCategory.INPATIENT) {
            return false;
        }

        Bed bed = findBed(bedNumber);

        // Check if bed exists
        if (bed == null) {
            return false;
        }

        // Check if bed is already occupied
        if (bed.isOccupied()) {
            return false;
        }

        // Check if patient already has a bed
        for (int i = 0; i < beds.size(); i++) {

            if (beds.get(i).getPatientId() != null
                    && beds.get(i).getPatientId().equalsIgnoreCase(patientId)) {

                return false;
            }
        }

        bed.allocateBed(patientId);

        // Update the inpatient's bed number
        if (patient instanceof Inpatient){

            Inpatient inpatient = (Inpatient) patient;
            inpatient.setBedNumber(bedNumber);
        }

        return true;
    }

    // Release bed
    public boolean releaseBed(String bedNumber) {

        Bed bed = findBed(bedNumber);

        if (bed == null) {
            return false;
        }

        if (!bed.isOccupied()) {
            return false;
        }

        String patientId = bed.getPatientId();

        Patient patient = searchPatient(patientId);

        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;
            inpatient.setBedNumber(null);
        }

        bed.releaseBed();

        return true;
    }

    // Display ward layout
    public void displayWardLayout() {

        System.out.println("\nWARD LAYOUT");

        for (int i = 0; i < beds.size(); i++) {

            Bed bed = beds.get(i);

            if (bed.isOccupied()) {
                System.out.print("[X] " + bed.getBedNumber() + "   ");
            } else {
                System.out.print("[ ] " + bed.getBedNumber() + "   ");
            }

            // Move to the next line after every 5 beds
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println("\n[X] = Occupied");
        System.out.println("[ ] = Available");
    }

    // Display available beds
    public void displayAvailableBeds() {

        System.out.println("\nAVAILABLE BEDS:");

        boolean available = false;

        for (int i = 0; i < beds.size(); i++) {

            if (!beds.get(i).isOccupied()) {

                System.out.println(beds.get(i).getBedNumber());
                available = true;
            }
        }

        if (!available) {
            System.out.println("No beds available.");
        }
    }

    // Display occupied beds
    public void displayOccupiedBeds() {

        System.out.println("\nOCCUPIED BEDS:");

        boolean occupied = false;

        for (int i = 0; i < beds.size(); i++) {

            if (beds.get(i).isOccupied()) {

                System.out.println(beds.get(i).getBedNumber()
                        + " - Patient ID: "
                        + beds.get(i).getPatientId());

                occupied = true;
            }
        }

        if (!occupied) {
            System.out.println("No occupied beds.");
        }
    }

    // Count registered patients
    public int getTotalPatients() {
        return patients.size();
    }

    // Count occupied beds
    public int getOccupiedBedCount() {

        int count = 0;

        for (int i = 0; i < beds.size(); i++) {

            if (beds.get(i).isOccupied()) {
                count++;
            }
        }

        return count;
    }

    // Count available beds
    public int getAvailableBedCount() {

        int count = 0;

        for (int i = 0; i < beds.size(); i++) {

            if (!beds.get(i).isOccupied()) {
                count++;
            }
        }

        return count;
    }

    // Calculate occupancy percentage
    public double getOccupancyPercentage() {

        return ((double) getOccupiedBedCount() / 20) * 100;
    }

    // Display report
    public void displayReport() {

        System.out.println("\n========== HOSPITAL REPORT ==========");

        System.out.println("Total Registered Patients: "
                + getTotalPatients());

        System.out.println("Total Occupied Beds: "
                + getOccupiedBedCount());

        System.out.println("Total Available Beds: "
                + getAvailableBedCount());

        System.out.println("Ward Occupancy Percentage: "
                + getOccupancyPercentage() + "%");
    }

    // Sort by surname
    public void sortBySurname() {

        for (int i = 0; i < patients.size() - 1; i++) {

            for (int j = 0; j < patients.size() - 1 - i; j++) {

                String surname1 = patients.get(j).getLastName();
                String surname2 = patients.get(j + 1).getLastName();

                if (surname1.compareToIgnoreCase(surname2) > 0) {

                    Patient temp = patients.get(j);

                    patients.set(j, patients.get(j + 1));
                    patients.set(j + 1, temp);
                }
            }
        }
    }

    // Sort by Patient ID
    public void sortByPatientId() {

        for (int i = 0; i < patients.size() - 1; i++) {

            for (int j = 0; j < patients.size() - 1 - i; j++) {

                String id1 = patients.get(j).getPatientId();
                String id2 = patients.get(j + 1).getPatientId();

                if (id1.compareToIgnoreCase(id2) > 0) {

                    Patient temp = patients.get(j);

                    patients.set(j, patients.get(j + 1));
                    patients.set(j + 1, temp);
                }
            }
        }
    }
}

