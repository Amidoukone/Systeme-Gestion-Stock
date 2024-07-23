import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonEntreePrintComponent } from './bon-entree-print.component';

describe('BonEntreePrintComponent', () => {
  let component: BonEntreePrintComponent;
  let fixture: ComponentFixture<BonEntreePrintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonEntreePrintComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonEntreePrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
