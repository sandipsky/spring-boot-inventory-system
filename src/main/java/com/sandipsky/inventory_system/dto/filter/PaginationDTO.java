package com.sandipsky.inventory_system.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDTO {
    private int pageIndex;
    private int pageSize;
}
