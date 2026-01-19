package hr.muzej.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* DTO za prijenos podataka o prostoriji
*/
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
