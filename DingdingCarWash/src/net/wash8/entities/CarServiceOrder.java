package net.wash8.entities;

import java.util.List;

/**
 * Created by lijie on 2014/12/19.
 */
public class CarServiceOrder {
    private int ID;
    private String serialNumber;
    private int userID;
    private User user;
    private int cardID;
    private UserCar userCar;
    private int services;
    private String fee;
    private String city;
    private String disrict;
    private String locationDescription;
    private long bookedPeriodFrom;
    private long bookedPeriodTo;
    private int appVersion;
    private int technicianID;
    private Technician technician;
    private int couponID;
    private Coupon coupon;
    private List<CarServiceOrderImage> carImages;
    private CarServiceOrderRating rating;
    private int status;
    private long createdDateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public UserCar getUserCar() {
        return userCar;
    }

    public void setUserCar(UserCar userCar) {
        this.userCar = userCar;
    }

    public int getServices() {
        return services;
    }

    public void setServices(int services) {
        this.services = services;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisrict() {
        return disrict;
    }

    public void setDisrict(String disrict) {
        this.disrict = disrict;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public long getBookedPeriodFrom() {
        return bookedPeriodFrom;
    }

    public void setBookedPeriodFrom(long bookedPeriodFrom) {
        this.bookedPeriodFrom = bookedPeriodFrom;
    }

    public long getBookedPeriodTo() {
        return bookedPeriodTo;
    }

    public void setBookedPeriodTo(long bookedPeriodTo) {
        this.bookedPeriodTo = bookedPeriodTo;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public int getTechnicianID() {
        return technicianID;
    }

    public void setTechnicianID(int technicianID) {
        this.technicianID = technicianID;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public int getCouponID() {
        return couponID;
    }

    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public List<CarServiceOrderImage> getCarImages() {
        return carImages;
    }

    public void setCarImages(List<CarServiceOrderImage> carImages) {
        this.carImages = carImages;
    }

    public CarServiceOrderRating getRating() {
        return rating;
    }

    public void setRating(CarServiceOrderRating rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
