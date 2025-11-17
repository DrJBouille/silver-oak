import { AttackResults } from './AttackResults';

export class Node {
  constructor(
    public attackResult: AttackResults,
    public children: Node[]
  ) {}
}
