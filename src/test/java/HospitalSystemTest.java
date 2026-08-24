

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mycompany.practicalassignment.HospitalSystem;
import com.mycompany.practicalassignment.Patient;
import com.mycompany.practicalassignment.Inpatient;
import com.mycompany.practicalassignment.PatientCategory;

public class HospitalSystemTest {
    
    public HospitalSystemTest() {
        import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {

    private HospitalSystem hospital;

    @BeforeEach
    public void setUp() {
        hospital = new HospitalSystem();
    }

    //Test 1
    @Test
    public void testRegisterPatient() {

        Patient patient = new Patient("P001", "John", "Smith", 25, "Male", "Flu", PatientCategory.OUTPATIENT);

        boolean result = hospital.registerPatient(patient);

        assertTrue(result);
        assertEquals(1, hospital.getTotalPatients());
    }

    //Test 2
    @Test
    public void testPreventDuplicatePatientId() {

        Patient patient1 = new Patient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P001",
                "Mike",
                "Jones",
                30,
                "Male",
                "Headache",
                PatientCategory.EMERGENCY
        );

        hospital.registerPatient(patient1);

        boolean result = hospital.registerPatient(patient2);

        assertFalse(result);
        assertEquals(1, hospital.getTotalPatients());
    }

    //Test 3
    @Test
    public void testSearchPatient() {

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        Patient foundPatient = hospital.searchPatient("P001");

        assertNotNull(foundPatient);
        assertEquals("John", foundPatient.getFirstName());
    }

    //Test 4
    @Test
    public void testSearchPatientNotFound() {

        Patient foundPatient = hospital.searchPatient("P999");

        assertNull(foundPatient);
    }

    //Test 5
    @Test
    public void testUpdatePatient() {

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result = hospital.updatePatient(
                "P001",
                "John",
                "Brown",
                26,
                "Male",
                "Cold"
        );

        Patient updatedPatient = hospital.searchPatient("P001");

        assertTrue(result);
        assertEquals("Brown", updatedPatient.getLastName());
        assertEquals(26, updatedPatient.getAge());
        assertEquals("Cold", updatedPatient.getMedicalCondition());
    }

    //Test 6
    @Test
    public void testDeletePatient() {

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result = hospital.deletePatient("P001");

        assertTrue(result);
        assertNull(hospital.searchPatient("P001"));
        assertEquals(0, hospital.getTotalPatients());
    }

   //Test 7
    @Test
    public void testAllocateBed() {

        Inpatient patient = new Inpatient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                "Ward 1",
                null
        );

        hospital.registerPatient(patient);

        boolean result = hospital.allocateBed("P001", "B01");

        assertTrue(result);
        assertEquals(1, hospital.getOccupiedBedCount());
        assertEquals("B01", patient.getBedNumber());
    }

    //Test 8
    @Test
    public void testReleaseBed() {

        Inpatient patient = new Inpatient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                "Ward 1",
                null
        );

        hospital.registerPatient(patient);
        hospital.allocateBed("P001", "B01");

        boolean result = hospital.releaseBed("B01");

        assertTrue(result);
        assertEquals(0, hospital.getOccupiedBedCount());
        assertNull(patient.getBedNumber());
    }

    //Test 9
    @Test
    public void testPreventOccupiedBedAllocation() {

        Inpatient patient1 = new Inpatient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                "Ward 1",
                null
        );

        Inpatient patient2 = new Inpatient(
                "P002",
                "Mike",
                "Brown",
                30,
                "Male",
                "Cold",
                "Ward 1",
                null
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        hospital.allocateBed("P001", "B01");

        boolean result = hospital.allocateBed("P002", "B01");

        assertFalse(result);
        assertEquals(1, hospital.getOccupiedBedCount());
    }

    //Test 10
    @Test
    public void testOutpatientCannotGetBed() {

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result = hospital.allocateBed("P001", "B01");

        assertFalse(result);
        assertEquals(0, hospital.getOccupiedBedCount());
    }

    //Test 11
    @Test
    public void testPreventAllocationWhenAllBedsOccupied() {

        for (int i = 1; i <= 20; i++) {

            String patientId;

            if (i < 10) {
                patientId = "P0" + i;
            } else {
                patientId = "P" + i;
            }

            String bedNumber;

            if (i < 10) {
                bedNumber = "B0" + i;
            } else {
                bedNumber = "B" + i;
            }

            Inpatient patient = new Inpatient(
                    patientId,
                    "Patient" + i,
                    "Surname" + i,
                    25,
                    "Male",
                    "Condition",
                    "Ward 1",
                    null
            );

            hospital.registerPatient(patient);
            hospital.allocateBed(patientId, bedNumber);
        }

        // All 20 beds should now be occupied
        assertEquals(20, hospital.getOccupiedBedCount());

        // Register another inpatient
        Inpatient extraPatient = new Inpatient(
                "P021",
                "Extra",
                "Patient",
                30,
                "Female",
                "Condition",
                "Ward 1",
                null
        );

        hospital.registerPatient(extraPatient);

        // Try allocating an already occupied bed
        boolean result = hospital.allocateBed("P021", "B01");

        assertFalse(result);
    }

    //Test 12
    @Test
    public void testSortBySurname() {

        Patient patient1 = new Patient(
                "P003",
                "John",
                "Zulu",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P001",
                "Mike",
                "Adams",
                30,
                "Male",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        Patient patient3 = new Patient(
                "P002",
                "Sarah",
                "Brown",
                28,
                "Female",
                "Headache",
                PatientCategory.EMERGENCY
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);
        hospital.registerPatient(patient3);

        hospital.sortBySurname();

        assertEquals("Adams",
                hospital.getPatients().get(0).getLastName());

        assertEquals("Brown",
                hospital.getPatients().get(1).getLastName());

        assertEquals("Zulu",
                hospital.getPatients().get(2).getLastName());
    }

    //Test 13
    @Test
    public void testSortByPatientId() {

        Patient patient1 = new Patient(
                "P003",
                "John",
                "Zulu",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P001",
                "Mike",
                "Adams",
                30,
                "Male",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        Patient patient3 = new Patient(
                "P002",
                "Sarah",
                "Brown",
                28,
                "Female",
                "Headache",
                PatientCategory.EMERGENCY
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);
        hospital.registerPatient(patient3);

        hospital.sortByPatientId();

        assertEquals("P001",
                hospital.getPatients().get(0).getPatientId());

        assertEquals("P002",
                hospital.getPatients().get(1).getPatientId());

        assertEquals("P003",
                hospital.getPatients().get(2).getPatientId());
    }
  public ArrayList<Patient>getPatients(){
      return patients;
  }  
}
