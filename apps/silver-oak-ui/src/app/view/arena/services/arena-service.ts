import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ArenaResponseDTO } from '../model/ArenaResponseDTO';
import { ArenaRequestDTO } from '../model/ArenaRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class ArenaService {
  private http = inject(HttpClient);

  getArenaType() {
    return this.http.get<string[]>('http://localhost:8080/api/arenas/types');
  }

  getArena(id: number) {
    return this.http.get<ArenaResponseDTO>(`http://localhost:8080/api/arenas/${id}`);
  }

  createArena(arenaRequestDTO: ArenaRequestDTO) {
    return this.http.post<ArenaRequestDTO>('http://localhost:8080/api/arenas', arenaRequestDTO);
  }

  playerAttack(id: number) {
    return this.http.get<ArenaResponseDTO>(`http://localhost:8080/api/arenas/attack/${id}`);
  }

  enemyAttack(id: number) {
    return this.http.get<ArenaResponseDTO>(`http://localhost:8080/api/arenas/enemy-attack/${id}`);
  }
}
