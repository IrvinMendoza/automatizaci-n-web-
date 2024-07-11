package runners.ModuloR4;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import runners.RunnerPersonalizado;
import util.BeforeSuite;
import util.DataToFeature;

import java.io.IOException;

@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/Features/", //actualizar ruta si es necesario
        tags = "@EXT00001_Redireccionar_Link",
     //   tags = "@ApiLibro",   //cambiar el tag si es otra ruta
        glue = "stepdefinitions",
        snippets = SnippetType.CAMELCASE)
@RunWith(RunnerPersonalizado.class)
public class EjecutarModulR4 {
    @BeforeSuite
    public static void test() throws InvalidFormatException, IOException {
        DataToFeature.overrideFeatureFiles("src/test/resources/features/ModuloR4/");
    }
}