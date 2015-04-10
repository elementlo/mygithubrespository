package net.wash8.classbean;

import java.io.Serializable;

/**
 * Created by ncb-user on 2015/1/12.
 */
public class Technician implements Serializable {
    private String CredentialID,Password,FullName,Mobile,Image,IdCardNumber,City,District,StationaryID,Stationary,
            Points,Rating,TotalOrders,CreatedDateTime,Status,ID;

    public String getCredentialID() {
        return CredentialID;
    }

    public void setCredentialID(String credentialID) {
        CredentialID = credentialID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIdCardNumber() {
        return IdCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        IdCardNumber = idCardNumber;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStationaryID() {
        return StationaryID;
    }

    public void setStationaryID(String stationaryID) {
        StationaryID = stationaryID;
    }

    public String getStationary() {
        return Stationary;
    }

    public void setStationary(String stationary) {
        Stationary = stationary;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getTotalOrders() {
        return TotalOrders;
    }

    public void setTotalOrders(String totalOrders) {
        TotalOrders = totalOrders;
    }

    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        CreatedDateTime = createdDateTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
