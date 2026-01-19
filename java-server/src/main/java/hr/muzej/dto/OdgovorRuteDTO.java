package hr.muzej.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
* DTO za odgovor sa optimiziranom rutom
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OdgovorRuteDTO {
private List<Long> putanja;                    
private List<DetaljiDTO> detalji;              
private Double ukupnaUdaljenost;               
private Integer ukupnoVrijeme;                 
private Long vrijemeIzracuna;                  
}
// Lista ID-ova u optimalnom redoslije
// Detalji o svakoj prostoriji
// Ukupna udaljenost u metrima
// Ukupno vrijeme u minutama
// Vrijeme TSP izracuna (ms)
