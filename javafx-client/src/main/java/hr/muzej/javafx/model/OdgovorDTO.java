package hr.muzej.javafx.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OdgovorDTO {
private List<Long> putanja;
private List<DetaljiDTO> detalji;
private Double ukupnaUdaljenost;
private Integer ukupnoVrijeme;
private Long vrijemeIzracuna;
}