package com.trodev.educationz;

public class User {
    public String usersname, email, age, institute, password;

    public User() {

    }

    public User(String usersname, String email, String age, String institute, String password) {
        this.usersname = usersname;
        this.email = email;
        this.age = age;
        this.institute = institute;
        this.password = password;
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
}
