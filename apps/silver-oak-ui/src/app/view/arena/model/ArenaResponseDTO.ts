import { Characters } from '../../../shared/models/Characters';

export class ArenaResponseDTO {
  constructor(
    public player: Characters,
    public enemy: Characters,
    public doesPlayerWin: boolean,
    public isFinished: boolean,
    public isPlayerTurn: boolean,
  ) {}
}
