package id.saputra.adi.customerdataservice.controller;

import id.saputra.adi.customerdataservice.domain.dto.CustomerDto;
import id.saputra.adi.customerdataservice.services.CustomerService;
import id.saputra.adi.customerdataservice.util.TransformUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Api(tags = "Customer Services API", value = "Created by Adi Saputra")
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final String PREFIX_TRACE_ERROR = "Trace error : ";
    
    @Autowired
    private CustomerService customerService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        try {
            return ResponseEntity.ok(customerService.showAll());
        } catch (Exception ex){
            log.error("Happened error when get data customers. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/detail/{username}")
    @ResponseBody
    public ResponseEntity<Object> getDetail(@PathVariable String username){
        try {
            CustomerDto customerDto = (CustomerDto) TransformUtil.transform(customerService.showDetail(username), new CustomerDto());
            return ResponseEntity.ok(customerDto);
        } catch (Exception ex){
            log.error("Happened error when get data customers. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> create (@RequestBody CustomerDto customerDto){
        try {
            customerService.create(customerDto);
            return ResponseEntity.ok().body(customerDto);
        } catch (Exception ex){
            log.error("Happened error when create customers. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerDto);
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Object> update (@RequestBody CustomerDto customerDto){
        try {
            customerService.update(customerDto);
            return ResponseEntity.ok().body(customerDto);
        } catch (Exception ex){
            log.error("Happened error when update data customers. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerDto);
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Object> delete (@RequestBody CustomerDto customerDto){
        try {
            customerService.delete(customerDto);
            return ResponseEntity.accepted().body(customerDto);
        } catch (Exception ex){
            log.error("Happened error when soft delete data customers. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerDto);
        }
    }
}
