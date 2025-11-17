import { Component, Input } from '@angular/core';
import { Characters } from '../../../../shared/models/Characters';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-character',
  imports: [RouterLink],
  templateUrl: './character.html',
  styleUrl: './character.css',
})
export class Character {
  @Input() character!: Characters;
}
