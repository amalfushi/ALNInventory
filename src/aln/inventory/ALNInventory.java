package aln.inventory;

import aln.Supply.Media;
import aln.Supply.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.JOptionPane;

public class ALNInventory {
    private String excelFilePath;
    private SpreadsheetReader reader;
    private LinkedHashMap<String, ArrayList<Supply>> supplyBySubCategory;
    private LinkedHashMap<String, ArrayList<Supply>> supplyBySuperCategory;
    private LinkedHashMap<Supply, Integer> toBeOrdered;
    private LinkedHashMap<String, ArrayList<Supply>> medias;
    private LinkedHashMap<String, ArrayList<Supply>> inks;
    private LinkedHashMap<String, ArrayList<Supply>> otherSupply;
    private ArrayList<Supply> allSupplies;
    private ArrayList allCells;

    public ALNInventory(String excelFilePath) {
        this.excelFilePath = excelFilePath;
        this.reader = new SpreadsheetReader();
        this.supplyBySuperCategory = new LinkedHashMap<>();
        this.supplyBySubCategory = new LinkedHashMap<>();
        this.medias = new LinkedHashMap();
        this.inks = new LinkedHashMap();
        this.otherSupply = new LinkedHashMap();
        this.allSupplies = new ArrayList<>();
        
        try { 
            parseSupplies();
        } catch (IOException ex) {  //File cannot be found
            JOptionPane.showMessageDialog(null, "The Excel sheet cannot be found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void parseSupplies() throws IOException {
        //generate ArrayList of all cells in a spreadsheet
        this.allCells = reader.readSpreadsheet(excelFilePath);
        removeHeaders(allCells);
        
        //Generates Hashmap sorted by category
        for(int i=0; i<allCells.size(); i+=8){
            if(allCells.get(i).equals("Media")){ //media specific
                Media media = new Media();
                generateGenericSupply(media, i);
                media.setSize(allCells.get(i+3).toString());
                addSupply(media);
            } else if (allCells.get(i).equals("Ink")){
                Ink ink = new Ink();
                generateGenericSupply(ink, i);
                ink.setVolume(allCells.get(i+3).toString());
                addSupply(ink);
            } else {
                OtherSupply other = new OtherSupply();
                generateGenericSupply(other, i);
                other.setOtherDetail(allCells.get(i+3).toString());
                addSupply(other);
            }
        }
    }
    
    public void generateGenericSupply(Supply currentSupply, int i){
            currentSupply.setCategory(allCells.get(i).toString());
            currentSupply.setSubCategory(allCells.get(i+1).toString());
            currentSupply.setName(allCells.get(i+2).toString());
            currentSupply.setCurrentCount((Double)allCells.get(i+4));
            currentSupply.setDesiredCount((Double)allCells.get(i+5));
            currentSupply.setPrice((Double)allCells.get(i+6));
            currentSupply.setVendor(allCells.get(i+7).toString());
    }
    
///this should be in the spreadsheetReader
    public void removeHeaders(ArrayList<String> allCells) throws IOException {
        ArrayList<String> removeHeaders = new ArrayList();
        for(int i=0; i<8; i++){
            removeHeaders.add(allCells.get(i));
        }
        allCells.removeAll(removeHeaders);
    }
    
//break this up
    public void addSupply(Supply supply){
        if (!supplyBySubCategory.containsKey(supply.getSubCategory())){
            supplyBySubCategory.put(supply.getSubCategory(), new ArrayList<>());
        }
        supplyBySubCategory.get(supply.getSubCategory()).add(supply);
        
        if (!supplyBySuperCategory.containsKey(supply.getCategory())){
            supplyBySuperCategory.put(supply.getCategory(), new ArrayList<>());
        }
        supplyBySuperCategory.get(supply.getCategory()).add(supply);
        
        if (supply.getCategory().equals("Media")){
            if (!medias.containsKey(supply.getSubCategory())){
                medias.put(supply.getSubCategory(), new ArrayList<>());
            }
            medias.get(supply.getSubCategory()).add(supply);
        } else if (supply.getCategory().equals("Ink")){
            if (!inks.containsKey(supply.getSubCategory())){
                inks.put(supply.getSubCategory(), new ArrayList<>());
            }
            inks.get(supply.getSubCategory()).add(supply);
        }
        
        if(!supply.getCategory().equals("Media") && !supply.getCategory().equals("Ink")){
            if(!otherSupply.containsKey(supply.getSubCategory())){
                otherSupply.put(supply.getSubCategory(), new ArrayList<>());
            }
            otherSupply.get(supply.getSubCategory()).add(supply);
        }
        
        allSupplies.add(supply);
    }

//needs to be finished
    public void generateOrderList(){
        toBeOrdered = new LinkedHashMap<>();
        for (ArrayList<Supply> supplies : supplyBySubCategory.values()){
            for(Supply supply: supplies){
                if(supply.checkForReorder()){
                    toBeOrdered.put(supply, supply.orderQuantity());
                }
            }
        }
    }

//needs to be finished
    public HashMap<Supply, Integer> getToBeOrdered() {
        if (toBeOrdered==null){
            generateOrderList();
        }
        return toBeOrdered;
    }
    
    public HashMap<String, ArrayList<Supply>> getSupplyBySubCategory() {
        return supplyBySubCategory;
    }
    
    public HashMap<String, ArrayList<Supply>> getSupplyBySuperCategory() {
        return supplyBySuperCategory;
    }

    public LinkedHashMap<String, ArrayList<Supply>> getMedias() {
        return medias;
    }
    
    public LinkedHashMap<String, ArrayList<Supply>> getInks() {
        return inks;
    }
    
    public LinkedHashMap<String, ArrayList<Supply>> getOtherSupply(){
        return otherSupply;
    }
    
    public ArrayList<Supply> getAllSupplies(){
        return allSupplies;
    }
}