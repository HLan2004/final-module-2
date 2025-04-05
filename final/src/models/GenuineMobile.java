package models;

public class GenuineMobile extends Mobile {
    private int warrantyPeriod;
    private String warrantyScope;

    public GenuineMobile(int id, String name, double price, int quantity,
                         String manufacturer, int warrantyPeriod, String warrantyScope) {
        super(id, name, price, quantity, manufacturer);
        this.warrantyPeriod = warrantyPeriod;
        this.warrantyScope = warrantyScope;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public String getWarrantyScope() {
        return warrantyScope;
    }

    @Override
    public String toString() {
        return super.toString() + ", Warranty Period: " + warrantyPeriod +
                " days, Warranty Scope: " + warrantyScope;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + price + "," + quantity + "," +
                manufacturer + "," + warrantyPeriod + "," + warrantyScope;
    }
}

