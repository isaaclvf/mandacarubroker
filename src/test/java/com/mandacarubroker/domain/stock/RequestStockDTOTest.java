package com.mandacarubroker.domain.stock;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RequestStockDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenSymbolInvalid_thenViolations() {
        // Arrange
        RequestStockDTO dto = new RequestStockDTO("invalidSymbol", "Test Company", 99.99);

        // Act
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("Symbol must be 3 letters followed by 1 number", violations.iterator().next().getMessage());
    }

    @Test
    void whenSymbolBlank_thenViolations() {
        // Arrange
        String blankString = "    ";
        RequestStockDTO dto = new RequestStockDTO(blankString, "Test Company", 99.99);

        // Act
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("Stock symbol cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenCompanyNameInvalid_thenViolations() {
        // Arrange
        String invalidCompanyName = "@~!+";
        RequestStockDTO dto = new RequestStockDTO("ABC0", invalidCompanyName, 99.99);

        // Act
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("Company name has invalid characters. Alphanumeric characters and white spaces only.", violations.iterator().next().getMessage());
    }

    @Test
    void whenCompanyNameBlank_thenViolations() {
        // Arrange
        String blankString = "    ";
        RequestStockDTO dto = new RequestStockDTO("ABC0", blankString, 99.99);

        // Act
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("Company name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenPriceSmallerOrEqualToZero_thenViolations() {
        // Arrange
        double smallerThanZero = -0.1;
        RequestStockDTO dto = new RequestStockDTO("ABC0", "Test Company", smallerThanZero);

        // Act
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("Price must be a positive number", violations.iterator().next().getMessage());
    }

    @Test
    void whenValidDTO_thenNoViolations() {
        // Arrange
        RequestStockDTO dto = new RequestStockDTO("ABC0", "Test Company", 99.99);

        // Act
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }
}
