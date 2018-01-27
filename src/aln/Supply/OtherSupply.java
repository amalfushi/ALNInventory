package aln.Supply;

public class OtherSupply extends Supply{

    private String otherDetail;

    @Override
    public String getName() {
        return super.getName();
    }
    
    public OtherSupply() {
        super("", "", "", 0 ,0 , 0.00, "");
        this.otherDetail ="";
        super.setThreshold(.51);
    }

    @Override
    public String getOtherDetail() {
        return  otherDetail;
    }

    public void setOtherDetail(String otherDetail) {
        this.otherDetail = otherDetail;
    }

    @Override
    public String toString() {
        return super.toString()+ " " + otherDetail;
    }
}
