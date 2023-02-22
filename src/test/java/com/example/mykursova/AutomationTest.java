package com.example.mykursova;
import com.example.mykursova.db.DataBase;
import com.example.mykursova.taxes.Income;
import com.example.mykursova.taxes.Taxes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AutomationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("design.fxml")));
            Scene scene = new Scene(root);
            Image icon = new Image("D:\\Kursova\\src\\main\\resources\\com\\example\\mykursova\\icon.png");
            stage.getIcons().add(icon);
            stage.setResizable(false);
            String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Програма 'Податки'");
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testSortDesc() {
        FxAssert.verifyThat("#sortDesc", LabeledMatchers.hasText("За спаданням"));
        Taxes taxes = new Taxes();

        clickOn("#sortDesc");
        sleep(1000);

        List<Income> person = new ArrayList<>();
        Taxes taxes2 = new Taxes(person);

        taxes2.addIncome(person, new Income("переказ з-за кордону", 2000));
        taxes2.addIncome(person, new Income("кошти в подарунок", 20000));
        taxes2.addIncome(person, new Income("додатковий дохід", 5000));
        taxes2.addIncome(person, new Income("головний дохід", 10000));
        taxes2.addIncome(person, new Income("фінансова допомога'", 15000));
        taxes2.addIncome(person, new Income("продаж майна", 500000));

        sleep(600);
        StringBuilder actual = new StringBuilder();
        StringBuilder expected = new StringBuilder();

        for(int i = 0; i < Taxes.taxes.size(); i++){
            expected.append(taxes2.getTaxes().get(i).toString());
            actual.append(taxes.getTaxes().get(i).toString());
        }

        Assert.assertEquals(expected.toString(), actual.toString());
        FxAssert.verifyThat("#totalSum", LabeledMatchers.hasText("Сума податків: 31760.0"));

    }
    @Test
    public void testSearchTaxes() {
        FxAssert.verifyThat("#searchTax", LabeledMatchers.hasText("Пошук податку"));
        clickOn("#rangeA").write("100");
        clickOn("#rangeB").write("1750");
        sleep(700);
        clickOn("#submit");
        sleep(700);
        clickOn("#searchTax");
        sleep(1000);

        String example1 = Taxes.taxes.get(0).toString();
        String expected1 = " Назва:  'додатковий дохід';      Дохід = 5000.0;      Відсоток = 18.0;      Податок = 900.0";
        Assert.assertEquals(expected1, example1);

        String example2 = Taxes.taxes.get(1).toString();
        String expected2 = " Назва:  'переказ з-за кордону';      Дохід = 2000.0;      Відсоток = 18.0;      Податок = 360.0";
        Assert.assertEquals(expected2, example2);

        String example3 = Taxes.taxes.get(2).toString();
        String expected3 = " Назва:  'кошти в подарунок';      Дохід = 20000.0;      Відсоток = 5.0;      Податок = 1000.0";
        Assert.assertEquals(expected3, example3);

        FxAssert.verifyThat("#totalSum", LabeledMatchers.hasText("Сума податків: 2260.0"));
        sleep(1000);
    }

    @Test
    public void testSearchIncomes() {
        FxAssert.verifyThat("#searchInc", LabeledMatchers.hasText("Пошук доходу"));
        clickOn("#rangeA").write("1000");
        clickOn("#rangeB").write("5000");
        sleep(700);
        clickOn("#submit");
        sleep(700);
        clickOn("#searchInc");
        sleep(1000);

        String example = Taxes.taxes.get(0).toString();
        String expected = " Назва:  'переказ з-за кордону';      Дохід = 2000.0;      Відсоток = 18.0;      Податок = 360.0";
        Assert.assertEquals(expected, example);

        FxAssert.verifyThat("#totalSum", LabeledMatchers.hasText("Сума податків: 360.0"));
        sleep(1000);
    }
    @Test
    public void testWriteToDataBase() {
        List<Income> person = new Taxes().getTaxes();

        clickOn("#writeToDataBase");
        sleep(700);

        List<Income> taxes = DataBase.getTaxesFromDatabase();
        StringBuilder actual = new StringBuilder();
        StringBuilder expected = new StringBuilder();


        for(int i = 0; i < person.size(); i++){
            expected.append(person.get(i).toString());
            actual.append(taxes.get(i).toString());
        }

        Assert.assertEquals(expected.toString(), actual.toString());
    }
    @Test
    public void testSortAsc() {

        FxAssert.verifyThat("#sortAsc", LabeledMatchers.hasText("За зростанням"));
        Taxes taxes = new Taxes();

        clickOn("#sortAsc");
        sleep(1000);

        List<Income> person = new ArrayList<>();
        Taxes taxes2 = new Taxes(person);
        taxes2.addIncome(person, new Income("продаж майна", 500000));
        taxes2.addIncome(person, new Income("фінансова допомога'", 15000));
        taxes2.addIncome(person, new Income("головний дохід", 10000));
        taxes2.addIncome(person, new Income("додатковий дохід", 5000));
        taxes2.addIncome(person, new Income("кошти в подарунок", 20000));
        taxes2.addIncome(person, new Income("переказ з-за кордону", 2000));

        sleep(600);
        StringBuilder actual = new StringBuilder();
        StringBuilder expected = new StringBuilder();

        for(int i = 0; i < Taxes.taxes.size(); i++){
            expected.append(taxes2.getTaxes().get(i).toString());
            actual.append(taxes.getTaxes().get(i).toString());
        }

        Assert.assertEquals(expected.toString(), actual.toString());
        FxAssert.verifyThat("#totalSum", LabeledMatchers.hasText("Сума податків: 31760.0"));

    }
    @Test
    public void testButtonAddTax() {
        FxAssert.verifyThat("#addIncome", LabeledMatchers.hasText("додати"));

        clickOn("#choiceBox").clickOn("авторські винагороди");
        sleep(800);
        clickOn("#sizeIncome").write("16050");
        sleep(800);
        clickOn("#addIncome");
        sleep(1000);
        FxAssert.verifyThat("#totalSum", LabeledMatchers.hasText("Сума податків: 34649.0"));
        String example = Taxes.taxes.get(Taxes.taxes.size() - 1).toString();
        String expected = " Назва:  'авторські винагороди';      Дохід = 16050.0;      Відсоток = 18.0;      Податок = 2889.0";
        Assert.assertEquals(expected, example);
        sleep(1000);
    }




}
