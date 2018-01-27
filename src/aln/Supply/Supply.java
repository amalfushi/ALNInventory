package aln.Supply;

public abstract class Supply {

    private String category;
    private String subCategory;
    private String name;
    private int currentCount;
    private int desiredCount;
    private double price;
    private String vendor;
    private double threshold;

    public Supply(String category, String subCategory, String name, int currentCount, int desiredCount, double price, String vendor) {
        this.category = category;
        this.subCategory = subCategory;
        this.name = name;
        this.currentCount = currentCount;
        this.desiredCount = desiredCount;
        this.price = price;
        this.threshold = .34;
    }
    
    public Supply(){
        this("", "", "", 0, 0, 0.00, "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }
    
    
    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Double d) {
        int i = d.intValue();
        this.currentCount = i;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getDesiredCount() {
        return desiredCount;
    }

    public void setDesiredCount(Double d) {
        int i = d.intValue();
        this.desiredCount = i;
    }

    public void adjustCurrentCount(int adjustment) {
        currentCount = adjustment;
    }

    public String getVendor() {
        return vendor;
    }
    
    public String getOtherDetail(){
        return null;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
    
    public Boolean checkForReorder(){
        //if(currentCount==0 ){
        //    return true;
        //}
        return ((double)currentCount/(double)desiredCount <= threshold);
    }
    
    public int orderQuantity(){
        return desiredCount-currentCount;
    }
    
    @Override
    public String toString() {
        String helper = "";
        helper += category;
        helper += " " + name;
        helper += " " + currentCount;
        helper += " " + desiredCount;
        helper += " " + price;
        helper += " " + vendor;
        return helper;
    }

    @Override
    public int hashCode() {
        return (13*category.hashCode()) + name.hashCode();
    }
    
    
}
