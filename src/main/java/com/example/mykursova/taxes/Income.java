package com.example.mykursova.taxes;

public class Income {
    private String name;
    private double size;
    public double percentage, tax;

    public Income(){}

    public Income(String name, double size) {
        this.name = name;
        this.size = size;
    }

    public String getName() { return name;}
    public void setName(String name) { this.name = name; }

    public double getSize() {
        return size;
    }
    public void setSize(double size) { this.size = size;}

    public double getTax() {
        if (tax == 0) {
            tax = (size * percentage) / 100.00;
        }
        return tax;
    }
    public void setTax(double tax) { this.tax = tax;}

    public double getPercentage() {
        if (name.equals("головний дохід") ||
                name.equals("додатковий дохід") || name.equals("авторські винагороди") || name.equals("переказ з-за кордону") ||
                name.equals("фінансова допомога")){

            percentage = 18;
        }
        else if (name.equals("продаж майна") || name.equals("кошти в подарунок")){
            percentage = 5;
        }

        return percentage;
    }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    @Override
    public String toString() {
        return " Назва:  " + "'" + name + "'" +
                ";      Дохід = " + size +
                ";      Відсоток = " + percentage +
                ";      Податок = " + tax;

    }
}