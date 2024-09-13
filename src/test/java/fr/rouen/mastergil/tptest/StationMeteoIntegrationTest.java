package fr.rouen.mastergil.tptest.meteo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StationMeteoTestIntegration {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void main_shouldPrintNewPrevisions(){
        // GIVEN
        String[] args = {};

        // WHEN
        StationMeteo.main(args);

        // THEN
        // Assuming we know the specific text structure that should be printed out,
        // for example, it contains "Paris,FR". In real test, adjust this content
        // to match the expected format of the weather forecast output.
        assertFalse(outputStreamCaptor.toString().trim().isEmpty(), "The output should not be empty");




    }


}