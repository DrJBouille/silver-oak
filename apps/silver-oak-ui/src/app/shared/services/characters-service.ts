import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CharactersResponseDTO } from '../../view/characters/model/CharactersResponseDTO';
import { CharacterRequestDTO } from '../../view/characters/model/CharacterRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class CharactersService {
  private http = inject(HttpClient)

  getCharacters() {
    return this.http.get<CharactersResponseDTO[]>('http://localhost:8080/api/characters');
  }

  getCharacter(id: number) {
    return this.http.get<CharactersResponseDTO>(`http://localhost:8080/api/characters/${id}`);
  }

  createCharacter(charactersRequestDTO: CharacterRequestDTO) {
    return this.http.post<CharactersResponseDTO>(`http://localhost:8080/api/characters`, charactersRequestDTO);
  }
}
