package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderFormTestSelenium {

    private static ChromeOptions options;
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.addArguments("--headless");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if(driver !=null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitRequestIfFieldsAreFilledCorrect() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Дмитрий Синюшкин");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79990000000");
        driver.findElement(By.cssSelector("[class='checkbox__text']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualMassage.trim());
    }

    @Test
    void shouldNotSubmitRequestIfTextFieldIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79990000000");
        driver.findElement(By.cssSelector("[class='checkbox__text']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actualMassage = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actualMassage.trim());
    }

    @Test
    void shouldNotSubmitRequestIfTextFieldIsInvalid() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("jdhfh");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79990000000");
        driver.findElement(By.cssSelector("[class='checkbox__text']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actualMassage = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualMassage.trim());
    }

    @Test
    void shouldNotSubmitRequestIfTelFieldIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Дмитрий Синюшкин");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector("[class='checkbox__text']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actualMassage = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actualMassage.trim());
    }

    @Test
    void shouldNotSubmitRequestIfTelFieldIsInvalid() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Дмитрий Синюшкин");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("9252525");
        driver.findElement(By.cssSelector("[class='checkbox__text']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actualMassage = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79037474367.", actualMassage.trim());
    }

    @Test
    void shouldNotSubmitRequestIfCheckboxNotClick() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Дмитрий Синюшкин");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79990000000");
        driver.findElement(By.cssSelector("[class='checkbox__text']"));
        driver.findElement(By.cssSelector("[type='button']")).click();

        String actualMassage = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expectedMessage, actualMassage.trim());
    }

}
