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
public class ZahtjevDTO {
private List<Long> idProstorija;
private String muzeomatId;
}