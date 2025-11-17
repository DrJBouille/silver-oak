import { WeaponType } from './WeaponType';

export class Weapons {
  constructor(
    public name: string,
    public damageRange: number,
    public additionalDamage: number,
    public WeaponType: WeaponType
  ) {}
}
