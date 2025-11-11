import { Component, inject } from '@angular/core';
import { NgClass } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ArenaResponseDTO } from '../../model/ArenaResponseDTO';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-arena-modal',
  imports: [NgClass, MatButton],
  templateUrl: './arena-dialog.component.html',
  styleUrl: './arena-dialog.component.css',
})
export class ArenaDialog {
  dialogRef = inject(MatDialogRef<ArenaDialog>);
  data = inject<ArenaResponseDTO>(MAT_DIALOG_DATA);

  close() {
    this.dialogRef.close();
  }
}
