import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Characters } from '../models/Characters';
import { CharacterRequestDTO } from '../../view/characters/model/CharacterRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class CharactersService {
  private http = inject(HttpClient)

  getCharacters() {
    return this.http.get<Characters[]>('http://localhost:8080/api/characters');
  }

  getCharacter(id: number) {
    return this.http.get<Characters>(`http://localhost:8080/api/characters/${id}`);
  }

  createCharacter(charactersRequestDTO: CharacterRequestDTO) {
    return this.http.post<Characters>(`http://localhost:8080/api/characters`, charactersRequestDTO);
  }

  getDefaultCharacters() {
    return this.http.get<Characters[]>('http://localhost:8080/api/characters/defaults');
  }

  getDefaultCharactersNames() {
    return this.http.get<string[]>('http://localhost:8080/api/characters/defaults-names');
  }
}
