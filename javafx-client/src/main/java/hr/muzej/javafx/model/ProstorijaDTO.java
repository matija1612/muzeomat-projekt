package hr.muzej.javafx.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProstorijaDTO {
private Long id;
private String naziv;
private String opis;
private Integer brojKata;
private Double koordinataX;
private Double koordinataY;
private Integer prosjecnoTrajanje;
private Boolean aktivna;
}
