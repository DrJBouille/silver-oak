import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ArenaService } from './services/arena-service';
import { ArenaResponseDTO } from './model/ArenaResponseDTO';
import { ArenaForm } from './component/arena-form/arena-form';
import { CharactersService } from '../../shared/services/characters-service';
import { ArenaDialog } from './component/arena-modal/arena-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-arena',
  imports: [ArenaForm],
  templateUrl: './arena.html',
  styleUrl: './arena.css',
})
export class Arena implements OnInit, OnDestroy {
  private arenasService = inject(ArenaService);
  private charactersService = inject(CharactersService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private dialog = inject(MatDialog);

  protected characterId!: number;
  protected arena: ArenaResponseDTO | null = null;
  private arenaSubscription: Subscription | null = null;

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const characterIdString = params.get('characterId');
      if (characterIdString && isNaN(Number(characterIdString))) this.router.navigate(['/characters']);

      this.characterId = Number(characterIdString);

      this.charactersService.getCharacter(this.characterId).subscribe({ error: () => this.router.navigate(['/characters']) })

      this.arenaSubscription = this.arenasService
        .getArena(Number(characterIdString))
        .subscribe({
          next: arena => this.arena = arena,
          error: () => this.arena = null,
        });
    });
  }

  playerAttack() {
    this.arenasService.playerAttack(this.characterId).subscribe((arena) => {
      this.arena = arena;
      if (this.arena?.isFinished) this.openModal()
    });
  }

  enemyAttack() {
    this.arenasService.enemyAttack(this.characterId).subscribe((arena) => {
      this.arena = arena;
      if (this.arena?.isFinished) this.openModal();
    });
  }

  openModal() {
    console.log("CLOSE")
    const dialogRef = this.dialog.open(ArenaDialog, {
      data: this.arena,
      disableClose: true,
      width: '400px',
      backdropClass: 'bg-black/60',
    });

    dialogRef.afterClosed().subscribe(() => this.router.navigate(['/characters']));
  }

  ngOnDestroy() {
    this.arenaSubscription?.unsubscribe();
  }
}
