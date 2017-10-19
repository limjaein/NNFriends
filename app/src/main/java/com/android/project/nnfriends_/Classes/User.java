package com.android.project.nnfriends_.Classes;

/**
 * Created by victory on 2017-10-19.
 */

public class User {

    /* 필수정보 */
    private int uID;        // 핸드폰번호
    private int uPW;        // 4자리 PIN비밀번호
    private String name;
    private int age;        // 만 나이, 주민번호에서 판별
    private int gender;     // 주민번호에서 판별
    private int SSN;        // 주민등록번호
    private int state;      // 권한상태; 0:봉사대기, 1:수혜대기, 2:봉사승인, 3:수혜승인
    private String address_full;    // 전체주소
    private String address_city;    // 시
    private String address_gu;      // 구
    private String address_dong;    // 동

    /* 추가정보 */
    private int matchNum;   // 매칭 번호
    private int payday;     // 월급일
    private int vID;        // 발굴자ID

    public User(){}
    public User(int uID, int uPW, String name, int age, int gender, int SSN, int state,
                String full, String city, String gu, String dong,
                int matchNum, int payday, int vID) {
        this.uID = uID;
        this.uPW = uPW;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.SSN = SSN;
        this.state = state;
        this.address_full = full;
        this.address_city = city;
        this.address_gu = gu;
        this.address_dong = dong;
        this.matchNum = matchNum;
        this.payday = payday;
        this.vID = vID;
    }

    public int getuID() {
        return uID;
    }

    public int getuPW() {
        return uPW;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public int getSSN() {
        return SSN;
    }

    public int getState() {
        return state;
    }

    public String getAddress_full() {
        return address_full;
    }

    public String getAddress_city() {
        return address_city;
    }

    public String getAddress_gu() {
        return address_gu;
    }

    public String getAddress_dong() {
        return address_dong;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public int getPayday() {
        return payday;
    }

    public int getvID() {
        return vID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public void setuPW(int uPW) {
        this.uPW = uPW;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAddress_full(String address_full) {
        this.address_full = address_full;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public void setAddress_gu(String address_gu) {
        this.address_gu = address_gu;
    }

    public void setAddress_dong(String address_dong) {
        this.address_dong = address_dong;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public void setPayday(int payday) {
        this.payday = payday;
    }

    public void setvID(int vID) {
        this.vID = vID;
    }
}
