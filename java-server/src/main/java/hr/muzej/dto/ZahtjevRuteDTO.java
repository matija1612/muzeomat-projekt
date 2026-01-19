package hr.muzej.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
* DTO za zahtjev optimizacije rute
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZahtjevRuteDTO {
private List<Long> idProstorija; // Lista ID-ova odabranih prostorija
private String muzeomatId;       
}
