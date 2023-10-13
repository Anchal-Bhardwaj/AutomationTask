package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CheckoutFlow {
    public static void main(String[] args) throws IOException, InterruptedException {
        WebDriver driver =new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();

        driver.findElement(By.id("first-name")).sendKeys("Anchal");
        driver.findElement(By.id("last-name")).sendKeys("Bhardwaj");
        driver.findElement(By.id("postal-code")).sendKeys("302017");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();
        String Result =null;
        Boolean isCheckout = driver.findElement(By.xpath("//button[@id='back-to-products']")).isDisplayed();
        if(isCheckout==true)
        {
            System.out.println("Checkout success");
        }

    }
}
