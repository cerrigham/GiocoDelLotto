package it.proactivity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "superenalotto")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Superenalotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "milano")
    private Integer milano;

    @Column(name = "bari")
    private Integer bari;

    @Column(name = "palermo")
    private Integer palermo;

    @Column(name = "roma")
    private Integer roma;

    @Column(name = "napoli")
    private Integer napoli;

    @Column(name = "firenze")
    private Integer firenze;

    @Column(name = "extraction_date")
    private LocalDate date;

    @Override
    public String toString() {
        return """
                I numeri del superenalotto sono :
                %s %s %s %s %s %s  data : %s
                """.formatted(this.getMilano(), this.getBari(), this.getPalermo(), this.getRoma(), this.getNapoli(),
                this.getFirenze(), this.getDate());
    }
}
