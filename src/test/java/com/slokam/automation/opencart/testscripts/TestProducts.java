package com.slokam.automation.opencart.testscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestProducts extends TestBase{

	
	@Test
	public void verifyProductDetails() {
		login();
		String expected = "Dashboard";
		String title = driver.getTitle();
		Assert.assertEquals(title, expected);

		// click catalog

		driver.findElement(By.xpath("//a[contains(text(),'Catalog')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Products')]")).click();

		title = driver.getTitle();
		System.out.println(title);

		String productName = "iPhone";
		driver.findElement(By.xpath(
				"//td[text()='" + productName + "']//following-sibling::td//a[contains(@data-original-title,'Edit')]"))
				.click();

		driver.findElement(By.xpath("//a[text()='Data']")).click();

		String price = driver.findElement(By.name("price")).getAttribute("value");
		System.out.println(price);

		System.out.println(price);
		// wrapper classes

		double dp = Double.parseDouble(price);
		System.out.println(dp);
		dp = dp + 1;
		System.out.println(dp);
		price = String.valueOf(dp);
		driver.findElement(By.name("price")).sendKeys(price);
		driver.findElement(By.xpath("//button[contains(@data-original-title,'Save')]")).click();
		String successMessage = driver.findElement(By.cssSelector(".alert-success")).getText();
		System.out.println(successMessage);

		driver.get("https://localhost/opencart/");
		driver.findElement(By.name("search")).sendKeys(productName);
		driver.findElement(By.cssSelector(".fa-search")).click();
		driver.findElement(By.xpath("//img[@title='" + productName + "']")).click();

		String origPriceString = driver.findElement(By.xpath("//li[contains(text(),'Ex Tax:')]")).getText();
		System.out.println(origPriceString);
		String ar[] = origPriceString.split("$");

		String origPrice = ar[1];
		double dd = Double.parseDouble(origPrice);
		System.out.println(dd);
		System.out.println(price);
	}

	@Test
	public void verifyCreateProduct() {
		login();
		String title = driver.getTitle();
		System.out.println(title);

		// click catalog

		driver.findElement(By.xpath("//a[contains(text(),'Catalog')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Products')]")).click();

		title = driver.getTitle();
		System.out.println(title);

		driver.findElement(By.cssSelector(".fa-plus")).click();

		String product = randomAlphaNumeric(5);
		String metaTag = product + "-tag";
		String model = product + "- model";
		driver.findElement(By.cssSelector("#input-name1")).sendKeys(product);
		driver.findElement(By.cssSelector("#input-meta-title1")).sendKeys(metaTag);
		driver.findElement(By.xpath("//a[text()='Data']")).click();
		driver.findElement(By.cssSelector("#input-model")).sendKeys(model);

		WebElement selectTaxElement = driver.findElement(By.cssSelector("#input-tax-class"));
		Select select = new Select(selectTaxElement);
		select.selectByVisibleText("Taxable Goods");
		select.selectByValue("10");
		select.selectByIndex(1);

		Select selectOutofStock = new Select(driver.findElement(By.cssSelector("#input-stock-status")));
		WebElement ele = selectOutofStock.getFirstSelectedOption();
		System.out.println(ele.getText());
		driver.findElement(By.cssSelector(".fa-save")).click();

		String message = driver.findElement(By.cssSelector(".alert")).getText();
		System.out.println(message);
	}

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}
