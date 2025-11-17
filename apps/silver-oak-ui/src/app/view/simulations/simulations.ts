import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ClassesService } from '../characters-creation/services/classes-service';
import { CharactersService } from '../../shared/services/characters-service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CharacterRequestDTO } from '../characters/model/CharacterRequestDTO';
import { Characters } from '../../shared/models/Characters';
import { SimulationsService } from './services/simulations/simulations.service';
import { SimulationsRequestDTO } from './models/SimulationsRequestDTO';

@Component({
  selector: 'app-simulations',
  imports: [ReactiveFormsModule],
  templateUrl: './simulations.html',
  styleUrl: './simulations.css',
})
export class Simulations implements OnInit, OnDestroy {
  private charactersService = inject(CharactersService);
  private simulationService = inject(SimulationsService);
  private router = inject(Router);
  private formBuilder = inject(FormBuilder);

  protected charactersNames: string[] = [];
  protected simulationForm!: FormGroup;

  private characterSubscription: Subscription | null = null;

  ngOnInit() {
    this.simulationForm = this.formBuilder.group({
      firstCharactersName: '',
      secondCharactersName: '',
    });

    this.characterSubscription = this.charactersService.getDefaultCharactersNames().subscribe((charactersNames) => {
      this.charactersNames = charactersNames;
      this.simulationForm.patchValue({ firstCharactersName: charactersNames[0] });
      this.simulationForm.patchValue({ secondCharactersName: charactersNames[0] });
    });
  }

  onSubmit() {
    if (this.simulationForm.invalid) return;

    const simulationRequestDTO = new SimulationsRequestDTO(this.simulationForm.value.firstCharactersName, this.simulationForm.value.secondCharactersName)

    this.characterSubscription?.unsubscribe();
    this.characterSubscription = this.simulationService
      .runTreeSimulation(simulationRequestDTO)
      .subscribe(result => {console.log(result)});
  }

  ngOnDestroy() {
    this.characterSubscription?.unsubscribe();
  }
}
