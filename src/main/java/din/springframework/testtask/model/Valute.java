package din.springframework.testtask.model;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "Valute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"valcurs"})
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
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

    @Transient
    @XmlElement(name = "Nominal", required = true)
    private String nominal;

    @XmlElement(name = "Name", required = true)
    private String name;

    @Transient
    @XmlElement(name = "Value", required = true)
    private String value;

    @Id
    @XmlAttribute(name = "ID")
    @Column(name = "valute_id")
    private String id;

    @Transient
    private ValCurs valcurs;

    @OneToMany(mappedBy = "valute")
    private Set<CursValute> cursValute;

    @Override
    public String toString() {
        return "\n Valute{" +
                "numCode='" + numCode + '\'' +
                ", charCode='" + charCode + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
