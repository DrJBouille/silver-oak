import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ArenaDialog } from './arena-dialog.component';

describe('ArenaModal', () => {
  let component: ArenaDialog;
  let fixture: ComponentFixture<ArenaDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArenaDialog],
    }).compileComponents();

    fixture = TestBed.createComponent(ArenaDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
