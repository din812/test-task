package din.springframework.testtask.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity(name = "Foreign_Currency_Market")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date", "nominal", "value"}))
public class CursValute {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String date;

    //private String numCode;

    //private String charCode;

    private String nominal;

    //private String name;

    private String value;

    @ManyToOne
    @JoinColumn(name="valute_id")
    private Valute valute;

}
