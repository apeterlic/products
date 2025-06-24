package dev.beenary.order.read;

import dev.beenary.PaginationAndSortingRequest;
import dev.beenary.TimeFilter;
import lombok.Data;

@Data
public class SearchOrderRequest extends PaginationAndSortingRequest {

    private TimeFilter createdFilter;
}
