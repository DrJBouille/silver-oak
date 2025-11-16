package com.api.silver_oak_core.model.simulations;

import com.api.silver_oak_core.model.characters.Characters;

import java.util.List;

public record TreeSimulation(List<Characters> characters, Node node) {
}
