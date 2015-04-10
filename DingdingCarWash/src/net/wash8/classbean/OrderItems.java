package net.wash8.classbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ncb-user on 2015/1/9.
 */
public class OrderItems implements Serializable{
    private String SerialNumber,UserID,CarID,Services,Fee,City,District,Neigborhoods,CarLocation,BookedPeriodFrom,
    BookedPeriodTo,ServiceStart,ServiceEnd,AppVersion,TechnicianID,CouponID,Status,CreatedDateTime,ID;
    private UserInfo User;
    private Cars Car;
    private Technician Technician;
    private Coupon Coupon;
    private List<CarImages> CarImages;
    private List<Ratings> Ratings;
    private Rating Rating;

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getCarID() {
        return CarID;
    }

    public void setCarID(String carID) {
        CarID = carID;
    }

    public String getServices() {
        return Services;
    }

    public void setServices(String services) {
        Services = services;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
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

    public String getNeigborhoods() {
        return Neigborhoods;
    }

    public void setNeigborhoods(String neigborhoods) {
        Neigborhoods = neigborhoods;
    }

    public String getCarLocation() {
        return CarLocation;
    }

    public void setCarLocation(String carLocation) {
        CarLocation = carLocation;
    }

    public String getBookedPeriodFrom() {
        return BookedPeriodFrom;
    }

    public void setBookedPeriodFrom(String bookedPeriodFrom) {
        BookedPeriodFrom = bookedPeriodFrom;
    }

    public String getBookedPeriodTo() {
        return BookedPeriodTo;
    }

    public void setBookedPeriodTo(String bookedPeriodTo) {
        BookedPeriodTo = bookedPeriodTo;
    }

    public String getServiceStart() {
        return ServiceStart;
    }

    public void setServiceStart(String serviceStart) {
        ServiceStart = serviceStart;
    }

    public String getServiceEnd() {
        return ServiceEnd;
    }

    public void setServiceEnd(String serviceEnd) {
        ServiceEnd = serviceEnd;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getTechnicianID() {
        return TechnicianID;
    }

    public void setTechnicianID(String technicianID) {
        TechnicianID = technicianID;
    }

    public String getCouponID() {
        return CouponID;
    }

    public void setCouponID(String couponID) {
        CouponID = couponID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        CreatedDateTime = createdDateTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public UserInfo getUser() {
        return User;
    }

    public void setUser(UserInfo user) {
        User = user;
    }

    public Cars getCar() {
        return Car;
    }

    public void setCar(Cars car) {
        Car = car;
    }

    public Technician getTechnician() {
        return Technician;
    }

    public void setTechnician(Technician technician) {
        Technician = technician;
    }

    public Coupon getCoupon() {
        return Coupon;
    }

    public void setCoupon(Coupon coupon) {
        Coupon = coupon;
    }

    public List<CarImages> getCarImages() {
        return CarImages;
    }

    public void setCarImages(List<CarImages> carImages) {
        CarImages = carImages;
    }

    public List<Ratings> getRatings() {
        return Ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        Ratings = ratings;
    }

    public Rating getRating() {
        return Rating;
    }

    public void setRating(Rating rating) {
        Rating = rating;
    }
}
