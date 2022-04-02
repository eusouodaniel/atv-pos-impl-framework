package pro.gsilva.catalogo.model;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.persistence.OneToOne;

import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "TB_MUSICA")
@Data
public class Musica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String autor;

    @NotBlank
    private String titulo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "UTC-03")
    private LocalDate data;

    @NotBlank
    @Lob
    private String letra;

    @OneToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

}
