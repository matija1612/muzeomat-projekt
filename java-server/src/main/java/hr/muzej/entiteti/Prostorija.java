package hr.muzej.entiteti;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* Entitet za muzejske prostorije
*/
@Entity
@Table(name = "prostorije")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prostorija {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false, length = 100)
private String naziv;
@Column(columnDefinition = "TEXT")
private String opis;
@Column(name = "broj_kata", nullable = false)
private Integer brojKata;
@Column(name = "koordinata_x", nullable = false)
private Double koordinataX;
@Column(name = "koordinata_y", nullable = false)
private Double koordinataY;
@Column(name = "prosjecno_trajanje")
private Integer prosjecnoTrajanje; // u minutama
@Column(nullable = false)
private Boolean aktivna = true;
// Pomocna metoda za racunanje udaljenosti do druge prostorije
public double udaljenostDo(Prostorija druga) {
double dx = this.koordinataX - druga.koordinataX;
double dy = this.koordinataY - druga.koordinataY;
return Math.sqrt(dx * dx + dy * dy);
}
}
