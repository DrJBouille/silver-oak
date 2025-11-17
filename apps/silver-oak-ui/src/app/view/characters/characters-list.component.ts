import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { Characters } from '../../shared/models/Characters';
import { CharactersService } from '../../shared/services/characters-service';
import { Subscription } from 'rxjs';
import { RouterLink } from '@angular/router';
import { Character } from './component/character/character';

@Component({
  selector: 'app-characters',
  imports: [RouterLink, Character],
  templateUrl: './characters-list.component.html',
  styleUrl: './characters-list.component.css',
})
export class CharactersList implements OnInit, OnDestroy {
  private characterService = inject(CharactersService);
  protected characters: Characters[] = [];

  private charactersSubscription: Subscription | null = null;

  ngOnInit() {
    this.charactersSubscription = this.characterService
      .getCharacters()
      .subscribe((characters) => (this.characters = characters));
  }

  ngOnDestroy() {
    this.charactersSubscription?.unsubscribe();
  }
}
