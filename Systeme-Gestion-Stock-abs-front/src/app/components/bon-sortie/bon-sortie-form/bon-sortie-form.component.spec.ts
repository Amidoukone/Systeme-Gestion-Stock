import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonSortieFormComponent } from './bon-sortie-form.component';

describe('BonSortieFormComponent', () => {
  let component: BonSortieFormComponent;
  let fixture: ComponentFixture<BonSortieFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonSortieFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonSortieFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
