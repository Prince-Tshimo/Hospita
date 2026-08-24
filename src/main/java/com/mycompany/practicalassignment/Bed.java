
package com.mycompany.practicalassignment;


public class Bed {

    private String bedNumber;
    private boolean occupied;
    private String patientId;

    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
        this.occupied = false;
        this.patientId = null;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getPatientId() {
        return patientId;
    }

    public void allocateBed(String patientId) {

        occupied = true;
        this.patientId = patientId;
    }

    public void releaseBed() {

        occupied = false;
        patientId = null;
    }
} 

