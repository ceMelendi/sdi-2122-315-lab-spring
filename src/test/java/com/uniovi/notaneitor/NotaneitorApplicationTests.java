package com.uniovi.notaneitor;

import com.uniovi.notaneitor.pageobjects.*;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotaneitorApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe"; //CLASE y CASA
    static String Geckodriver = "C:\\Users\\uo276077\\Desktop\\SDI\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe"; //CLASE
    //static String Geckodriver = "E:\\UNIOVI\\TERCERO\\Segundo cuatri\\SDI\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe"; //CASA
    //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";

    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(URL);
    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {
    }

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    @Test
    @Order(1)
    void PR01A() {
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    @Test
    @Order(2)
    void PR01B() {
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver, PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.get(0).getText(),
                PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }

    // Opción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    @Order(3)
    void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    // Opción de navegación. Pinchar en el enlace Identifícate en la página home
    @Test
    @Order(4)
    void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    // Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
    // este test altera la bd y como no la reinicializamos entre tests nos da fallo en sucesivas ejecuciones
    @Test
    @Order(5)
    void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    // Prueba del formulario de registro. Registro con datos correctos
    @Test
    @Order(6)
    void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06A. Prueba del formulario de registro. DNI repetido en la BD
    // Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777",
                "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate",
                PO_Properties.getSPANISH());
        //Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06B. Prueba del formulario de registro. Nombre corto.
    // Propiedad: Error.signup.name.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777",
                "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
                PO_Properties.getSPANISH());
        //Comprobamos el error de Nombre corto.
        String checkText = PO_HomeView.getP().getString("Error.signup.name.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06C. Prueba del formulario de registro. Apellido largo.
    // Propiedad: Error.signup.lastName.length
    @Test
    @Order(9)
    public void PR06C() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez Alvarez Fernandez Garcia",
                "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length",
                PO_Properties.getSPANISH());
        //Comprobamos el error de apellido largo.
        String checkText = PO_HomeView.getP().getString("Error.signup.lastName.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06D. Prueba del formulario de registro. Contraseña corta.
    // Propiedad: Error.signup.password.length
    @Test
    @Order(10)
    public void PR06D() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez",
                "77", "77");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length",
                PO_Properties.getSPANISH());
        //Comprobamos el error de contraseña corta.
        String checkText = PO_HomeView.getP().getString("Error.signup.password.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06E. Prueba del formulario de registro. Contraseña no coincide.
    // Propiedad: Error.signup.passwordConfirm.coincidence
    @Test
    @Order(11)
    public void PR06E() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez",
                "77777", "aaaaa");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
        //Comprobamos el error de contraseña no coincide.
        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06F. Prueba del formulario de registro. Dni corto.
    // Propiedad: Error.signup.dni.length
    @Test
    @Order(12)
    public void PR06F() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "9", "Josefo", "Perez",
                "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.length",
                PO_Properties.getSPANISH());
        //Comprobamos el error de contraseña corta.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //Prueba del formulario de login. Identificación válida con usuario de ROL alumno
    @Test
    @Order(13)
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //Prueba del formulario de login. Identificación válida con usuario de ROL profesor
    @Test
    @Order(14)
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        //Comprobamos que entramos en la pagina privada de Profesor
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //Prueba del formulario de login. Identificación válida con usuario de ROL administrador
    @Test
    @Order(15)
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada de Administrador
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //Prueba del formulario de login. Identificación inválida con usuario de ROL alumno
    @Test
    @Order(16)
    public void PR010() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "");

        //Comprobamos que no entramos en la pagina privada de Alumno (nos pide volver a identificarnos)
        String checkText = PO_HomeView.getP().getString("login.message", PO_Properties.getSPANISH());
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);

        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //Prueba del formulario de login. Identificación válida y desconexión con usuario de ROL usuario
    @Test
    @Order(17)
    public void PR011() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");

        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        //Nos desconectamos
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

        //Comprobamos que volvemos a la página de login
        checkText = PO_HomeView.getP().getString("login.message", PO_Properties.getSPANISH());
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //Prueba de la vista privada. Identificarse como estudiante, comprobar la lista de notas y logout.
    @Test
    @Order(18)
    public void PR012() {
        PO_PrivateView.doLogin(driver, "99999990A", "123456", "Notas del usuario");

        //Contamos el número de filas de notas
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr", PO_View.getTimeout());
        Assertions.assertEquals(4, markList.size());

        PO_PrivateView.doLogout(driver);
    }

    //Prueba de la vista privada. Identificarse como estudiante, pinchar el detalle de una nota y logout.
    @Test
    @Order(19)
    public void PR013() {
        PO_PrivateView.doLogin(driver, "99999990A", "123456", "Notas del usuario");


        //SeleniumUtils.esperarSegundos(driver, 1);
        //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        //Esperamos por la ventana de detalle

        String checkText = "Detalles de la nota";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        PO_PrivateView.doLogout(driver);
    }

    //Prueba de la vista privada. Identificarse como profesor, agregar una nota y logout.
    @Test
    @Order(20)
    public void PR014() {
        PO_PrivateView.doLogin(driver, "99999993D", "123456", "99999993D");


        PO_PrivateView.goToMenuOption(driver, "marks-menu", "mark/add");

        //Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
        String checkText = "Nota Nueva 1";
        PO_PrivateView.fillFormAddMark(driver, 3, checkText, "8");

        PO_PrivateView.goToLastPage(driver);

        //Comprobamos que aparece la nota en la pagina
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());

        PO_PrivateView.doLogout(driver);
    }

    //Prueba de la vista privada. Identificarse como profesor, ir a la última página de notas, eliminar una nota y logout.
    @Test
    @Order(21)
    public void PR015() {
        PO_PrivateView.doLogin(driver, "99999993D", "123456", "99999993D");

        PO_PrivateView.goToMenuOption(driver, "marks-menu", "mark/list");

        PO_PrivateView.goToLastPage(driver);

        //Esperamos a que aparezca la Nueva nota en la ultima pagina
        //Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.get(0).click();

        PO_PrivateView.goToLastPage(driver);

        //Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "Nota Nueva 1",PO_View.getTimeout());

        PO_PrivateView.doLogout(driver);
    }
}
