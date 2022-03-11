package com.uniovi.notaneitor.pageobjects;

import com.uniovi.notaneitor.CustomConfiguration;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class PO_PrivateView extends PO_NavView {

    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        // Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void doLogin(WebDriver driver, String username, String password, String checkText) {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, username, password);
        PO_View.checkElementBy(driver, "text", checkText);
    }

    static public void doLogout(WebDriver driver){
        //Comprobamos que aparece el menu de identificarse
        PO_PrivateView.clickOption(driver, "logout", "text", "Login");
    }

    public static void goToMenuOption(WebDriver driver, String menuOption, String submenu) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, '"+ menuOption +"')]/a");
        elements.get(0).click();

        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, '" + submenu +"')]");
        elements.get(0).click();
    }

    public static void goToLastPage(WebDriver driver) {
        //Esperamos a que se muestren los enlaces de paginacion la lista de notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página (sin generalizar porque no encontramos la forma)
        elements.get(3).click();
    }
}
