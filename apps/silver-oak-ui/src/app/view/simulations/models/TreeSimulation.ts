import { Characters } from '../../../shared/models/Characters';
import { Node } from './Node';

export class TreeSimulation {
  constructor(
    public characters: Characters[],
    public node: Node
  ) {}
}
