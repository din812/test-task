package din.springframework.testtask.model;


import lombok.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString(exclude = {"currency"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlType(name = "ValCurs")
public class ValCurs implements Serializable {

    @XmlElement(name = "Valute")
    private Set<Currency> currency;

    @XmlID
    @XmlAttribute(name = "Date")
    private String date;

    @XmlAttribute(name = "name")
    private String name;
}
