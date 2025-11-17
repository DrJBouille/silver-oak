import { Classes } from './Classes';

export class Characters {
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


