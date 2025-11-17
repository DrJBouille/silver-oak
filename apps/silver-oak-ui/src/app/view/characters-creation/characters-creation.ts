import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CharactersService } from '../../shared/services/characters-service';
import { Characters } from '../../shared/models/Characters';
import { Subscription } from 'rxjs';
import { ClassesService } from './services/classes-service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CharacterRequestDTO } from '../characters/model/CharacterRequestDTO';
import { Router } from '@angular/router';

@Component({
  selector: 'app-characters-creation',
  imports: [ReactiveFormsModule],
  templateUrl: './characters-creation.html',
  styleUrl: './characters-creation.css',
})
export class CharactersCreation implements OnInit, OnDestroy {
  private classesService = inject(ClassesService);
  private charactersService = inject(CharactersService);
  private router = inject(Router);
  private formBuilder = inject(FormBuilder);

  protected classes: string[] = [];
  protected characterForm!: FormGroup;

  private classesSubscription: Subscription | null = null;
  private characterSubscription: Subscription | null = null;

  ngOnInit() {
    this.characterForm = this.formBuilder.group({
      characterClass: '',
    });

    this.classesSubscription = this.classesService.getClasses().subscribe((classes) => {
        this.classes = classes;
        this.characterForm.patchValue({ characterClass: classes[0] });
    });
  }

  onSubmit() {
    if (this.characterForm.invalid) return;

    this.characterSubscription = this.charactersService
      .createCharacter(
        new CharacterRequestDTO(this.characterForm.value.characterClass)
      )
      .subscribe(() => this.router.navigate(['/characters']));
  }

  ngOnDestroy() {
    this.classesSubscription?.unsubscribe();
    this.characterSubscription?.unsubscribe();
  }
}
