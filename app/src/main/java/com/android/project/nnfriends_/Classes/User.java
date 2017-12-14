package com.android.project.nnfriends_.Classes;

/**
 * Created by victory on 2017-10-19.
 */

public class User {

    /* 필수정보 */
    private String uID;        // 핸드폰번호
    private String uPW;        // 4자리 PIN비밀번호
    private String name;
    private int age;        // 만 나이, 주민번호에서 판별
    private int gender;     // 주민번호에서 판별; 0:여, 1:남
    private String SSN;        // 주민등록번호
    private int state;      // 권한상태; 0:봉사대기, 1:수혜대기, 2:봉사승인, 3:수혜승인
    private String address_full;    // 전체주소
    private String address_city;    // 시
    private String address_gu;      // 구
    private String address_dong;    // 동

    /* 추가정보 */
    private String matchNum;   // 매칭 번호 (그룹이름같은 존재, 수혜자의 ID)
    private int payday;     // 월급일
    private String vID;        // 발굴자ID; -1:없음
    private int vAge;       // 수혜자가 매칭을 원하는 봉사자의 나이대; 0:all, 1:60s, 2:70s, 3:80s, 4:90s~
    private int vGender;    // 수혜자가 매칭을 원하는 봉사자의 성별; 0:all, 1:여, 2:남

    public User(){}
    public User(String uID, String uPW, String name, int age, int gender, String SSN, int state,
                String full, String city, String gu, String dong, String vID) {
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
        this.vID = vID;

        matchNum = "";
    }

    public String getuID() {
        return uID;
    }

    public String getuPW() {
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

    public String getSSN() {
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

    public String getMatchNum() {
        return matchNum;
    }

    public int getPayday() {
        return payday;
    }

    public String getvID() {
        return vID;
    }

    public int getvAge() { return vAge; }

    public int getvGender() { return vGender; }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setuPW(String uPW) {
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

    public void setSSN(String SSN) {
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

    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum;
    }

    public void setPayday(int payday) {
        this.payday = payday;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public void setvAge(int vAge) { this.vAge = vAge; }

    public void setvGender(int vGender) { this.vGender = vGender; }
}
