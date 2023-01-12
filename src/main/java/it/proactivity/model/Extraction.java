package it.proactivity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "extraction")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Extraction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_number")
    private Integer firstNumber;

    @Column(name = "second_number")
    private Integer secondNumber;

    @Column(name = "third_number")
    private Integer thirdNumber;

    @Column(name = "fourth_number")
    private Integer fourthNumber;

    @Column(name = "fifth_number")
    private Integer fifthNumber;

    @Column(name = "extraction_date")
    private LocalDate extractionDate;

    @OneToMany
    private Wheel wheel;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + id + "]" +  " ");
        sb.append(fifthNumber +  " - " + secondNumber +  " - " + thirdNumber +  " - " + fourthNumber
                +  " - " +fifthNumber);
        return sb.toString();
    }

}
