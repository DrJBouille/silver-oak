package com.api.silver_oak_core.model.simulations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Node {
  final AttackResults attackResult;
  List<Node> children = new ArrayList<>();
}
