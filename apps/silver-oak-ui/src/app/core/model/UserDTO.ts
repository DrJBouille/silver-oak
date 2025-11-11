import { RolesDTO } from './RolesDTO';

export class UserDTO {
  constructor(
    public id: number,
    public username: string,
    public isEnabled: boolean,
    public roles: RolesDTO[]
  ) {}
}
