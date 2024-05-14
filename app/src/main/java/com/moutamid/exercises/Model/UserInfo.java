package com.moutamid.exercises.Model;

public class UserInfo {
  public   String name;
    public String email;
    public String token;

    public  String profile_url;
    public   String id;

    public UserInfo() {
    }

    public UserInfo(String name, String email, String id, String age, String weight, String gender) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
    }

    public UserInfo(String name, String email, String age, String weight, String gender) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
    }

    String age;
    String weight;
    String gender;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo(String name, String email, String profile_url, String id) {
        this.name = name;
        this.email = email;
        this.profile_url = profile_url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
