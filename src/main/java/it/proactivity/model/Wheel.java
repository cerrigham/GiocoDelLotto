package it.proactivity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wheel")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Wheel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="wheel_id")
    private List<Extraction> extractionList;



    @Override
    public String toString() {
        return id + " " + city;
    }
}
