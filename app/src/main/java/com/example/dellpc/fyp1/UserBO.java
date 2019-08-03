package com.example.dellpc.fyp1;

/**
 * Created by Zainab Nazir on 09/05/2019.
 */

public class UserBO {

    private String email;
    private String password;
    private long id, count;
    private String name, number;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }


    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.id = count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return name;
    }
}


