package fxDeals.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "fx_deal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String dealUniqueId;

    @NotNull
    private Currency fromCurrencyIsoCode;

    @NotNull
    private Currency toCurrencyIsoCode;

    @NotNull
    private LocalDateTime dealTimestamp;

    @NotNull
    private BigDecimal dealAmount;
}
