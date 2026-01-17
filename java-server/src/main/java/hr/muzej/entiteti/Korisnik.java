package hr.muzej.entiteti;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* Entitet za admin korisnike
*/
@Entity
@Table(name = "korisnici")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(name = "korisnicko_ime", unique = true, nullable = false)
private String korisnickoIme;
@Column(nullable = false)
private String lozinka; // BCrypt hash
@Column(length = 20)
private String uloga = "ADMIN"; // ADMIN ili USER
public boolean isAdmin() {
return "ADMIN".equals(uloga);
}
}
