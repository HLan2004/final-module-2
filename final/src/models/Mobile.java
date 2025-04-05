package models;

public abstract class Mobile {
    protected int id;
    public String name;
    protected double price;
    protected int quantity;
    protected String manufacturer;

    public Mobile(int id, String name, double price, int quantity, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: " + price +
                ", Quantity: " + quantity + ", Manufacturer: " + manufacturer;
    }

    public abstract String toCSV();
}
