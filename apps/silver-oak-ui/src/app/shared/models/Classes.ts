import { WeaponType } from './WeaponType';

export class Classes {
  constructor(
    public name: string,
    public usableWeapons: WeaponType[]
  ) {}
}
