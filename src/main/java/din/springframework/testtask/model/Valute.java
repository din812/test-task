package din.springframework.testtask.model;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "Valute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"valcurs"})
@Builder
@Entity(name = "valute")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Valute"/*, propOrder = {
        "id",
        "numCode",
        "charCode",
        "nominal",
        "name",
        "value"}*/)
public class Valute implements Serializable {

    @XmlElement(name = "NumCode", required = true)
    private String numCode;

    @XmlElement(name = "CharCode", required = true)
    private String charCode;

    @XmlElement(name = "Nominal", required = true)
    private String nominal;

    @XmlElement(name = "Name", required = true)
    private String name;

    @XmlElement(name = "Value", required = true)
    private String value;

    @XmlAttribute(name = "ID")
    @Id
    @Column(insertable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ValCurs valcurs;

    @Override
    public String toString() {
        return "\n Valute{" +
                "numCode='" + numCode + '\'' +
                ", charCode='" + charCode + '\'' +
                ", nominal='" + nominal + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", id='" + id + '\'' +
                ", valcurs=" + valcurs +
                '}';
    }
}
