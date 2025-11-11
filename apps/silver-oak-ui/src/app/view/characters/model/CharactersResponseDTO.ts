export class CharactersResponseDTO {
  constructor(
    public id: number,
    public maxLife: number,
    public life: number,
    public damage: number,
    public level: number,
    public experience: number,
    public characterClass: Classes
  ) {}
}

class Classes {
  constructor(
    public usableWeapons: string[]
  ) {}
}
