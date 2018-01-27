package aln.Supply;

import javafx.scene.paint.Color;


public class Ink extends Supply{
    
    private String volume;
    private java.awt.Color color;

    
    @Override
    public String getName() {
        return super.getName();
    }
    
    public Ink() {
        super("", "", "", 0 ,0 , 0.00, "");
        this.volume ="";
        setColor();
        super.setThreshold(.67);
    }

    public String getVolume() {
        return volume;
    }
    
    public void setVolume(String volume) {
        this.volume = volume;
    }
    
    public void setColor(){
        javafx.scene.paint.Color tempColor;
        if (super.getName().toLowerCase().contains("light magneta")){
            tempColor=Color.LIGHTPINK;
        } else if (super.getName().contains("Photo Magenta")){
            tempColor=Color.LIGHTPINK;
        } else if (super.getName().contains("Light Cyan")){
            tempColor=Color.CYAN;
        } else if (super.getName().contains("Photo Cyan")){
            tempColor=Color.CYAN;
        } else if (super.getName().contains("Light Gray")){
            tempColor=Color.gray(.50);
        } else if (super.getName().contains("Light Grey")){
            tempColor=Color.gray(.50);
        } else if (super.getName().contains("Photo Gray")){
            tempColor=Color.gray(.50);
        } else if (super.getName().contains("Photo Grey")){
            tempColor=Color.gray(.50);
        } else if (super.getName().contains("Matte Black")){
            tempColor=Color.gray(.15);
        } else if (super.getName().contains("Light Black")){
            tempColor=Color.GRAY;
        } else if (super.getName().contains("Black")){
            tempColor=Color.BLACK;
        } else if (super.getName().contains("Cyan")){
            tempColor=Color.DARKCYAN;
        } else if (super.getName().contains("Magenta")){
            tempColor=Color.MAGENTA;
        } else if (super.getName().contains("Yellow")){
            tempColor=Color.YELLOW;
        } else if (super.getName().contains("Red")){
            tempColor=Color.MAGENTA;
        } else if (super.getName().contains("Green")){
            tempColor=Color.GREEN;
        } else if (super.getName().contains("Blue")){
            tempColor=Color.GREEN;
        } else if (super.getName().contains("Orange")){
            tempColor=Color.ORANGE;
        } else if (super.getName().contains("Grey")){
            tempColor=Color.gray(.30);
        } else if (super.getName().contains("Gray")){
            tempColor=Color.gray(.30);
        } else{
            tempColor=Color.BLACK;
        }
        
        float r = (float) tempColor.getRed();
        float g = (float) tempColor.getGreen();
        float b = (float) tempColor.getBlue();
        //float o = (float) tempColor.getOpacity();
        
        this.color = new java.awt.Color(r,g,b);
    }
    
    

    public java.awt.Color getColor() {
        return color;
    }
    
    @Override
    public String getOtherDetail() {
        return getVolume();
    }
    
    @Override
    public String toString() {
        return super.toString()+ " " + volume;
    }
}
