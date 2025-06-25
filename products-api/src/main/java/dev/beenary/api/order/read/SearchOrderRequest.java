package dev.beenary.api.order.read;

import dev.beenary.api.PaginationAndSortingRequest;
import dev.beenary.api.TimeFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchOrderRequest extends PaginationAndSortingRequest {

    @Serial
    private static final long serialVersionUID = 1L;

    private TimeFilter createdFilter;
}
