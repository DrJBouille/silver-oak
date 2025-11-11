import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ArenaService } from '../../services/arena-service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ArenaRequestDTO } from '../../model/ArenaRequestDTO';
import { CharactersService } from '../../../../shared/services/characters-service';

@Component({
  selector: 'app-arena-form',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './arena-form.html',
  styleUrl: './arena-form.css',
})
export class ArenaForm implements OnInit, OnDestroy {
  @Input() characterId!: number;

  private arenasService = inject(ArenaService);
  private router = inject(Router);
  private formBuilder = inject(FormBuilder);

  protected arenaTypes: string[] = [];
  protected arenaForm!: FormGroup;

  private arenaSubscription: Subscription | null = null;

  ngOnInit() {
    this.arenaForm = this.formBuilder.group({
      arenaType: '',
      characterId: this.characterId,
    });

    this.arenaSubscription = this.arenasService
      .getArenaType()
      .subscribe((arenaTypes) => {
        this.arenaTypes = arenaTypes;
        this.arenaForm.patchValue({ arenaType: arenaTypes[0] });
      });
  }

  onSubmit() {
    if (this.arenaForm.invalid) return;

    this.arenaSubscription = this.arenasService
      .createArena(
        new ArenaRequestDTO(
          this.arenaForm.value.arenaType,
          this.arenaForm.value.characterId
        )
      )
      .subscribe(() => window.location.reload());
  }

  ngOnDestroy() {
    this.arenaSubscription?.unsubscribe();
  }
}
