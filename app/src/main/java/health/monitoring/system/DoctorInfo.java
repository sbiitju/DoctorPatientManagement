package health.monitoring.system;

public class DoctorInfo {
    private String name,phone,speciality,time;

    public DoctorInfo(String name, String phone, String speciality, String time) {
        this.name = name;
        this.phone = phone;
        this.speciality = speciality;
        this.time = time;
    }

    public DoctorInfo() {
    }

    public DoctorInfo(String name, String speciality, String time) {
        this.name = name;
        this.speciality = speciality;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
