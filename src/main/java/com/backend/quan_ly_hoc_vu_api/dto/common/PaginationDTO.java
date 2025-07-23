package com.backend.quan_ly_hoc_vu_api.dto.common;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@Data
public class PaginationDTO<T> {

    private int totalPages;
    private long totalElements;
    private List<T> items;
    private int pageSize;
    private int currentPage;

}
