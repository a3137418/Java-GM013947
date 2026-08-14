package com.example.demo.dto.watchlist;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WatchlistRequest {
    @NotNull
    private String stockId;
}
