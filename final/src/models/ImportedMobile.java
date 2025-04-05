package models;

public class ImportedMobile extends Mobile {
    private String importCountry;
    private String status;

    public ImportedMobile(int id, String name, double price, int quantity,
                          String manufacturer, String importCountry, String status) {
        super(id, name, price, quantity, manufacturer);
        this.importCountry = importCountry;
        this.status = status;
    }

    public String getImportCountry() {
        return importCountry;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return super.toString() + ", Import Country: " + importCountry +
                ", Status: " + status;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + price + "," + quantity + "," +
                manufacturer + "," + importCountry + "," + status;
    }
}

