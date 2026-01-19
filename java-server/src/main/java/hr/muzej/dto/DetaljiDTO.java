package hr.muzej.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* DTO za detalje prostorije u ruti
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetaljiDTO {
private Long id;
private String naziv;
private Integer brojKata;
private Integer prosjecnoTrajanje;
private Double udaljenostDoSljedece; // Udaljenost do sljedece prostorije u ruti
// Koordinate za prikaz na canvasu (ako bude trebalo)
private Double koordinataX;
private Double koordinataY;
}
