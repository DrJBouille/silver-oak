import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ArenaForm } from './arena-form';

describe('ArenaForm', () => {
  let component: ArenaForm;
  let fixture: ComponentFixture<ArenaForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArenaForm],
    }).compileComponents();

    fixture = TestBed.createComponent(ArenaForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
