package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)


public class OrderModel {
    private String id;
    private String status;
    private String number;
    private String createdAt;
    private String updatedAt;
    private List<String> ingredients;
}
