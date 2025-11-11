import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CharactersCreation } from './characters-creation';

describe('CharactersCreation', () => {
  let component: CharactersCreation;
  let fixture: ComponentFixture<CharactersCreation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CharactersCreation],
    }).compileComponents();

    fixture = TestBed.createComponent(CharactersCreation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
