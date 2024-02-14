package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.validation.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockService {


    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(String id) {
        return stockRepository.findById(id);
    }

    public Optional<Stock> updateStock(String id, Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.getSymbol());
                    stock.setCompanyName(updatedStock.getCompanyName());
                    double newPrice = stock.changePrice(updatedStock.getPrice(), true);
                    stock.setPrice(newPrice);

                    return stockRepository.save(stock);
                });
    }

    public void deleteStock(String id) {
        stockRepository.deleteById(id);
    }

    public static void validateRequestStockDTO(RequestStockDTO data) {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        } catch (Exception e) {
            System.out.println("Error building validator factory: " + e.getMessage());
            return;
        }

        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(violation -> String.format("[%s: %s]", violation.getPropertyPath(), violation.getMessage()))
                    .collect(Collectors.joining(", ", "Validation failed. Details: ", "."));

            throw new ConstraintViolationException(errorMessage, new HashSet<>(violations));
        }
    }


    public Stock validateAndCreateStock(RequestStockDTO data) {
        validateRequestStockDTO(data);

        Stock stock = new Stock(data);
        return stockRepository.save(stock);
    }
}
