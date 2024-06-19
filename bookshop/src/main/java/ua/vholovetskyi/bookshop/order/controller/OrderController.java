package ua.vholovetskyi.bookshop.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.vholovetskyi.bookshop.order.controller.dto.*;
import ua.vholovetskyi.bookshop.commons.exception.impl.order.ReportOrderException;
import ua.vholovetskyi.bookshop.commons.exception.impl.order.UploadOrderException;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.service.ExportOrderService;
import ua.vholovetskyi.bookshop.order.service.OrderService;
import ua.vholovetskyi.bookshop.order.service.QueryOrderService;
import ua.vholovetskyi.bookshop.order.service.UploadOrderService;


import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class OrderController {

    private static final CSVFormat FORMAT = CSVFormat.Builder
            .create(CSVFormat.DEFAULT)
            .setHeader("Id", "Status", "Gross value", "Order date")
            .build();
    private final OrderService orderService;
    private final UploadOrderService uploadOrderService;
    private final QueryOrderService queryOrderService;
    private final ExportOrderService exportOrder;

    @PostMapping("/_list")
    public OrderSearchResponse findOrders(@RequestBody @Valid OrderSearchRequest searchRequest) {
        return queryOrderService.findOrders(searchRequest);
    }

    @GetMapping("/{id}")
    public OrderEntity findOrderById(@PathVariable Long id) {
        return queryOrderService.findOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderSummary createOrder(@RequestBody @Valid OrderDto order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderEntity updateOrder(@PathVariable Long id, @RequestBody @Valid OrderDto orderDto) {
        return orderService.updateOrder(id, orderDto);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UploadOrderResponse uploadOrders(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return uploadOrderService.uploadOrders(file.getOriginalFilename(), inputStream);
        } catch (IOException e) {
            throw new UploadOrderException(e);
        }
    }

    @PostMapping("/_report")
    public ResponseEntity<Resource> exportOrders(@RequestBody @Valid SearchRequest searchRequest) {
        var orders = exportOrder.exportOrders(searchRequest);
        ByteArrayInputStream stream = transformToCsv(orders);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "reportOrder.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(stream));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    private ByteArrayInputStream transformToCsv(List<OrderList> orderList) {
        log.info("Start transform object to CSV file...");
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {
            for (OrderList order : orderList) {
                printer.printRecord(Arrays.asList(
                        order.getId(),
                        order.getStatus(),
                        order.getGrossValue(),
                        order.getOrderDate()
                ));
            }
            printer.flush();
            log.info("The end transform object to CSV file...");
            return new ByteArrayInputStream(stream.toByteArray());
        } catch (IOException e) {
            log.warn("Error transform object to CSV file. Error message: %s".formatted(e.getMessage()));
            throw new ReportOrderException("Error processing CSV file", e);
        }
    }
}
