package din.springframework.testtask.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString(exclude = {"valute"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlType(name = "ValCurs"/*, propOrder = {
        "valute",
         "id"}*/)
public class ValCurs implements Serializable {

    @XmlElement(name = "Valute")
    private Set<Valute> valute;

    @XmlID
    @XmlAttribute(name = "Date")
    private String date;

    @XmlAttribute(name = "name")
    private String name;

    /*private String valcurs_id;*/

}
