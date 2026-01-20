package hr.muzej.javafx.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetaljiDTO {
private Long id;
private String naziv;
private Integer brojKata;
private Integer prosjecnoTrajanje;
private Double udaljenostDoSljedece;
// Za prikaz (ako bude trebalo)
private Double koordinataX;
private Double koordinataY;
}