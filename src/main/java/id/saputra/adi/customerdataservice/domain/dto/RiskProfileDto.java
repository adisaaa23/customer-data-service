package id.saputra.adi.customerdataservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskProfileDto implements Serializable {
    private static final long serialVersionUID = -4217345771722675199L;
    private Integer code;
    private String name;
}
