package entity;

public class Purchase {

    private int id_purchase;
    private String date;
    private int amount;
    private int id_client;
    private int id_product;

    public Purchase() {

    }

    public int getId_purchase() {
        return id_purchase;
    }

    public void setId_purchase(int id_purchase) {
        this.id_purchase = id_purchase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public Purchase(int id_purchase, String date, int amount, int id_client, int id_product) {
        this.id_purchase = id_purchase;
        this.date = date;
        this.amount = amount;
        this.id_client = id_client;
        this.id_product = id_product;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id_purchase=" + id_purchase +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", id_client=" + id_client +
                ", id_product=" + id_product +
                '}';
    }
}
