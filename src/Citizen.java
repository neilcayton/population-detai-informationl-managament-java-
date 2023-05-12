package src;

public class Citizen implements Comparable<Citizen> {
    String fullname;
    String email;
    String address;
    int age;
    boolean resident;
    int district;
    char gender;

    public Citizen(String fullname, String email, String address,
                   int age, boolean resident, int district, char gender){
        this.fullname = fullname;
        this.email = email;
        this.address = address;
        this.age = age;
        this.resident = resident;
        this.district = district;
        this.gender = gender;
    }


    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }


    public boolean isResident() {
        return resident;
    }

    public int getDistrict() {
        return district;
    }

    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return String.format("%-20s%-50s%-50s%-5d%-5d%-5c",fullname, email ,address ,age , district , gender);
    }

    @Override
    public int compareTo(Citizen o) {
        return 0;
    }

    public Object getValue(String val) {
        if (val.equals("fullname")) {
            return fullname;
        } else if (val.equals("email")) {
            return email;
        } else if (val.equals("address")) {
            return address;
        } else if (val.equals("age")) {
            return age;
        } else if (val.equals("resident")) {
            return resident;
        } else if (val.equals("district")) {
            return district;
        } else if (val.equals("gender")) {
            return gender;
        } else {
            throw new IllegalArgumentException("Invalid field name: " + val);
        }
    }}
