package hr.muzej.entiteti;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
/**
* Entitet za spremanje izracunatih ruta (za statistiku)
*/
@Entity
@Table(name = "rute")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ruta {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(name = "muzeomat_id", length = 50)
private String muzeomatId;
@Column(name = "vrijeme_kreiranja")
private LocalDateTime vrijemeKreiranja;
@Column(columnDefinition = "TEXT", nullable = false)
private String putanja; // Lista ID-ova prostorija, npr. "1,5,3,7,1"
@Column(name = "ukupna_udaljenost")
private Double ukupnaUdaljenost;
@PrePersist
protected void onCreate() {
vrijemeKreiranja = LocalDateTime.now();
}
}
