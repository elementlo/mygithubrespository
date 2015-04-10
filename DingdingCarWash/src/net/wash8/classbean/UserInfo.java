package net.wash8.classbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ncb-user on 2014/12/25.
 */
public class UserInfo implements Serializable {
    private String Mobile;
    private String AuthCode;
    private String LastName;
    private String Gender;
    private String Job;
    private String Weixin;
    private String Balance;
    private String AppVersion;
    private String CreatedDateTime;
    private String ID;


    private String NotOrdered;
    private List<Cars> Cars;
    private List<VipCards> VipCards;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAuthCode() {
        return AuthCode;
    }

    public void setAuthCode(String authCode) {
        AuthCode = authCode;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getWeixin() {
        return Weixin;
    }

    public void setWeixin(String weixin) {
        Weixin = weixin;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
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

    public List<Cars> getCars() {
        return Cars;
    }

    public void setCars(List<Cars> cars) {
        Cars = cars;
    }

    public List<VipCards> getVipCards() {
        return VipCards;
    }

    public void setVipCards(List<VipCards> vipCards) {
        VipCards = vipCards;
    }

    public String getNotOrdered() {
        return NotOrdered;
    }

    public void setNotOrdered(String notOrdered) {
        NotOrdered = notOrdered;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "Mobile='" + Mobile + '\'' +
                ", AuthCode='" + AuthCode + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Job='" + Job + '\'' +
                ", Weixin='" + Weixin + '\'' +
                ", Balance='" + Balance + '\'' +
                ", AppVersion='" + AppVersion + '\'' +
                ", CreatedDateTime='" + CreatedDateTime + '\'' +
                ", ID='" + ID + '\'' +
                ", Cars=" + Cars +
                ", VipCards=" + VipCards +
                '}';
    }
}
