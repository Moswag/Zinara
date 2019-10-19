package cytex.co.zw.model;

public class Ticket {

    private String reference;
    private String amount;
    private String tollgateNumber;
    private String status;
    private String numberPlate;
    private String date;
    private String tollgateName;

    public Ticket() {
    }

    public Ticket(String reference, String amount, String tollgateNumber, String status, String numberPlate, String date, String tollgateName) {
        this.reference = reference;
        this.amount = amount;
        this.tollgateNumber = tollgateNumber;
        this.status = status;
        this.numberPlate = numberPlate;
        this.date = date;
        this.tollgateName = tollgateName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTollgateNumber() {
        return tollgateNumber;
    }

    public void setTollgateNumber(String tollgateNumber) {
        this.tollgateNumber = tollgateNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTollgateName() {
        return tollgateName;
    }

    public void setTollgateName(String tollgateName) {
        this.tollgateName = tollgateName;
    }
}
