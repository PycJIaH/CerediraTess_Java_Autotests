package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Scripts {
    final Logger log = LoggerFactory.getLogger(Scripts.class);
    WebDriver driver;

    @Test
    @DisplayName("1. Создание скрипта (новой записи)")
    public void createScriptNewEntry() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            // Локатор раздела "Администрирование"
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            // Локатор раздела "Скрипты"
            WebElement scripts = driver.findElement(new By.ByXPath("//a[normalize-space()='Скрипты']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Скрипты\"");
            administration.click();
            scripts.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            log.info("3. Нажать на вкладку \"Создать\"");
            createButton.click();
            log.info("4. В поле \"Агенты\" выбрать значение \"CerediraTess\" - Поле отсутствует в данном релизе программы");
            //Локатор поля "Имя скрипта"
            WebElement scriptName = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            log.info("5. В поле \"Имя скрипта\" ввести значение \"test_1.bat\"");
            String scriptNameValue = "test_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            scriptName.sendKeys(scriptNameValue);
            //Локатор поля "Описание"
            WebElement descriptionScript = driver.findElement(new By.ByXPath("//*[@id=\"description\"]"));
            log.info("6. В поле \"Описание\" ввести значение \"Описание скрипта\"");
            descriptionScript.sendKeys("Описание скрипта");
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("7. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("8. В таблице \"Список\" отображается строка со значением \"test_1.bat\" в столбце \"Имя скрипта\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + scriptNameValue + "']")).isDisplayed());

            log.info("СЛЕДУЮЩИЕ ШАГИ ПРИМЕНИМЫ ИЗ-ЗА ОТСУТСТВИЯ 4 ШАГА В ДАННОМ КЕЙСЕ И В НОВОМ РЕЛИЗЕ БУДУТ УДАЛЕНЫ");
            // Локатор раздела "Администрирование"
            WebElement administration2 = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор раздела "Агенты"
            WebElement agents = driver.findElement(new By.ByXPath("//a[normalize-space()='Агенты']"));
            log.info("В главном меню перейти в раздел \"Администрирование -> Агенты\"");
            administration2.click();
            agents.click();
            //Локатор кнопки "Редактировать запись"
            WebElement editAgentCerediraTessButton = driver.findElement(new By.ByXPath("//td[normalize-space()='CerediraTess']/..//a[@title='Редактировать запись']"));
            log.info("Нажать на иконку \"Редактировать запись\" агента с названием \"CerediraTess\"");
            editAgentCerediraTessButton.click();
            //Локатор поля "Скрипты"
            WebElement scriptsField = driver.findElement(new By.ByXPath("//*[@id=\"s2id_scripts\"]/ul"));
            log.info("Выбрать в поле \"Скрипты\" ...");
            scriptsField.click();
            log.info("...значение вновь созданного имени скрипта");
            //Локатор значения вновь созданного скрипта
            WebElement chooseScriptNameValue = driver.findElement(new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='" + scriptNameValue + "']"));
            chooseScriptNameValue.click();
            //Локатор кнопки "Сохранить"
            WebElement saveButton2 = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("Нажать на кнопку \"Сохранить\"");
            saveButton2.click();

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("2. Удаление записи скрипта, когда остался 1 скрипт")
    public void deleteScriptWhen1ScriptRemains() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_2.bat\"");
            String scriptNameValue = "test_" + new Random().ints(101, 200).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор имени пользователя вновь созданного (в ролях)
            WebElement scriptForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']"));
            //Локатор кнопки "Удалить запись"
            WebElement deleteUserRoleButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//button[@title='Delete record']"));
            log.info("3. Нажать на иконку \"Удалить запись\" скрипта с именем \"test_2.bat\"");
            deleteUserRoleButton.click();
            log.info("4. Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить эту запись?\"");
            Alert ConfirmDelete = driver.switchTo().alert();
            log.info("5. Нажать на кнопку \"ОК\"");
            ConfirmDelete.accept();
            log.info("6. В списке отсутствует строка со значением \"test_2.bat\" в столбце \"Имя скрипта\"");
            assertFalse(isElementExists(scriptForDelete), "Скрипт не удален");
            log.info("7. В списке появилась надпись \"Нет элементов в таблице.\"");
            assertTrue(driver.findElement(new By.ByXPath("//div[normalize-space()='Нет элементов в таблице.']")).isDisplayed(), "В таблице присутствуют другие записи скриптов");

        } finally {
            driver.quit();
        }
    }

    private void permanentAuthorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            log.info("    1. Перейти на страницу http://{url}:7801/");
            driver.get("http://192.168.242.128:7801/");
            // Локатор поля "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            // Локатор поля "Пароль"
            WebElement password = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            log.info("    2. В поле \"Логин\" вводим значение \"admin\"");
            login.sendKeys("admin");
            log.info("    3. В поле \"Пароль\" вводим значение \"admin\"");
            password.sendKeys("admin");
            log.info("    4. Нажать на кнопку \"Войти\"");
            submit.click();

        } finally {
        }
    }

    public void createScript(String scriptName) {

        try {
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            // Локатор раздела "Скрипты"
            WebElement scripts = driver.findElement(new By.ByXPath("//a[normalize-space()='Скрипты']"));
            log.info("      1. В главном меню перейти в раздел \"Администрирование -> Скрипты\"");
            administration.click();
            scripts.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            log.info("      2. Нажать на вкладку \"Создать\"");
            createButton.click();
            //Локатор поля "Имя скрипта"
            WebElement scriptNameValue = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("      3. В поле \"Имя скрипта\" вставить \"${test_name}\"");
            scriptNameValue.sendKeys(scriptName);
            log.info("      4. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("      5. В таблице \"Список\" отображается строка со значением \"${test_name}\" в столбце \"Имя скрипта\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='"+ scriptName +"']")).isDisplayed());

        } finally {
        }
    }

    private boolean isElementExists(WebElement el1) {
        try {
            el1.isDisplayed();
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
        }
        return false;
    }
}
