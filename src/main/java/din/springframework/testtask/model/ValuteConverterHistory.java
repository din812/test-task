package din.springframework.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private Date queryDate;
}
