package org.financetracker.Controller;

import static org.financetracker.Security.Encryption.AesEncryptionUtil.encrypt;
import static org.financetracker.Security.Encryption.AesEncryptionUtil.decrypt;
import static org.financetracker.Util.FinanceMapper.convertToDto;
import static org.financetracker.Util.FinanceMapper.toDTOList;

import org.financetracker.Modal.Dto.FinanceDTO;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<FinanceDTO>> getAllFinances() throws Exception {
        List<Finance> finances = financeService.findAll();
        if (finances.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Finance finance : finances) {
            finance.setCode(decrypt(finance.getCode(), secretKey));
        }
        return ResponseEntity.ok(toDTOList(finances));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<FinanceDTO> getFinanceById(@PathVariable Integer id) throws Exception {
        Optional<Finance> finance = financeService.findById(id);
        if (finance.isPresent()) {
            Finance foundFinance = finance.get();
            foundFinance.setCode(decrypt(foundFinance.getCode(), secretKey));
            return ResponseEntity.ok(convertToDto(foundFinance));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<FinanceDTO> updateFinance(@PathVariable Integer id, @RequestBody Finance finance) {
        try {
            Optional<Finance> foundFinanceOptional = financeService.findById(id);

            if (foundFinanceOptional.isPresent()) {
                Finance foundFinance = foundFinanceOptional.get();
                foundFinance.setLanguage(finance.getLanguage());
                foundFinance.setCode(encrypt(finance.getCode(), secretKey));
                Finance updatedFinance = financeService.updateFinance(id, foundFinance);
                return ResponseEntity.ok(convertToDto(updatedFinance));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<FinanceDTO> createFinance(@RequestBody Finance finance) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        finance.setUser(user);
        finance.setCode(encrypt(finance.getCode(), secretKey));
        Finance newFinance = financeService.saveFinance(finance);

        return new ResponseEntity<>(convertToDto(newFinance), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Finance> deleteFinance(@PathVariable Integer id) {
        Optional<Finance> financeOptional = financeService.findById(id);
        if (financeOptional.isPresent()) {
            financeService.deleteFinance(financeOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}