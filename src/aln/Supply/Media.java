package aln.Supply;

public class Media extends Supply{
    
    private String size;

    @Override
    public String getName() {
        return super.getName();
    }
    
    public Media() {
        super("","", "", 0,0,0.00,"");
        this.size ="";
    }

    public String getSize() {
        return size;
    }
    
    @Override
    public String getOtherDetail(){
        return getSize();
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString()+ " " + size;
    }
}
