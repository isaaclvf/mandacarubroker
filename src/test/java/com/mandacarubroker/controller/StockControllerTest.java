package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockNotFoundException;
import com.mandacarubroker.service.StockService;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@SpringBootTest
class StockControllerTest {
    @Mock
    private StockService stockService;

    private StockController stockController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stockController = new StockController(stockService);
    }

    @Test
    @DisplayName("Get all stocks should return ok")
    void getAllShouldReturnOk() {
        ResponseEntity<List<Stock>> response = stockController.getAllStocks();

        HttpStatusCode code = response.getStatusCode();
        assertEquals(HttpStatus.OK, code);
    }

    @Test
    @DisplayName("Get stock by id should work if the stock is found")
    void getByIdShouldWorkIfFound() {
        String id = "1";
        Stock expectedStock = new Stock();
        expectedStock.setId(id);
        Mockito.when(stockService.getStockById(id)).thenReturn(Optional.of(expectedStock));

        ResponseEntity<Stock> response = stockController.getStockById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStock, response.getBody());
    }

    @Test
    @DisplayName("Get stock by id should return 404 if not found")
    void getByIdShould404IfNotFound() {
        String id = "1";
        Mockito.when(stockService.getStockById(id)).thenReturn(Optional.empty());

        ResponseEntity<Stock> response = stockController.getStockById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Create stock should work if valid request")
    void createStockShouldWork_withValidRequest() {
        RequestStockDTO requestData = new RequestStockDTO("ABC1", "Test Company", 1);
        Stock expectedStock = new Stock(requestData);
        Mockito.when(stockService.validateAndCreateStock(requestData)).thenReturn(expectedStock);

        ResponseEntity<Stock> response = stockController.createStock(requestData);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedStock, response.getBody());
    }

    @Test
    @DisplayName("Create stock should return bad request if invalid json request")
    void createStockShouldRespondBadRequest_withInvalidRequest() {
        RequestStockDTO requestData = new RequestStockDTO("ABC1", "Test Company", 1);
        Mockito.when(stockService.validateAndCreateStock(requestData)).thenThrow(new ValidationException("Invalid request"));

        ResponseEntity<Stock> response = stockController.createStock(requestData);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Update stock should return ok if valid request")
    void updateStockShouldReturnOk_ifValid() {
        String id = "1";
        Stock updatedStock = new Stock();
        Mockito.when(stockService.updateStock(id, updatedStock)).thenReturn(Optional.of(updatedStock));

        ResponseEntity<Stock> response = stockController.updateStock(id, updatedStock);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStock, response.getBody());
    }

    @Test
    @DisplayName("Update stock should return 404 if id was not found")
    void updateStockShould404_ifNotFound() {
        String id = "1";
        Stock updatedStock = new Stock();
        Mockito.when(stockService.updateStock(id, updatedStock)).thenReturn(Optional.empty());

        ResponseEntity<Stock> response = stockController.updateStock(id, updatedStock);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Update stock should return bad request if invalid request")
    void updateStockShouldBadRequest_ifInvalidRequest() {
        String id = "1";
        Stock updatedStock = new Stock();
        Mockito.when(stockService.updateStock(id, updatedStock)).thenThrow(new ValidationException("Invalid request"));

        ResponseEntity<Stock> response = stockController.updateStock(id, updatedStock);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete stock should return no content if deleted successfully")
    void deleteStockShouldReturnNoContent_ifFound() throws StockNotFoundException {
        String id = "1";
        Mockito.doNothing().when(stockService).deleteStock(id);

        ResponseEntity<Void> response = stockController.deleteStock(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(stockService, times(1)).deleteStock(id);
    }

    @Test
    @DisplayName("Delete stock should return 404 if not found")
    @SneakyThrows
    void deleteStockShould404_ifNotFound() {
        String id = "1";
        Mockito.doThrow(new StockNotFoundException("Stock not found")).when(stockService).deleteStock(id);

        ResponseEntity<Void> response = stockController.deleteStock(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
