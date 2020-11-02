package din.springframework.testtask.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity(name = "Foreign_Currency_Market")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date", "nominal", "value"}))
public class ExchangeRate {

    @Id
    @GeneratedValue
    private UUID uuid;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    private String nominal;

    private String value;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name= "currency_id")
    private Currency currency;

}
