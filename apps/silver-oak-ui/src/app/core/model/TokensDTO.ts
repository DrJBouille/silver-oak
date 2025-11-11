import { UserDTO } from './UserDTO';

export class TokensDTO {
  constructor(
    public accessToken: string,
    public refreshToken: string,
    public user: UserDTO
  ) {}
}
