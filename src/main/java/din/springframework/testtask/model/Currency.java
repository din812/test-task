package din.springframework.testtask.model;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "Valute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"valcurs", "exchangeRate", "nominal"})
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@XmlType(name = "Currency")
public class Currency implements Serializable {

    @XmlElement(name = "NumCode", required = true)
    private String numCode;

    @XmlElement(name = "CharCode", required = true)
    private String charCode;

    @ToString.Exclude
    @Transient
    @XmlElement(name = "Nominal", required = true)
    private String nominal;

    @XmlElement(name = "Name", required = true)
    private String name;

    @ToString.Exclude
    @Transient
    @XmlElement(name = "Value", required = true)
    private String value;

    @Id
    @XmlAttribute(name = "ID")
    @Column(name = "currency_id")
    private String id;

    @ToString.Exclude
    @Transient
    private ValCurs valcurs;

    @ToString.Exclude
    @OneToMany(mappedBy = "currency")
    private Set<ExchangeRate> exchangeRate;
}
