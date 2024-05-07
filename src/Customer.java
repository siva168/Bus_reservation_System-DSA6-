class Customer {
    private String name;
    private String mobileNumber;
    private String email;
    private int age;

    public Customer(String name, String mobileNumber, String email, int age) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}