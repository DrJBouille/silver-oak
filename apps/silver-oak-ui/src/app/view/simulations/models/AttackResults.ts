export class AttackResults {
  constructor(
    public damage: number,
    public diceDamage: number,
    public defaultDamage: number,
    public isFatalBlow: boolean
  ) {}
}
