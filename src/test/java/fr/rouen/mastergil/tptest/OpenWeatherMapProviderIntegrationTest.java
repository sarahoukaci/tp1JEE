package fr.rouen.mastergil.tptest;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import fr.rouen.mastergil.tptest.meteo.IWeatherProvider;
import fr.rouen.mastergil.tptest.meteo.OpenWeatherMapProvider;
import fr.rouen.mastergil.tptest.meteo.Prevision;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpenWeatherMapProviderIntegrationTest {
    IWeatherProvider weatherProvider = new OpenWeatherMapProvider();

    @Test
    public void getForecastByCity_shouldReturnProvisionsObjectWhenSuccess(){
        //GIVEN
        String city = "paris";

        //WHEN
        List<Prevision> forecast = weatherProvider.getForecastByCity(city);

        //THEN
        assertNotNull(forecast, "The forecast list should not be null");
        assertFalse(forecast.isEmpty(), "The forecast list should not be empty");

    }

    @Test
    public void getForecastByCity_shouldThrowAnExceptionWhenStatusResponseIsDiffFrom200(){
        //GIVEN
        String city = "inexistant";
        //String expectedMessage = "Failed : HTTP error code :";

        //WHEN && THEN
        assertThatThrownBy(() -> weatherProvider.getForecastByCity(city))
                .isInstanceOf(RuntimeException.class);

    }

}
