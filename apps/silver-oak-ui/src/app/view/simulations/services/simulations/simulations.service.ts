import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TreeSimulation } from '../../models/TreeSimulation';
import { SimulationsRequestDTO } from '../../models/SimulationsRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class SimulationsService {
  private http = inject(HttpClient)

  runTreeSimulation(simulationsRequestDTO: SimulationsRequestDTO) {
    return this.http.post<TreeSimulation>('http://localhost:8080/api/arenas/tree-simulation', simulationsRequestDTO);
  }
}
