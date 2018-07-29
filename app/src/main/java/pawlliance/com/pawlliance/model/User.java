/*
 * File: User.java
 * @author daniela kepper
 * Date: 01.07.2018
 * Focus: User Object (Profile) for Database entries.
 */

package pawlliance.com.pawlliance.model;

import java.text.DateFormat;

public class User {

    // Member Variables
    private int userID;
    private String ownerFullName;
    private String email;
    private String city;
    private String password;
    private String dogName;
    private String dogBreed;
    private String birthday;
    private String dogGender;
    private String imagePath;
    private String description;


    // Constructors

    public User(){
    }

    public User(int userID, String ownerFullName, String email, String city, String password, String dogName, String dogBreed, String birthday, String dogGender, String imagePath) {
        this.userID = userID;
        this.ownerFullName = ownerFullName;
        this.email = email;
        this.city = city;
        this.password = password;
        this.dogName = dogName;
        this.dogBreed = dogBreed;
        this.birthday = birthday;
        this.dogGender = dogGender;
        this.imagePath = imagePath;
        this.description = "Hello there furry Friend! Welcome to my pawtastic Profile!";
    }

    public User(int userID, String ownerFullName, String email, String city, String password){
        this.userID = userID;
        this.ownerFullName = ownerFullName;
        this.email = email;
        this.city = city;
        this.password = password;
    }

    public User(String dogName, String city, String dogBreed, String description){
        this.dogName = dogName;
        this.city = city;
        this.dogBreed = dogBreed;
        this.description = description;
    }

    // Setters
    public void setUserID(int userID){
        this.userID = userID;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setDogGender(String dogGender) {
        this.dogGender = dogGender;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // Getters
    public int getUserID(){
        return userID;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDogGender() {
        return dogGender;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    // DNC - class closing tag
}
