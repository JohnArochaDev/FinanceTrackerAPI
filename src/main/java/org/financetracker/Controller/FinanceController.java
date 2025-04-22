package org.financetracker.Controller;

import static org.financetracker.Security.Encryption.AesEncryptionUtil.encrypt;
import static org.financetracker.Security.Encryption.AesEncryptionUtil.decrypt;

import org.financetracker.Modal.Finance;
import org.financetracker.Modal.User;
import org.financetracker.Service.Finance.FinanceService;
import org.financetracker.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/finance")
public class FinanceController {
    private final FinanceService financeService;
    private final UserService userService;
    private final String secretKey;

    @Autowired
    public FinanceController(FinanceService financeService, UserService userService) {
        this.financeService = financeService;
        this.userService = userService;
        this.secretKey = System.getenv("ENCRYPTED_KEY");
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping
//    public ResponseEntity<List<FinanceDTO>> getAllFinances() throws Exception {
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/{id}")
//    public ResponseEntity<FinanceDTO> getFinanceById(@PathVariable Integer id) throws Exception {
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PutMapping("/{id}")
//    public ResponseEntity<FinanceDTO> updateFinance(@PathVariable Integer id, @RequestBody Finance finance) {
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping
//    public ResponseEntity<FinanceDTO> createFinance(@RequestBody Finance finance) throws Exception {
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Finance> deleteFinance(@PathVariable Integer id) {
//    }
}