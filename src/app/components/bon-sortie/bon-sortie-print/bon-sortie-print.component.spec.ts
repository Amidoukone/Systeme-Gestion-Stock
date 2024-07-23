import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonSortiePrintComponent } from './bon-sortie-print.component';

describe('BonSortiePrintComponent', () => {
  let component: BonSortiePrintComponent;
  let fixture: ComponentFixture<BonSortiePrintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonSortiePrintComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonSortiePrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
