package entity;

public class Product {

    private int productId;
    private String productName;
    private String brand;
    private double price;
    private String productCategory;
    private String status;
    private String photo;
    private String userEmail;

    // Constructor with all fields (excluding bookId for auto-increment cases)

    public Product(String productName, String brand, double price, String productCategory, String status, String photo, String userEmail) {
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.productCategory = productCategory;
        this.status = status;
        this.photo = photo;
        this.userEmail = userEmail;
    }
    

    // Constructor with bookId (if needed)
    public Product(int productId, String productName, String brand, double price, String productCategory, String status, String photo, String userEmail) {    
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.productCategory = productCategory;
        this.status = status;
        this.photo = photo;
        this.userEmail = userEmail;
    }

    // Getters and Setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
}
