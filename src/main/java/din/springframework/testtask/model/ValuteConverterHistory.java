package din.springframework.testtask.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
@Builder
@Entity
public class ValuteConverterHistory {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String initialValute;

    private String goalValute;

    private String initialSum;

    private String goalSum;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(columnDefinition = "DATE")
    private LocalDate queryDate;
}
