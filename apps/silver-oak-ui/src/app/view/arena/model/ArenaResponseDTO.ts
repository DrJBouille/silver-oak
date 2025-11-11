import { CharactersResponseDTO } from '../../characters/model/CharactersResponseDTO';

export class ArenaResponseDTO {
  constructor(
    public player: CharactersResponseDTO,
    public enemy: CharactersResponseDTO,
    public doesPlayerWin: boolean,
    public isFinished: boolean,
    public isPlayerTurn: boolean,
  ) {}
}
