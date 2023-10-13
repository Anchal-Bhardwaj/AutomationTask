package org.example;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginStatus {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File(System.getProperty("user.dir") + "\\Files\\" +"Creds" + ".xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb=new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Login");

        //1. Getting number of columns, and cloumn/Header names.
      /*  XSSFRow row = sheet.getRow(0);
        int column_count = row.getLastCellNum();
        System.out.println("column count is " + column_count);
        XSSFCell cell =null;

        for (int i=0;i<column_count;i++) //Names
        {
            cell =row.getCell(i);
            String Column_Name=cell.getStringCellValue();
            System.out.println("cloumn name is "+ Column_Name);
        }
      */

        //2. Printing all the values from the column.(2 for loops: 1 to iterate through each and every row & other to column)
       /* XSSFRow row = null;
        XSSFCell cell = null;

        for (int i=0;i<=sheet.getLastRowNum();i++)  //navigate rows
        {
            row = sheet.getRow(i);
            for (int j=0;j< row.getLastCellNum();j++) // navigate columns
            {
                cell = row.getCell(j);
                String my_cell_value = cell.getStringCellValue();
                System.out.println("My cell value is "+my_cell_value );
            }
        }
        */

        WebDriver driver =new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.saucedemo.com/");
        //3. Storing values into variables to pass to the web application for testing.

        XSSFRow row = null;
        XSSFCell cell = null;
        String Username = null;
        String Password = null;

        for (int i=1; i<= sheet.getLastRowNum();i++)
        {
            row=sheet.getRow(i);
            for (int j=0;j<row.getLastCellNum();j++)
            {
                cell=row.getCell(j);
                if(j==0)  // can use column name as well
                {
                    Username=cell.getStringCellValue();
                }
                if(j==1)   // can use column name as well
                {
                    Password= cell.getStringCellValue();
                }

            }
            // System.out.println("UserName :" + Username + "----->" + "Password" + Password);
            //we can pass these values in' web application for testing purpose   .
            driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(Username);
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys(Password);
            driver.findElement(By.xpath("//input[@id='login-button']")).click();
            Thread.sleep(2000);
            String Result =null;
            try
            {
                Boolean isLoggedIn =driver.findElement(By.cssSelector("#react-burger-menu-btn")).isDisplayed();
                if(isLoggedIn==true)
                {
                    Result="PASS";
                    // Writing to an excel
                    cell=row.createCell(2);
                    //cell.setCellType(cell.CELL_TYPE_STRING);
                    cell.setCellValue(Result);


                }
                System.out.println("UserName :" + Username + "----->" + "Password" + Password + "-----> Login Success ? " + Result);
                driver.findElement(By.cssSelector("#react-burger-menu-btn")).click();
                Thread.sleep(1000);
                driver.findElement(By.cssSelector("#logout_sidebar_link")).click();

            }
            catch (Exception e)
            {
                Boolean isError = driver.findElement(By.xpath("(//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')])[1]")).isDisplayed();
                if(isError==true)
                {
                    Result="FAIl";
                    cell = row.createCell(2);
                    //cell.setCellType(cell.CELL_TYPE_STRING);
                    cell.setCellValue(Result);
                }
                System.out.println("UserName :" + Username + "----->" + "Password " + Password + "-----> Login Success ?  " + Result);
            }
            driver.get("https://www.saucedemo.com/");



        }
        FileOutputStream fos =new FileOutputStream(file);
        wb.write(fos);
        fos.close();


    }
}
