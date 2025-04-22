package org.financetracker.Util;

import org.financetracker.Modal.Dto.FinanceDTO;
import org.financetracker.Modal.Finance;

import java.util.ArrayList;
import java.util.List;

public class FinanceMapper {

    public static FinanceDTO convertToDto(Finance finance) {
        FinanceDTO dto = new FinanceDTO();
        dto.setId(finance.getId());
        dto.setLanguage(finance.getLanguage());
        dto.setCode(finance.getCode());
        dto.setOwnerUsername(finance.getUser().getUsername());
        return dto;
    }

    public static List<FinanceDTO> toDTOList(List<Finance> finances) {
        List <FinanceDTO> dtos = new ArrayList<>();
        for (Finance finance : finances) {
            dtos.add(convertToDto(finance));
        }
        return dtos;
    }
}
