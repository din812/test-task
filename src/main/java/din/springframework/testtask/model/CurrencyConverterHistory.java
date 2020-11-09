package din.springframework.testtask.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
@Builder
@Entity
public class CurrencyConverterHistory {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String initialCurrency;

    private String goalCurrency;

    private String initialSum;

    private String goalSum;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    private LocalDateTime queryDate;

    public CurrencyConverterHistory(String initialCurrency, String goalCurrency, String initialSum,
                                                                   String goalSum, User user, LocalDateTime queryDate) {
        this.initialCurrency = initialCurrency;
        this.goalCurrency = goalCurrency;
        this.initialSum = initialSum;
        this.goalSum = goalSum;
        this.user = user;
        this.queryDate = queryDate;
    }
}
