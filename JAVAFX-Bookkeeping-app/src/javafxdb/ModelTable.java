package javafxdb;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.InputStream;

public class ModelTable extends RecursiveTreeObject<ModelTable> {


    String date;

    Float amount;

    String details;

    String receiv;

    Integer emp_id;

    Float mainbudget;

    String filepath;

    String sheetname;



    public ModelTable(String date, Float amount, String details, String receiv, Integer emp_id, Float mainbudget, String filepath,  String sheetname) {

        this.date = date;
        this.amount = amount;
        this.details = details;
        this.receiv = receiv;
        this.emp_id = emp_id;
        this.mainbudget = mainbudget;
        this.filepath = filepath;
        this.sheetname = sheetname;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getReceiv() {
        return receiv;
    }

    public void setReceiv(String receiv) {
        this.receiv = receiv;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public Float getMainbudget() {
        return mainbudget;
    }

    public void setMainbudget(Float mainbudget) {
        this.mainbudget = mainbudget;
    }
    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getSheetname() {
        return sheetname;
    }

    public void setSheetname(String sheetname) {
        this.sheetname = sheetname;
    }



}

