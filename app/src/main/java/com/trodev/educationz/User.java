package com.trodev.educationz;

public class User {
    public String usersname, email, age, institute, password, number, image;

    public User() {

    }

    public User(String usersname, String email, String age, String institute, String password, String number, String image) {
        this.usersname = usersname;
        this.email = email;
        this.age = age;
        this.number= number;
        this.institute = institute;
        this.password = password;
        this.image= image;
    }

    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    }
