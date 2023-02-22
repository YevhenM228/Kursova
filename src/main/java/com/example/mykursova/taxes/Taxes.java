package com.example.mykursova.taxes;

import com.example.mykursova.db.DataBase;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.mykursova.Main.logger;


public class Taxes extends Income {

    public static List<Income> taxes;
    public Taxes(List<Income> taxes2){ taxes = taxes2; }
    public Taxes() {
        taxes = DataBase.getTaxesFromDatabase();
    }

    public List<Income> getTaxes() {
        return taxes;
    }

    public void addIncome(List<Income> taxes, Income income){
        logger.fine("Отримання розміру податку шляхом надання доходу та додавання податкової інформації до списку податків");        income.getPercentage();
        income.getTax();
        taxes.add(income);
    }
    public double SumTaxes(){

        logger.info("Сума податку");
        if (taxes.isEmpty()) {
            logger.info("Список податків порожній");
            return -1;
        }

        double total = 0.0;
        for(Income tax: taxes){
            total += tax.getTax();
        }
        System.out.println("\n Сума податків: " + total);

        return total;
    }

    public Set<Double> SetTaxes(){

        if (taxes.isEmpty()) {
            logger.info("Список податків порожній");
            return null;
        }

        logger.info("Визначення сукупності розмірів податків");
        Set<Double> taxesSet = new HashSet<>();
        for(Income tax: taxes){
            taxesSet.add(tax.getTax());
        }
        System.out.println("\n Набір податків:");
        for (Double obj : taxesSet)
            System.out.println(obj);

        return taxesSet;
    }

    public List<Income> sortTaxes(String choice){

        List<Income> sortedTaxes = new ArrayList<>(taxes);
        if (Objects.equals(choice, "За зростанням")) {
            logger.info("Сортування податків за розміром податку та за зростанням");
            System.out.println("\n Розсортовані податки за зростанням:");
            sortedTaxes.sort(new SortAsc());
        }  if (Objects.equals(choice, "За спаданням")) {
            logger.info("Сортування податків за розміром податку та за спаданням");
            System.out.println("\n Розсортовані податки за спаданням:");
            sortedTaxes.sort(new SortDesc());
        }
        info(sortedTaxes);
        return sortedTaxes;
    }
    public List<Income> searchTaxes(String type, double a, double b){

        if (Objects.equals(type, "Дохід")){
            logger.info("Пошук податків за доходами");
            System.out.println("\n Пошук податків за доходами в діапазоні [" + a +"; "+ b +"]:");
        }
        else {
            logger.info("Пошук податків за розміром податку");
            System.out.println("\n Пошук податків за доходами в діапазоні [" + a + "; " + b + "]:");
        }
        taxes = filterTaxes(type,a,b);

        info(taxes);
        return taxes;
    }

    public List<Income> filterTaxes(String type, double a, double b){

        if (Objects.equals(type, "Дохід")){
            return taxes.stream().filter(k -> k.getSize() > a && k.getSize() < b).collect(Collectors.toCollection(ArrayList::new));
        }
        else{
            return taxes.stream().filter(k -> k.getTax() > a && k.getTax() < b ).collect(Collectors.toCollection(ArrayList::new));
        }

    }

    public void info(List<Income> taxes){
        logger.info("Друк списку податків");
        for(int i = 0; i < taxes.size(); i++) {
            System.out.print((i + 1) + ". " + taxes.get(i).toString() + "\n");
        }
    }
}
