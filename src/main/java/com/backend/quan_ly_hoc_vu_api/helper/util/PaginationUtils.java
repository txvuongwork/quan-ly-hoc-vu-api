package com.backend.quan_ly_hoc_vu_api.helper.util;

import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import org.springframework.data.domain.Page;

public class PaginationUtils {

    public static <T> PaginationDTO<T> createPaginationResponse(Page<T> page) {
        return PaginationDTO.<T>builder()
                .items(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .currentPage(page.getNumber())
                .build();
    }
}
