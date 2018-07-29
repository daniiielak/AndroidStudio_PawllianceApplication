package pawlliance.com.pawlliance.model;

import java.sql.Date;
import java.sql.Time;

public class WalkingActivity {

    // Member Variables
    private int walkingID;
    private int userID;
    private String dog;
    private Date walkingDate;
    private Time walkingStartTime;
    private Time walkingEndTime;
    private double totalWalkingTime;
    private double totalWalkingDistance;

    // Constructors
    public WalkingActivity(){
    }

    public WalkingActivity(int walkingID, int userID, String dog, Date walkingDate, Time walkingStartTime, Time walkingEndTime, double totalWalkingTime, double totalWalkingDistance) {
        this.walkingID = walkingID;
        this.userID = userID;
        this.dog = dog;
        this.walkingDate = walkingDate;
        this.walkingStartTime = walkingStartTime;
        this.walkingEndTime = walkingEndTime;
        this.totalWalkingTime = totalWalkingTime;
        this.totalWalkingDistance = totalWalkingDistance;
    }

    // Setters
    public void setWalkingID(int walkingID) {
        this.walkingID = walkingID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setDog(String dog) {
        this.dog = dog;
    }

    public void setWalkingDate(Date walkingDate) {
        this.walkingDate = walkingDate;
    }

    public void setWalkingStartTime(Time walkingStartTime) {
        this.walkingStartTime = walkingStartTime;
    }

    public void setWalkingEndTime(Time walkingEndTime) {
        this.walkingEndTime = walkingEndTime;
    }

    public void setTotalWalkingTime(double totalWalkingTime) {
        this.totalWalkingTime = totalWalkingTime;
    }

    public void setTotalWalkingDistance(double totalWalkingDistance) {
        this.totalWalkingDistance = totalWalkingDistance;
    }


    // Getters

    public int getWalkingID() {
        return walkingID;
    }

    public int getUserID() {
        return userID;
    }

    public String getDog() {
        return dog;
    }

    public Date getWalkingDate() {
        return walkingDate;
    }

    public Time getWalkingStartTime() {
        return walkingStartTime;
    }

    public Time getWalkingEndTime() {
        return walkingEndTime;
    }

    public double getTotalWalkingTime() {
        return totalWalkingTime;
    }

    public double getTotalWalkingDistance() {
        return totalWalkingDistance;
    }

    // DNC - class closing
}
